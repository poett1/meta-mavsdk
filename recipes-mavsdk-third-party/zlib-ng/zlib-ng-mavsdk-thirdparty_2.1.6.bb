# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE.md
LICENSE = "Zlib"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=b133671347d1c0879cb72e6691debfe3"

SRC_URI = "git://github.com/zlib-ng/zlib-ng;protocol=https;branch=stable"

# Modify these as desired
PV = "2.1.6"
SRCREV = "74253725f884e2424a0dd8ae3f69896d5377f325"

# NOTE: unable to map the following CMake package dependencies: GTest benchmark
DEPENDS = "libpng"

inherit cmake

# Specify any options you want to pass to cmake using EXTRA_OECMAKE:
EXTRA_OECMAKE = "-DCMAKE_POSITION_INDEPENDENT_CODE=ON -DBUILD_SHARED_LIBS=OFF -DZLIB_COMPAT=ON -DZLIB_ENABLE_TESTS=OFF -DWITH_GTEST=OFF"

