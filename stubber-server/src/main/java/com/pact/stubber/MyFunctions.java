package com.pact.stubber;

import com.pact.parse.dto.InteractionDTO;
import com.pact.parse.implementation.PactParse;
import com.pact.stubber.config.SSLData;
import com.pact.stubber.interfaces.TriFunction;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


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


    public static Function<String, File> getFolder = str-> new File(str);

    public static Predicate<File> isDirAndReadable = file->file.exists()&&file.isDirectory()&&file.canRead();

    public static Predicate<File> isFileAndReadable = file-> file.exists() && file.isFile() && !file.isDirectory() && file.canRead();

    public static Function<DirectoryStream<Path>,Function<Predicate,List<File>>> convertStreamToList = stream->predicate->{
        List<File> pactFiles = null;

        Iterator<Path> streamIterator = stream.iterator();

        while(streamIterator.hasNext()){
            Path path = streamIterator.next();

            if(predicate.test(path.toFile())){
                if(pactFiles==null){
                    pactFiles = new ArrayList();
                }
                pactFiles.add(path.toFile());
            }else{
                System.out.println(path.toFile().getPath() +"is not a file or not accessible");
            }

        }

        return pactFiles;
    };

    /*public static BiFunction<DirectoryStream<Path>,Predicate,List<File>> convertStreamToList = (stream,predicate)->{
        List<File> pactFiles = null;

        Iterator<Path> streamIterator = stream.iterator();

        while(streamIterator.hasNext()){
            Path path = streamIterator.next();

            if(predicate.test(path.toFile())){
                if(pactFiles==null){
                    pactFiles = new ArrayList();
                }
                pactFiles.add(path.toFile());
            }else{
                System.out.println(path.toFile().getPath() +"is not a file or not accessible");
            }

        }

        return pactFiles;



    };*/

    public static Function<File,Function<Predicate<File>,List<File>>> getPactFiles = pactFolder->predicate->{
        List<File> pactFiles = null;
        if(predicate.test(pactFolder)){

            try {
                pactFiles = convertStreamToList.apply(Files.newDirectoryStream(pactFolder.toPath(),path -> path.toString().endsWith(".json"))).apply(isFileAndReadable);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return pactFiles;
    };

    /*public static BiFunction<File,Predicate<File>,List<File>> getPactFiles = (pactFolder,predicate)->{
        List<File> pactFiles = null;
        if(predicate.test(pactFolder)){

            try {
                pactFiles = convertStreamToList.apply(Files.newDirectoryStream(pactFolder.toPath(),path -> path.toString().endsWith(".json")),isFileAndReadable);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return pactFiles;
    };*/

    public static Function<File,InteractionDTO> parsePact = file->{

        PactParse parse = new PactParse();
        return parse.getInteractions(parse.getJsonObj(file));

    };

    /*public static BiFunction<List<File>,Predicate<File>,List<InteractionDTO>> getInteractions = (files,predicate)->{
        return files.stream().filter(predicate).map(parsePact).collect(Collectors.toList());
    };*/

    public static Function<List<File>,Function<Predicate<File>,List<InteractionDTO>>> getInteractions = files->predicate->{
        return files.stream().filter(predicate).map(parsePact).collect(Collectors.toList());
    };


}
