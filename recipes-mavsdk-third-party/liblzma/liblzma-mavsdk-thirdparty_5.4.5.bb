LICENSE = "GPL-2.0-only & LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=c8ea84ebe7b93cce676b54355dc6b2c0 \
                    file://COPYING.GPLv2;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.GPLv3;md5=1ebbd3e34237af26da5dc08a4e440464 \
                    file://COPYING.LGPLv2.1;md5=4fbd65380cdd255951079008b364516c"

SRC_URI = "https://github.com/tukaani-project/xz/releases/download/v${PV}/xz-${PV}.tar.gz"
SRC_URI[sha256sum] = "135c90b934aee8fbc0d467de87a05cb70d627da36abe518c357a873709e5b7d6"

S = "${UNPACKDIR}/xz-${PV}"

inherit cmake

EXTRA_OECMAKE = "-DCMAKE_POSITION_INDEPENDENT_CODE=ON -DBUILD_SHARED_LIBS=OFF -DCMAKE_DEBUG_POSTFIX=d"
