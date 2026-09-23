SUMMARY = "MAVSDK C++ library and optional gRPC server"
HOMEPAGE = "https://mavsdk.mavlink.io"
# debian/copyright is only a BSD-3-Clause template, LICENSE.md is the real license.
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=84b641454775df91a2bae8fdd450e2e9"

# MAVSDK's superbuild vendors every dependency. We build with SUPERBUILD=OFF and
# take the dependencies from the distro; only what no other layer provides
# (mavlink headers, libmavlike, libevents, picosha2) lives in this layer.
#
# The proto definitions are a submodule pointing at a fork of MAVSDK-Proto, so
# they are fetched explicitly from the upstream repo at the pinned commit
# instead of via gitsm://. They are only needed for the mavsdk-server build.
SRC_URI = "git://github.com/mavlink/MAVSDK.git;protocol=https;branch=main;name=mavsdk \
           git://github.com/mavlink/MAVSDK-Proto.git;protocol=https;branch=main;name=proto;destsuffix=${BP}/proto \
           file://0001-FIX-LibLZMA-not-found.patch \
           file://0002-FIX-find-jsoncpp-using-pkgconfig.patch \
           file://0001-core-wake-the-work-thread-when-a-message-is-queued-fo.patch \
           "

require recipes-mavsdk/mavsdk-pins.inc

PV = "v3.15.0+git"
SRCREV_mavsdk = "${MAVSDK_SRCREV}"
SRCREV_proto = "${MAVSDK_PROTO_SRCREV}"
SRCREV_FORMAT = "mavsdk_proto"

# Core library: src/mavsdk/CMakeLists.txt and src/CMakeLists.txt at this SRCREV.
DEPENDS = " \
    mavlink-headers \
    libmavlike \
    libevents \
    picosha2 \
    libtinyxml2 \
    jsoncpp \
    curl \
    xz \
"

inherit cmake pkgconfig

PACKAGECONFIG ??= ""
PACKAGECONFIG[mavsdk-server] = "-DBUILD_MAVSDK_SERVER=ON,-DBUILD_MAVSDK_SERVER=OFF,grpc grpc-native protobuf protobuf-native abseil-cpp c-ares re2 zlib"

# Stop the build when mavsdk-pins.inc drifts from the revisions this MAVSDK
# was released with (its superbuild pins and the proto submodule).
mavsdk_check_pin() {
    if [ "$2" != "$3" ]; then
        bbfatal "mavsdk-pins.inc: $1 is $3, but MAVSDK ${MAVSDK_SRCREV} pins $2. Update mavsdk-pins.inc."
    fi
}

check_mavsdk_pins() {
    tp="${S}/third_party"
    mavsdk_check_pin MAVSDK_MAVLINK_SRCREV \
        "$(sed -n 's/.*set(MAVLINK_HASH "\([0-9a-f]*\)".*/\1/p' "$tp/CMakeLists.txt")" \
        "${MAVSDK_MAVLINK_SRCREV}"
    mavsdk_check_pin MAVSDK_LIBMAVLIKE_SRCREV \
        "$(awk '$1 == "GIT_TAG" { print $2; exit }' "$tp/libmavlike/CMakeLists.txt")" \
        "${MAVSDK_LIBMAVLIKE_SRCREV}"
    mavsdk_check_pin MAVSDK_LIBEVENTS_SRCREV \
        "$(awk '$1 == "GIT_TAG" { print $2; exit }' "$tp/libevents/CMakeLists.txt")" \
        "${MAVSDK_LIBEVENTS_SRCREV}"
    mavsdk_check_pin MAVSDK_PICOSHA2_BRANCH \
        "$(awk '$1 == "GIT_TAG" { print $2; exit }' "$tp/picosha2/CMakeLists.txt")" \
        "${MAVSDK_PICOSHA2_BRANCH}"
    mavsdk_check_pin MAVSDK_PROTO_SRCREV \
        "$(git -C "${S}" ls-tree HEAD proto | awk '{ print $3 }')" \
        "${MAVSDK_PROTO_SRCREV}"
}

# The checked-in protobuf/gRPC gencode under src/mavsdk_server/src/generated is
# produced by protobuf 29.1 and only compiles against that exact runtime.
# Regenerate it with the distro protoc so it matches the distro protobuf,
# after checking the pins.
do_configure:prepend() {
    check_mavsdk_pins
    if ${@bb.utils.contains('PACKAGECONFIG', 'mavsdk-server', 'true', 'false', d)}; then
        protos="${S}/proto/protos"
        gen="${S}/src/mavsdk_server/src/generated"
        for proto in "${protos}"/mavsdk_options.proto "${protos}"/*/*.proto; do
            protoc -I "${protos}" --cpp_out="${gen}" --grpc_out="${gen}" \
                --plugin=protoc-gen-grpc=${STAGING_BINDIR_NATIVE}/grpc_cpp_plugin "${proto}"
        done
    fi
}

EXTRA_OECMAKE += " \
    -DSUPERBUILD=OFF \
    -DBUILD_SHARED_LIBS=ON \
    -DBUILD_TESTING=OFF \
    -DMAVLINK_DIALECT=ardupilotmega \
    -DDEPS_INSTALL_PATH:STRING=${RECIPE_SYSROOT}/usr \
"

PACKAGE_BEFORE_PN = "${PN}-server"
# MAVSDKTargets.cmake exports MAVSDK::mavsdk_server_bin, and CMake refuses to
# load the package if that file is missing, so stage it for find_package(MAVSDK).
SYSROOT_DIRS += "${@bb.utils.contains('PACKAGECONFIG', 'mavsdk-server', '${bindir}', '', d)}"
FILES:${PN}-server = "${bindir}/mavsdk_server ${libdir}/libmavsdk_server${SOLIBS}"
