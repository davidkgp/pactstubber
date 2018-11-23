package com.pact.parse.interfaces;

import com.pact.parse.exception.PactParseException;

import java.io.File;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public interface IPactParse<J,I> {

    default Optional<J> readFile(File fileObj, Function<File,String> convert,Function <String,J> convert2,Predicate<File> filePredicate){
        return filePredicate.test(fileObj)?Optional.ofNullable(convert.andThen(convert2).apply(fileObj)):Optional.empty();
    }

    public String getProviderName(J obj) throws PactParseException;

    public String getConsumerName(J obj) throws PactParseException;

    public I getInteractions(J obj) throws PactParseException;

    /*public String getProviderName(J obj, Function<J,String> convert) throws PactParseException;

    public String getConsumerName(J obj, Function<J,String> convert) throws PactParseException;

    public I getInteractions(J obj,Function<J,I> convert) throws PactParseException;*/


}
