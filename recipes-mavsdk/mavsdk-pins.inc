# Source pins for MAVSDK and the libraries it builds against, in one place.
#
# MAVSDK names the exact revisions it was released with in its superbuild:
# MAVLINK_HASH and LIBMAVLIKE_HASH in cpp/third_party/CMakeLists.txt, GIT_TAG
# in cpp/third_party/{libevents,picosha2}/CMakeLists.txt, and the 'proto'
# submodule. When bumping MAVSDK_SRCREV, copy those values here. The mavsdk
# recipe checks them in do_configure and stops the build on a mismatch.

# MAVSDK v4.1.0
MAVSDK_SRCREV = "52ea50b52d518b7ff5d550f572c0657b9225c475"
# 'proto' submodule of MAVSDK_SRCREV
MAVSDK_PROTO_SRCREV = "2910d567224df6436fc9b15369ef9e448e565262"
# cpp/third_party/CMakeLists.txt MAVLINK_HASH
MAVSDK_MAVLINK_SRCREV = "87da370c02e40f6f9a2eacf1b162d7f07e640467"
# cpp/third_party/CMakeLists.txt LIBMAVLIKE_HASH
MAVSDK_LIBMAVLIKE_SRCREV = "1fa194035bf309ddeba3e12890d1c161c790a0b1"
# cpp/third_party/libevents/CMakeLists.txt GIT_TAG
MAVSDK_LIBEVENTS_SRCREV = "0ef305faa4cffdb09f01ca862ebfc108b7ff0cd5"
# cpp/third_party/picosha2/CMakeLists.txt names a branch, not a commit; the
# branch is checked, the commit is our own pin of its tip.
MAVSDK_PICOSHA2_BRANCH = "cmake-install-support"
MAVSDK_PICOSHA2_SRCREV = "1bf940d8a03bb752604fbb366d47b97b50b9e6ce"
