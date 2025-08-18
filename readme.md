<h1> Durok Card Game Backend</h1>

https://en.wikipedia.org/wiki/Durak

<hr>

Build and run local server
```
./gradlew clean
./gradlew build
./gradlew bootRun
```

Build and run Container
```
./buildDocker.sh
./runDocker.sh
```

Json Documentation: http://localhost:8080/v3/api-docs <br>
Swagger Documentation: http://localhost:8080/swagger-ui/index.html

<hr>
<hr>
Deprecated notes not ready to remove: <br>

To get swagger files to resolve.
right click src/main/java -> open module setting
dependencies -> add module dependency