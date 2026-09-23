SUMMARY = "MAVLink library with a runtime defined message set, MAVSDK fork"
HOMEPAGE = "https://github.com/julianoes/libmavlike"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8e5974086ac4189f96d6afe4dc36ddbe"

require recipes-mavsdk/mavsdk-pins.inc

SRC_URI = "gitsm://github.com/julianoes/libmavlike;protocol=https;branch=mavsdk-fork"

# project(mav VERSION 0.1.0) in CMakeLists.txt. Pin matches MAVSDK 3.15.0.
PV = "0.1.0+git"
SRCREV = "${MAVSDK_LIBMAVLIKE_SRCREV}"

S = "${WORKDIR}/git"

DEPENDS += "libtinyxml2 picosha2"

FILES:${PN}-dev += "${datadir}/mav/cmake/*.cmake"

inherit cmake

EXTRA_OECMAKE = "-DCMAKE_POSITION_INDEPENDENT_CODE=ON"
