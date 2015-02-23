ID Server
=========

Server to generate IDs - by btnguyen2k.

Project home: [https://github.com/btnguyen2k/id-server](https://github.com/btnguyen2k/id-server).

## License ##

See [LICENSE.txt](LICENSE.txt) for details. Copyright (c) 2015 btnguyen2k.

Third party libraries are distributed under their own license(s).

## Release-notes ##

Latest release: `0.1.0`.

See [RELEASE-NOTES.md](RELEASE-NOTES.md).

## Installation ##

Note: Java 7+ is required!

### Install from binary ###

- Download binary package from [project release site](https://github.com/btnguyen2k/id-server/releases).
- Unzip the binary package and copy it to your favourite location, e.g. `/usr/local/id-server`.


### Install from source ###

- Download application's source, either cloning github project or download the source package from [project release site](https://github.com/btnguyen2k/id-server/releases).
- Build [Play! Framework](https://www.playframework.com): `play dist`.
- The built binary package is available at `target/universal/id-server-<version>.zip`. You may copy it to your favourite location, e.g. `/usr/local/id-server`.


## Start/Stop Id-Server ##

### Linux ###

Start server with default options:
> `/usr/local/id-server/conf/server-production.sh start`

Start server with 1024M memory limit, REST API in port 18080, Thrift API on port 19090
> `/usr/local/id-server/conf/server-production.sh start -m 1024 -p 18080 -t 19090`

Upon successful startup, id-server listens for REST requests on port `8080` and Thrift requests on port `9090` (both port numbers are configurable).

Stop server:
> `/usr/local/id-server/conf/server-production.sh stop`


## Configurations ##

### Startup Script Parameters ###

>`/usr/local/id-server/conf/server-production.sh start [-m <JVM memory limit in mb>] [-p <http port>] [-t <thrift port>] [-j "<extra jvm options>"]`
>    -m : JVM memory limit in mb (default 64)
>    -p : Http port for REST APIs (default 8080)
>    -t : Thrift port for Thrift APIs (default 9090)
>    -j : Extra JVM options (example: -Djava.rmi.server.hostname=localhost)

### ID Engines Configurations ###

See file `conf/spring/beans.xml`.
