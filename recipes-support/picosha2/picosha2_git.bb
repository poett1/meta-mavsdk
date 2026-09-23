SUMMARY = "Header-only SHA-256, fork with CMake install support"
HOMEPAGE = "https://github.com/julianoes/PicoSHA2"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=277ac5bc231af240c801ade17211246a"

require recipes-mavsdk/mavsdk-pins.inc

SRC_URI = "git://github.com/julianoes/PicoSHA2;protocol=https;branch=${MAVSDK_PICOSHA2_BRANCH}"

# write_basic_package_version_file(... VERSION 1.0.1) in CMakeLists.txt.
# Pin is the tip of the cmake-install-support branch MAVSDK 3.15.0 uses.
PV = "1.0.1+git"
SRCREV = "${MAVSDK_PICOSHA2_SRCREV}"

inherit cmake
