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
Set thrift port to 0 (`-t 0`) to disable Thrift APIs.

Upon successful startup, id-server listens for REST requests on port `8080` and Thrift requests on port `9090` (both port numbers are configurable).

Stop server:
> `/usr/local/id-server/conf/server-production.sh stop`


## Configurations ##

### ID Engines Configurations ###

See file `conf/spring/beans.xml`.


## APIs ##

### REST APIs ###

Generates next ID for a namespace, using default engine:
> `GET /nextId/<namespace>`
> `GET /nextId/users`
> `GET /nextId/topics`

Generates next ID for a namespace, using specified engine:
> `GET /nextId/<namespace>/<engine>`

Supported engines:
- `default`: default engine, configured in `conf/spring/beans.xml`
- `jdbc`: utilize a database system to generate IDs.
- `redis`: utilize [Redis](http://redis.io) to generate IDs.
- `zookeeper`: utilize [ZooKeeper](http://zookeeper.apache.org) to generate IDs.
- `snowflake`: generate IDs using [Snowflake algorithm](https://blog.twitter.com/2010/announcing-snowflake).

### Thrift APIs ###

See file [idserver.thrift](thrift/idserver.thrift).
