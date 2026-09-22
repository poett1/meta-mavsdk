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
           file://0005-FIX-mavlink-headers-not-found.patch \
           file://0001-core-wake-the-work-thread-when-a-message-is-queued-fo.patch \
           "

PV = "v3.15.0+git"
SRCREV_mavsdk = "721efdc45eedfe8761ceb7280dedca6004b1ea92"
# Must match the 'proto' submodule commit of SRCREV_mavsdk.
SRCREV_proto = "d274275a9bb15259959c6224e76bf2cbed5a9dff"
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
    pkgconfig-native \
"

inherit cmake pkgconfig

PACKAGECONFIG ??= ""
PACKAGECONFIG[mavsdk-server] = "-DBUILD_MAVSDK_SERVER=ON,-DBUILD_MAVSDK_SERVER=OFF,grpc grpc-native protobuf protobuf-native abseil-cpp c-ares re2 zlib"

# The checked-in protobuf/gRPC gencode under src/mavsdk_server/src/generated is
# produced by protobuf 29.1 and only compiles against that exact runtime.
# Regenerate it with the distro protoc so it matches the distro protobuf.
do_configure:prepend() {
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

do_install:append() {
    # The CMake export leaks the absolute sysroot include path (DEPS_INSTALL_PATH)
    # into MAVSDKTargets.cmake, which trips do_package_qa [buildpaths].
    sed -i -e "s#${RECIPE_SYSROOT}/usr/include;##g" \
        ${D}${libdir}/cmake/MAVSDK/MAVSDKTargets.cmake
}

PACKAGE_BEFORE_PN = "${PN}-server"
FILES:${PN}-server = "${bindir}/mavsdk_server ${libdir}/libmavsdk_server${SOLIBS}"
