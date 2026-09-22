FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=54ad3cbe91bebcf6b1823970ff1fb97f \
                    file://pymavlink/COPYING;md5=6ea13ec5f0f3dd35ac5b53afdc3ed9ff \
                    file://pymavlink/generator/javascript/local_modules/jspack/LICENSE;md5=312dd5360f685408b04fb52e84940c4a \
                    file://pymavlink/generator/javascript/local_modules/long/LICENSE;md5=d273d63619c9aeaf15cdaf76422c4f87"

SRC_URI = "gitsm://github.com/mavlink/mavlink.git;protocol=https;branch=master"

SRC_URI += "file://0001-Pymavlink-as-yocto-dependency.patch"

PV = "1.0+git"
SRCREV = "5e3a42b8f3f53038f2779f9f69bd64767b913bb8"

S = "${WORKDIR}/git"

DEPENDS += "python3-pymavlink-native"

inherit cmake

EXTRA_OECMAKE += "-DMAVLINK_DIALECT=ardupilotmega"
