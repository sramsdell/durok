package com.durok.controller;

import com.example.model.*;
import com.example.api.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;


@RestController
public class ImplementedAPI implements DevEntrypointApi, VersionApi {

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return DevEntrypointApi.super.getRequest();
    }

    @Override
    public ResponseEntity<VersionResponse> getVersion() {
        final VersionResponse versionObj = new VersionResponse();

        String versionFilePath = System.getProperty("user.dir") + "/src/main/resources/version.txt";
        Path filePath = Paths.get(versionFilePath);
        try {
            versionObj.setVersion(Files.readString(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(versionObj);
    }

    @Override
    public ResponseEntity<DevEntrypoint> devEntrypoint() {
        final DevEntrypoint devEntrypointObj = new DevEntrypoint();
        return ResponseEntity.ok(devEntrypointObj);
    }
}