package com.pact.parse.implementation.constants;

import com.pact.parse.dto.*;
import com.pact.parse.implementation.InteractionParse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONPointer;


import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MyFunctions {

    public static Function<JSONObject, Map<String, List>> convertMap = json->{
        Map<String,List> mapHeaders = new HashMap();
        Map<String, Object> headers = json.toMap();
        headers.entrySet().forEach(entry->{
            mapHeaders.put(entry.getKey(), Arrays.asList(((String)entry.getValue()).split(":")));
        });

        return mapHeaders;

    };

    public static Function<JSONObject,HeaderObj> getRequestHeader = json->{
        JSONObject jsonObj = (JSONObject) new JSONPointer(JsonPath.requestHeader).queryFrom(json);
        return new HeaderObj(convertMap.apply(jsonObj!=null?jsonObj:new JSONObject(JsonPath.emptyJSON)));
    };

    public static Function<JSONObject,HeaderObj> getResponseHeader = json->{
        JSONObject jsonObj = (JSONObject) new JSONPointer(JsonPath.responseHeader).queryFrom(json);
        return new HeaderObj(convertMap.apply(jsonObj!=null?jsonObj:new JSONObject(JsonPath.emptyJSON)));
    };


    public static Function<JSONObject,String> getMethod = json->{

        JSONPointer jsonPointer = new JSONPointer(JsonPath.requestMethod);
        return (String) jsonPointer.queryFrom(json);

    };
    public static Function<JSONObject,String> getUrl = json->{

        JSONPointer jsonPointer = new JSONPointer(JsonPath.requestPath);
        return (String) jsonPointer.queryFrom(json);

    };
    public static Function<JSONObject,Integer> getStatus = json->{

        JSONPointer jsonPointer = new JSONPointer(JsonPath.responseStatus);
        return (Integer) jsonPointer.queryFrom(json);

    };

    public static Function<JSONObject,JSONObject> getRequestBody = json ->{
        JSONPointer jsonPointer = new JSONPointer(JsonPath.requestBodyPath);
        return (JSONObject) jsonPointer.queryFrom(json);
    };

    public static Function<JSONObject,JSONObject> getResponseBody = json ->{
        JSONPointer jsonPointer = new JSONPointer(JsonPath.responseBodyPath);
        return (JSONObject) jsonPointer.queryFrom(json);
    };

    public static Function<JSONObject,String> getProviderName = json-> {
        JSONPointer jsonPointer = new JSONPointer(JsonPath.consumerPath);
        return (String) jsonPointer.queryFrom(json);
    };
    public static Function<JSONObject,String> getConsumerName = json->{
        JSONPointer jsonPointer = new JSONPointer(JsonPath.providerPath);
        return (String) jsonPointer.queryFrom(json);
    };

    public static Function<JSONObject, JSONArray> convert = json->{
        JSONPointer jsonPointer = new JSONPointer(JsonPath.interactionPath);
        return (JSONArray) jsonPointer.queryFrom(json);
    };

    public static Function<JSONArray, List<JSONObject>> jsonArrayMap = (array)->{
        List<JSONObject> arrayObj = new ArrayList<JSONObject>();
        if(array!=null && !array.isEmpty()){
            for(int i = 0 ; i < array.length() ; i++){
                arrayObj.add((JSONObject) array.get(i));
            }
        }
        return arrayObj;
    };

    public static Function<JSONObject,Interaction<JSONObject>> convertInteractions = json->{
        InteractionParse parse = new InteractionParse();
        return new Interaction<JSONObject>(new RequestData<JSONObject>(Optional.ofNullable(parse.getBodyRequest(json)),parse.getUrl(json),parse.getHeadersRequest(json),parse.getMethod(json))
                ,new ResponseData<JSONObject>(parse.getStatus(json),Optional.ofNullable(parse.getBodyResponse(json)),parse.getHeadersResponse(json)));
    };

    public static Function<JSONArray,InteractionDTO<JSONObject>> convertObj = (array)->{
        return new InteractionDTO(jsonArrayMap.apply(array).stream().map(obj->convertInteractions.apply(obj)).collect(Collectors.toList()));
    };


    public static Function<JSONObject, InteractionDTO<JSONObject>> getInteractions = json-> {
        return convert.andThen(convertObj).apply(json);
    };
}
