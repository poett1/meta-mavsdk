SUMMARY = "PX4/MAVLink events library, C++ part"
HOMEPAGE = "https://github.com/mavlink/libevents"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE.md;md5=92eb10e2bbc58e0d704de5f23dd755ef"

SRC_URI = "git://github.com/mavlink/libevents.git;protocol=https;branch=main"

# libevents has no releases or version in its build files. Pin matches MAVSDK 3.15.0.
PV = "0.0+git"
SRCREV = "7c1720749dfe555ec2e71d5f9f753e6ac1244e1c"

S = "${WORKDIR}/git/libs/cpp"

inherit cmake

EXTRA_OECMAKE = "-DCMAKE_POSITION_INDEPENDENT_CODE=ON -DBUILD_SHARED_LIBS=OFF -DCMAKE_CFG_INTDIR=${CMAKE_CFG_INTDIR} -DCMAKE_DEBUG_POSTFIX=d -DENABLE_TESTING=OFF"
