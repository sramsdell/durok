./gradlew clean
./gradlew build
./gradlew bootRun
curl http://localhost:8080/hello

./buildDocker.sh
./runDocker.sh
curl http://localhost:8080/hello


To get swagger files to resolve.
right click src/main/java -> open module setting
dependencies -> add module dependency