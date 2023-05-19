# TCP Socket Web Client
> for testing tcp socket connections

> java, spring boot, maven, tomcat

> with maven generate connect.war and deploy it in tomcat.

```
default host: loopback
default port: 9090
```

### Web browser
```
http://webserver:webserverport/connector/socket
socket localhost! 9090! response: Hello, client!!
```
### Curl
```
curl http://webserver:webserverport/connector/socket
socket 127.0.0.1! 9090! response: Hello, client!!
```
### Custom Host & Port
```
curl "http://webserver:webserverport/connector/socket?host=127.0.0.1&port=9090"
socket 127.0.0.1! 9090! response: Hello, client!!
```
