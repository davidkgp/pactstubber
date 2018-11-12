package com.pact.stubber;

import com.pact.stubber.config.SSLData;
import com.pact.stubber.interfaces.TriFunction;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Optional;
import java.util.function.BiFunction;



public class MyFunctions {


    public static BiFunction<String,String,KeyStore> keyStoreLoad = (keyStoreProperty,keyStorePassProperty)->{

        KeyStore ks = null;
        InputStream fis = null;
        try {
            ks = KeyStore.getInstance("JKS");
            fis = new FileInputStream(keyStoreProperty);
            ks.load(fis, keyStorePassProperty.toCharArray());
            return ks;
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } finally{
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return ks;


    };

    public static BiFunction<SSLData,BiFunction<String,String,KeyStore>, Optional<KeyManagerFactory>> keyStore = (sslData,function1)->{
        KeyManagerFactory kmf = null;
        try {
            kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(function1.apply(sslData.getKeystorePath(),sslData.getKeyStorePass()), sslData.getKeyStorePass().toCharArray());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch(Exception excep){
            excep.printStackTrace();
        }
        return Optional.ofNullable(kmf);

    };

    public static BiFunction<SSLData,BiFunction<String,String,KeyStore>, Optional<TrustManagerFactory>> trustStore = (sslData,function1)->{
        TrustManagerFactory tmf = null;
        try {
            tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(function1.apply(sslData.getTrustStorePath().orElse(null),sslData.getTrustStorePass().orElse(null)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch(Exception excep){
            excep.printStackTrace();
        }
        return Optional.ofNullable(tmf);
    };

    public static TriFunction<BiFunction<SSLData,BiFunction<String,String,KeyStore>,Optional<KeyManagerFactory>>,
                BiFunction<SSLData,BiFunction<String,String,KeyStore>,Optional<TrustManagerFactory>>,
                SSLData,
                SSLContext> sslConvert = (keyStore, trustStore, sslData) ->{
        SSLContext sslContext = null;
        try{

            sslContext = SSLContext.getInstance("TLS");

            KeyManagerFactory kmf = keyStore.apply(sslData,keyStoreLoad).orElse(null);
            TrustManagerFactory tmf = trustStore.apply(sslData,keyStoreLoad).orElse(null);
            sslContext.init(kmf!=null?kmf.getKeyManagers():null, tmf!=null?tmf.getTrustManagers():null, null);


        }catch(Exception excep){
            excep.printStackTrace();
        }

        return sslContext;
    };
}
