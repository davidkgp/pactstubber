package com.pact.stubber.impl;

import com.pact.stubber.StubberServerAbstract;
import com.pact.stubber.config.SSLData;
import com.pact.stubber.config.ServerConfig;
import com.pact.stubber.interfaces.TriFunction;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.security.KeyStore;
import java.util.Optional;
import java.util.function.BiFunction;

public class StubberHttpServer extends StubberServerAbstract {

    StubberHttpServer(ServerConfig serverConfig){
        super(serverConfig);
    }

    @Override
    public SSLData getSSLData() {
        return null;
    }

    @Override
    public SSLContext getSSLContext(TriFunction<BiFunction<SSLData, BiFunction<String, String, KeyStore>, Optional<KeyManagerFactory>>, BiFunction<SSLData, BiFunction<String, String, KeyStore>, Optional<TrustManagerFactory>>, SSLData, SSLContext> function) {
        return null;
    }


}
