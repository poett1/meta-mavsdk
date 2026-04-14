DESCRIPTION = "A regular expression library"
HOMEPAGE = "https://github.com/google/re2/"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b5c31eb512bdf3cb11ffd5713963760"

# tag 2024-03-01
SRCREV = "2d866a3d0753f4f4fce93cccc6c59c4b052d7db4"

SRC_URI = "git://github.com/google/re2.git;branch=main;protocol=https"

S = "${WORKDIR}/git"

DEPENDS = "abseil-cpp"

inherit cmake

EXTRA_OECMAKE += " \
	-DBUILD_SHARED_LIBS=ON \
	${@bb.utils.contains('PTEST_ENABLED', '1', '-DRE2_BUILD_TESTING=ON', '-DRE2_BUILD_TESTING=OFF', d)} \
"
EXTRA_OECMAKE:class-target = "-DCMAKE_POSITION_INDEPENDENT_CODE=ON -DBUILD_SHARED_LIBS=OFF"

# ignore .so in /usr/lib64
SOLIBS = ".so*"
FILES_SOLIBSDEV = ""
INSANE_SKIP:${PN} += "dev-so"

# Don't include so files in dev package
FILES:${PN}-dev = "${includedir} ${libdir}/cmake ${libdir}/pkgconfig"

BBCLASSEXTEND = "native nativesdk"
