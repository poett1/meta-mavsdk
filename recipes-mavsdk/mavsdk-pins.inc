# Source pins for MAVSDK and the libraries it builds against, in one place.
#
# MAVSDK names the exact revisions it was released with in its superbuild:
# MAVLINK_HASH in third_party/CMakeLists.txt, GIT_TAG in
# third_party/{libmavlike,libevents,picosha2}/CMakeLists.txt, and the 'proto'
# submodule. When bumping MAVSDK_SRCREV, copy those values here. The mavsdk
# recipe checks them in do_configure and stops the build on a mismatch.

# MAVSDK v3.15.0
MAVSDK_SRCREV = "721efdc45eedfe8761ceb7280dedca6004b1ea92"
# 'proto' submodule of MAVSDK_SRCREV
MAVSDK_PROTO_SRCREV = "d274275a9bb15259959c6224e76bf2cbed5a9dff"
# third_party/CMakeLists.txt MAVLINK_HASH
MAVSDK_MAVLINK_SRCREV = "d6a7eeaf43319ce6da19a1973ca40180a4210643"
# third_party/libmavlike/CMakeLists.txt GIT_TAG
MAVSDK_LIBMAVLIKE_SRCREV = "80dbd91a0c5d6f0a79f1e8597b820ba075d1cf15"
# third_party/libevents/CMakeLists.txt GIT_TAG
MAVSDK_LIBEVENTS_SRCREV = "7c1720749dfe555ec2e71d5f9f753e6ac1244e1c"
# third_party/picosha2/CMakeLists.txt names a branch, not a commit; the branch
# is checked, the commit is our own pin of its tip.
MAVSDK_PICOSHA2_BRANCH = "cmake-install-support"
MAVSDK_PICOSHA2_SRCREV = "1bf940d8a03bb752604fbb366d47b97b50b9e6ce"
