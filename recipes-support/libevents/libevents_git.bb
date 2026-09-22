LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE.md;md5=92eb10e2bbc58e0d704de5f23dd755ef"

SRC_URI = "git://github.com/mavlink/libevents.git;protocol=https;branch=main"

PV = "1.0+git"
SRCREV = "7c1720749dfe555ec2e71d5f9f753e6ac1244e1c"

S = "${WORKDIR}/git/libs/cpp"

inherit cmake

EXTRA_OECMAKE = "-DCMAKE_POSITION_INDEPENDENT_CODE=ON -DBUILD_SHARED_LIBS=OFF -DCMAKE_CFG_INTDIR=${CMAKE_CFG_INTDIR} -DCMAKE_DEBUG_POSTFIX=d -DENABLE_TESTING=OFF"
