package com.pact.stubber.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.concurrent.ExecutorService;

@Getter
@AllArgsConstructor
public class ServerConfig {

    private int port;
    @NonNull private String pactFolderPath;
    private ExecutorService executor;
}
