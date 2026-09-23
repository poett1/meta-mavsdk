# meta-mavsdk

Yocto/OpenEmbedded layer for the [MAVSDK](https://mavsdk.mavlink.io) C++ library.

MAVSDK's own build vendors every dependency through a CMake superbuild. This
layer builds with `SUPERBUILD=OFF` and takes the dependencies from the distro
(openembedded-core and meta-oe). Only the few libraries no other layer provides
are carried here.

## Recipes

| Recipe | What it is |
| :--- | :--- |
| `recipes-mavsdk/mavsdk/mavsdk_3.15.0.bb` | MAVSDK library, optional `mavsdk_server` |
| `recipes-support/mavlink-headers/mavlink-headers_git.bb` | Generated MAVLink C headers (ardupilotmega dialect), using the pymavlink submodule of the same mavlink commit |
| `recipes-support/libmavlike/libmavlike_git.bb` | MAVLink helper library used by MAVSDK |
| `recipes-support/libevents/libevents_git.bb` | PX4/MAVLink events library |
| `recipes-support/picosha2/picosha2_git.bb` | Header-only SHA-256 |

Everything else (curl, openssl, xz, jsoncpp, tinyxml2, grpc, protobuf, ...) comes
from openembedded-core and meta-oe. Do not add copies of those here: two recipes
installing the same library into one sysroot is exactly the conflict this layer
avoids.

## Usage

Add the layer to `bblayers.conf` together with `meta-oe`, then depend on `mavsdk`
from your recipe or add it to the image:

```
IMAGE_INSTALL:append = " mavsdk"
```

### mavsdk_server

The gRPC server is off by default. Enable it with

```
PACKAGECONFIG:append:pn-mavsdk = " mavsdk-server"
```

which pulls grpc and protobuf from meta-oe and produces the `mavsdk-server`
package. MAVSDK ships protobuf gencode for protobuf 29.1, which only compiles against that
exact runtime. The recipe regenerates it with the distro protoc during
`do_configure`, so the server matches whatever protobuf meta-oe provides.

## Branches

`scarthgap` and `wrynose` follow the Yocto release they are named after.

## License

Recipes are MIT (see `COPYING.MIT`). The software they build keeps its upstream
license, see `LIC_FILES_CHKSUM` in each recipe.
