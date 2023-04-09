

### Carell 

A docker clone with two parts as it is in a docker stack running in a laptop.

A server daemon which accepts the commands over HTTP server via TCP (and UNIX socket) later on, this will run as an HTTP server.

A client which communicates the user interactions to the server. It will compiled to a native image (using GraalVM) so that it can be run as a binary.
