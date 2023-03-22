# Echo

Just echo back the uri.
For example:

```shell
curl localhost:8080/echo/12345
```

should return

```
/echo/12345
```

> _HttpServer code taken from https://dzone.com/articles/simple-http-server-in-java._

### Problem

Build and run the native image with following commands (_on Windows 11_):

```
javac -d classes Echo.java
native-image --static -cp classes Echo
.\echo
```

A `curl` request

```shell
curl localhost:8080/echo/12345
```

receives expected response:

```
/echo/12345
```

But for build and run of a docker image,

```
docker build -t echo:1 .
docker run --rm -p 8080:8080 echo:1
```

for the same request, receives following response:

```
curl: (52) Empty reply from server
```

Stating container as `docker run --rm -p 8080:8080 -it echo:1 sh`, and then executing following commands, in the
container:

```
apk update
apk upgrade
apk add curl
./echo &
curl localhost:8080/echo/12345
```

receives expected response `/echo/12345`.

### Solution

Change `localhost` to `0.0.0.0`, as described [here](https://stackoverflow.com/a/67311644/840031).
