package com.pact.stubber.impl;

import com.pact.stubber.config.SSLData;
import com.pact.stubber.config.ServerConfig;
import com.pact.stubber.interfaces.IServer;

public class StubberServerBuilder {

    private ServerConfig serverConfig;
    private SSLData sslData;

    public static StubberServerBuilder getInstance(){
        return new StubberServerBuilder();
    }

    public StubberServerBuilder setServerConfig(ServerConfig serverConfig){
        this.serverConfig = serverConfig;
        return this;
    }

    public StubberServerBuilder setSslData(SSLData sslData){
        this.sslData = sslData;
        return this;
    }

    public IServer build(){
        return sslData==null?new StubberHttpServer(this.serverConfig):new StubberHttpsServer(this.serverConfig,this.sslData);
    }

}
