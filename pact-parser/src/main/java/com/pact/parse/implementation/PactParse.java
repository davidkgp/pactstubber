package com.pact.parse.implementation;

import com.pact.parse.dto.InteractionDTO;
import com.pact.parse.dto.payload.JSONObjEx;
import com.pact.parse.exception.PactParseException;
import com.pact.parse.implementation.constants.MyFunctions;
import com.pact.parse.interfaces.IPactParse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.function.Predicate;

public class PactParse implements IPactParse<JSONObjEx, InteractionDTO<JSONObjEx>> {

    private Function<File,String> convertFileToStringContent = file->{
        try {
            return new String(Files.readAllBytes(Paths.get(file.toURI())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    };

    private Function<String,JSONObjEx> convert = fileObj->new JSONObjEx(fileObj);

    private Predicate<File> filePredicate = fileObj->fileObj.isFile()&&fileObj.exists()&&fileObj.canRead();


    public JSONObjEx getJsonObj(File fileObj){
        return this.readFile(fileObj,convertFileToStringContent,convert,filePredicate).get();
    }


    @Override
    public String getProviderName(JSONObjEx obj) throws PactParseException {
        return MyFunctions.getProviderName.apply(obj);
    }

    @Override
    public String getConsumerName(JSONObjEx obj) throws PactParseException {
        return MyFunctions.getConsumerName.apply(obj);
    }

    @Override
    public InteractionDTO<JSONObjEx> getInteractions(JSONObjEx obj) throws PactParseException {
        return MyFunctions.getInteractions.apply(obj);
    }
}
