package com.pact.stubber.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.net.ssl.SSLContext;
import java.util.Optional;
import java.util.function.Function;

@Getter
@AllArgsConstructor
public class SSLData {

    private String keystorePath;
    private Optional<String> trustStorePath;
    private String keyStorePass;
    private Optional<String> trustStorePass;

    /*public SSLContext getSSLContext(Function<SSLData,SSLContext> function){
        return function.apply(this);
    }*/

}

