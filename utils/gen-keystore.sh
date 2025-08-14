#!/bin/bash

keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore ../src/main/resources/keystore.p12 -validity 3650

