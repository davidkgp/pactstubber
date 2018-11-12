package com.pact.stubber;

import com.pact.parse.dto.InteractionDTO;
import com.pact.stubber.config.SSLData;
import com.pact.stubber.config.ServerConfig;
import com.pact.stubber.exceptions.StubberServerException;
import com.pact.stubber.impl.handler.MyPactHandler;
import com.pact.stubber.interfaces.IServer;
import com.pact.stubber.interfaces.TriFunction;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.KeyStore;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.pact.stubber.MyFunctions.getPactFiles;

public abstract class StubberServerAbstract implements IServer {

    private ServerConfig serverConfig;
    protected HttpServer server;
    protected ExecutorService executor;

    protected StubberServerAbstract(ServerConfig serverConfig){
        this.serverConfig = serverConfig;
    }

    protected ServerConfig getServerConfig(){
        return this.serverConfig;
    }

    @Override
    public void stopServer() throws StubberServerException {
        if(this.server!=null && this.executor!=null){
            this.server.stop(0);
            this.executor.shutdownNow();

        }else{
            throw new StubberServerException("Server not started yet");
        }


    }

    @Override
    public void startServer() throws StubberServerException {

        try {

            if(getSSLData()!=null){
                this.server = HttpsServer.create(new InetSocketAddress(getServerConfig().getPort()), 0);
                ((HttpsServer) this.server).setHttpsConfigurator(new HttpsConfigurator(getSSLContext(MyFunctions.sslConvert)));
            }else{
                this.server = HttpServer.create(new InetSocketAddress(getServerConfig().getPort()), 0);
            }

            this.server.createContext("/", new MyPactHandler());
            if (getServerConfig().getExecutor() != null) {
                this.server.setExecutor(getServerConfig().getExecutor());
            } else {
                this.server.setExecutor(Executors.newCachedThreadPool());
            }// creates a default executor
            this.server.start();
        } catch (IOException e) {
            e.printStackTrace();
            throw new StubberServerException(e.getMessage());
        }


    }

    @Override
    public void loadPacts() throws StubberServerException{

        List<InteractionDTO> listInteractions = MyFunctions.getInteractions.apply(MyFunctions.getPactFiles.apply(MyFunctions.getFolder.apply(getServerConfig().getPactFolderPath()),
                MyFunctions.isDirAndReadable),MyFunctions.isFileAndReadable);

    }

    public abstract SSLData getSSLData();

    public abstract SSLContext getSSLContext(TriFunction<BiFunction<SSLData,BiFunction<String,String, KeyStore>, Optional<KeyManagerFactory>>,
            BiFunction<SSLData,BiFunction<String,String,KeyStore>,Optional<TrustManagerFactory>>,
            SSLData,
            SSLContext> function);



}
