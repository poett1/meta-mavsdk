LICENSE = "BSD-3-Clause & Unknown"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=84b641454775df91a2bae8fdd450e2e9 \
                    file://debian/copyright;md5=40d669a2ad31adadbe5505defc10fbcc"

# This does not work because github.com/ryanf55/MAVSDK-Proto is added as a submodule in MAVSDK, but 
# github.com/mavlink/MAVSDK-Proto must be used. Since we are not using superbuild, we can leave out the submodule and just add a patch to fix the URL.
# SRC_URI = "gitsm://github.com/mavlink/MAVSDK.git;protocol=https;branch=main 

SRC_URI = "git://github.com/mavlink/MAVSDK.git;protocol=https;branch=main \
            file://0001-FIX-LibLZMA-not-found.patch  \
            file://0002-FIX-find-jsoncpp-using-pkgconfig.patch \
            file://0005-FIX-mavlink-headers-not-found.patch \
            file://0006-FIX-MAVLINK_MSG_ID_PARAM_ERROR-not-found.patch \
            file://0001-core-wake-the-work-thread-when-a-message-is-queued-fo.patch \
           "

#TODO: QA Issue: File /usr/lib/cmake/MAVSDK/MAVSDKTargets.cmake in package mavsdk-dev contains reference to TMPDIR [buildpaths]

PV = "v3.15.0+git"
SRCREV = "721efdc45eedfe8761ceb7280dedca6004b1ea92"

S = "${WORKDIR}/git"

DEPENDS = " \
    mavlink-mavsdk-thirdparty  \
    libmavlike-mavsdk-thirdparty \
    abseil-cpp \
    libtinyxml2 \
    c-ares \
    protobuf \
    curl \
    zlib \
    re2 \
    libevents-mavsdk-thirdparty \
    pkgconfig-native \
    openssl \
    jsoncpp \
    xz \
    gtest \
"

inherit cmake

# Optional features: mavsdk server needs a fixed version of gRPC and protobuf, so we need to add those as dependencies when mavsdk-server is enabled.
# alternatively, add file://0007-FIX-remove-fixed-protobuf-version-from-mavsdk-server.patch to remove the fixed version requirement and just use the versions provided by the distro.
PACKAGECONFIG ??= ""
PACKAGECONFIG[mavsdk-server] = "-DBUILD_MAVSDK_SERVER=ON,-DBUILD_MAVSDK_SERVER=OFF,grpc-mavsdk-thirdparty protobuf-mavsdk-thirdparty"

# Specify any options you want to pass to cmake using EXTRA_OECMAKE:
EXTRA_OECMAKE += " -DSUPERBUILD=OFF -DBUILD_SHARED_LIBS=ON -DBUILD_TESTING=OFF -DMAVLINK_DIALECT=ardupilotmega -DDEPS_INSTALL_PATH:STRING=${RECIPE_SYSROOT}/usr"

