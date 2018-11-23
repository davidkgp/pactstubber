package com.pact.stubber;

import com.pact.parse.dto.*;
import com.pact.parse.dto.payload.JSONObjEx;
import com.pact.parse.implementation.PactParse;
import com.pact.stubber.config.SSLData;
import com.pact.stubber.interfaces.TriFunction;
import com.pact.stubber.util.ListUtils;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.json.JSONObject;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


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

    public static Predicate<List> listNotEmpty = list->list!=null && !list.isEmpty();

    public static Predicate<Map> mapNotEmpty = map->map!=null && !map.isEmpty();


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
                System.out.println(path.toFile().getPath());
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
        return files!=null && !files.isEmpty()?files.stream().filter(predicate).map(parsePact).collect(toList()):null;
    };

    public static Function<List<InteractionDTO>, Map<RequestData, ResponseData>> convertToMap = list->{

        return list!=null && !list.isEmpty()?list.stream().map(i->i.getInteractions()).flatMap(i->((List<Interaction>)i).stream()).collect(Collectors.toMap(i->i.getRequestBodyData(), i->i.getResponseBodyData())):null;
    };


    /*public static Function<List<InteractionDTO>, Map<RequestData, ResponseData>> convertToMap = list->{

        Function<InteractionDTO,List<Interaction>> funcion1 = i->i.getInteractions();

        return list!=null && !list.isEmpty()? ListUtils.map(funcion1).andThen(ListUtils.flatMap(list))apply(list) :null;
    };*/


    public static Function<Headers, HeaderObj> convertHeaders = headers -> {
        return new HeaderObj(headers.entrySet().stream().collect(Collectors.toMap(b->b.getKey().toUpperCase(),b->b.getValue())));
    };

    /*public static Function<InputStream,JSONObject> convertBody = in->{
        JSONObject jsonObj = null;
        StringBuilder body = new StringBuilder();
        try (InputStreamReader reader = new InputStreamReader(in, Charset.defaultCharset())) {
            char[] buffer = new char[256];
            int read;
            while ((read = reader.read(buffer)) != -1) {
                body.append(buffer, 0, read);
            }
            jsonObj = body.length()!=0? new JSONObject(body.toString()):null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObj;
    };*/

    public static Function<InputStream, JSONObjEx> convertBody = in->{
        JSONObjEx jsonObj = null;
        StringBuilder body = new StringBuilder();
        try (InputStreamReader reader = new InputStreamReader(in, Charset.defaultCharset())) {
            char[] buffer = new char[256];
            int read;
            while ((read = reader.read(buffer)) != -1) {
                body.append(buffer, 0, read);
            }
            jsonObj = body.length()!=0? new JSONObjEx(body.toString()):null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObj;
    };


    public static Function<HttpExchange,RequestData> convertIncomingRequest = httpExchange -> {

        return new RequestData(Optional.ofNullable(convertBody.apply(httpExchange.getRequestBody())),httpExchange.getRequestURI().getPath(),convertHeaders.apply(httpExchange.getRequestHeaders()),httpExchange.getRequestMethod());

    };
    public static BiConsumer<ResponseData,HttpExchange> formResponse=(response,httpExchange)->{

        try {
            response.getHeader().getMaps().entrySet().forEach(entry->httpExchange.getResponseHeaders().put(entry.getKey(),entry.getValue()));
            String body = (String) response.getBody().map(o->o.toString()).orElse("");
            httpExchange.sendResponseHeaders(response.getStatus(),body.length());
            if(body.trim().length()>0){
                try (OutputStream out = httpExchange.getResponseBody()) {
                    out.write(body.getBytes(Charset.defaultCharset()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            httpExchange.close();
        }

    };

}
