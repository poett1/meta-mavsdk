SUMMARY = "Protocol Buffers - structured data serialisation mechanism"
DESCRIPTION = "Protocol Buffers are a way of encoding structured data in an \
efficient yet extensible format. Google uses Protocol Buffers for almost \
all of its internal RPC protocols and file formats."
HOMEPAGE = "https://github.com/google/protobuf"
SECTION = "console/tools"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=37b5762e07f0af8c74ce80a8bda4266b"

DEPENDS = "zlib abseil-cpp"
DEPENDS:append:class-target = " protobuf-mavsdk-thirdparty-native"

SRCREV = "796e49f6ca82f763d1087d2ff7355d2f0d7b71b1"

SRC_URI = "gitsm://github.com/protocolbuffers/protobuf.git;branch=29.x;protocol=https \
           file://0001-examples-Makefile-respect-CXX-LDFLAGS-variables-fix-.patch \
           "
SRC_URI:append:mips:toolchain-clang = " file://0001-Fix-build-on-mips-clang.patch "
SRC_URI:append:mipsel:toolchain-clang = " file://0001-Fix-build-on-mips-clang.patch "

S = "${WORKDIR}/git"

CVE_STATUS[CVE-2024-7254] = "fixed-version: The vulnerability has been addressed and the fix is included in version v4.25.8"

inherit cmake pkgconfig 

PACKAGECONFIG ??= ""
PACKAGECONFIG:class-native ?= "compiler"
PACKAGECONFIG:class-nativesdk ?= "compiler"
PACKAGECONFIG[python] = ",,"
PACKAGECONFIG[compiler] = "-Dprotobuf_BUILD_PROTOC_BINARIES=ON,-Dprotobuf_BUILD_PROTOC_BINARIES=OFF"

EXTRA_OECMAKE += "\
    -Dprotobuf_BUILD_SHARED_LIBS=ON \
    -Dprotobuf_BUILD_LIBPROTOC=ON \
    -Dprotobuf_BUILD_TESTS=OFF \
    -Dprotobuf_BUILD_EXAMPLES=OFF \
    -Dprotobuf_ABSL_PROVIDER="package" \
"

EXTRA_OECMAKE:class-native += "\
    -Dprotobuf_BUILD_SHARED_LIBS=ON \
    -Dprotobuf_BUILD_LIBPROTOC=ON \
    -Dprotobuf_BUILD_TESTS=OFF \
    -Dprotobuf_BUILD_EXAMPLES=OFF \
    -Dprotobuf_ABSL_PROVIDER="package" \
    -Dprotobuf_BUILD_LIBUPB=OFF \
"

EXTRA_OECMAKE:class-target = "-Dprotobuf_BUILD_LIBUPB=OFF -Dprotobuf_BUILD_TESTS=OFF \
                 -Dprotobuf_BUILD_SHARED_LIBS=OFF -Dprotobuf_MODULE_COMPATIBLE=ON \
                 -Dprotobuf_MSVC_STATIC_RUNTIME=OFF -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
                 -Dprotobuf_ABSL_PROVIDER=package -DCMAKE_CXX_STANDARD=17 \
                 -Dprotobuf_BUILD_PROTOC_BINARIES=OFF \
"

TEST_SRC_DIR = "examples"
LANG_SUPPORT = "cpp ${@bb.utils.contains('PACKAGECONFIG', 'python', 'python', '', d)}"

PACKAGE_BEFORE_PN = "${PN}-compiler ${PN}-lite"

FILES:${PN}-compiler = "${bindir} ${libdir}/libprotoc${SOLIBS}"
FILES:${PN}-lite = "${libdir}/libprotobuf-lite${SOLIBS}"

# CMake requires protoc binary to exist in sysroot, even if it has wrong architecture.
SYSROOT_DIRS += "${bindir}"

RDEPENDS:${PN}-compiler = "${PN}"
RDEPENDS:${PN}-dev += "${PN}-compiler"

MIPS_INSTRUCTION_SET = "mips"

BBCLASSEXTEND = "native nativesdk"

LDFLAGS:append:arm = " -latomic"
LDFLAGS:append:mips = " -latomic"
LDFLAGS:append:powerpc = " -latomic"
LDFLAGS:append:mipsel = " -latomic"
