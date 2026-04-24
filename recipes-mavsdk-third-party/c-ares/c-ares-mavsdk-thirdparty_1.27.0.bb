# Copyright (c) 2012-2014 LG Electronics, Inc.
SUMMARY = "c-ares is a C library that resolves names asynchronously."
HOMEPAGE = "http://daniel.haxx.se/projects/c-ares/"
SECTION = "libs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://../git/LICENSE.md;md5=fdbc58a6da11a9f68aa73c453818decc"

SRC_URI = "gitsm://github.com/c-ares/c-ares.git;protocol=https;branch=v1.27"

SRCREV = "9eb57f2c8beb4e8af590992e56182c0788b9ce0b"

PACKAGECONFIG = ""
PACKAGECONFIG[manpages] = ""
PACKAGECONFIG[tests] = "-DCARES_BUILD_TESTS=ON,-DCARES_BUILD_TESTS=OFF,"

inherit cmake manpages pkgconfig

EXTRA_OECMAKE = "-DCARES_STATIC=${@ 'ON' if d.getVar('DISABLE_STATIC') == '' else 'OFF' }"
EXTRA_OECMAKE:class-target = "-DCARES_STATIC=ON -DCARES_SHARED=OFF -DCARES_STATIC_PIC=ON -DCARES_BUILD_TOOLS=OFF"

PACKAGE_BEFORE_PN = "${PN}-utils"

FILES:${PN}-utils = "${bindir}"

BBCLASSEXTEND = "native nativesdk"
