package com.pact.parse.implementation.constants;

import com.pact.parse.dto.*;
import com.pact.parse.dto.payload.JSONObjEx;
import com.pact.parse.implementation.InteractionParse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONPointer;


import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MyFunctions {

    public static Function<JSONObjEx, Map<String, List>> convertMap = json->{
        Map<String,List> mapHeaders = new HashMap();
        Map<String, Object> headers = json.toMap();
        headers.entrySet().forEach(entry->{
            mapHeaders.put(entry.getKey().toUpperCase(), Arrays.asList(((String)entry.getValue()).split(":")));
        });

        return mapHeaders;

    };

    public static Function<JSONObjEx,HeaderObj> getRequestHeader = json->{

        Object o = new JSONPointer(JsonPath.requestHeader).queryFrom(json);
        JSONObjEx jsonObj = o!=null?new JSONObjEx((JSONObject)o):null;
        return new HeaderObj(convertMap.apply(jsonObj!=null?jsonObj:new JSONObjEx(JsonPath.emptyJSON)));
    };

    public static Function<JSONObjEx,HeaderObj> getResponseHeader = json->{
        Object o = new JSONPointer(JsonPath.responseHeader).queryFrom(json);
        JSONObjEx jsonObj = o!=null?new JSONObjEx((JSONObject)o):null;
        return new HeaderObj(convertMap.apply(jsonObj!=null?jsonObj:new JSONObjEx(JsonPath.emptyJSON)));
    };


    public static Function<JSONObjEx,String> getMethod = json->{

        JSONPointer jsonPointer = new JSONPointer(JsonPath.requestMethod);
        return (String) jsonPointer.queryFrom(json);

    };
    public static Function<JSONObjEx,String> getUrl = json->{

        JSONPointer jsonPointer = new JSONPointer(JsonPath.requestPath);
        return (String) jsonPointer.queryFrom(json);

    };
    public static Function<JSONObjEx,Integer> getStatus = json->{

        JSONPointer jsonPointer = new JSONPointer(JsonPath.responseStatus);
        return (Integer) jsonPointer.queryFrom(json);

    };

    public static Function<JSONObjEx,JSONObjEx> getRequestBody = json ->{
        JSONPointer jsonPointer = new JSONPointer(JsonPath.requestBodyPath);
        Object o = jsonPointer.queryFrom(json);
        return o!=null?new JSONObjEx((JSONObject)o):null;
    };

    public static Function<JSONObjEx,JSONObjEx> getResponseBody = json ->{
        JSONPointer jsonPointer = new JSONPointer(JsonPath.responseBodyPath);
        Object o = jsonPointer.queryFrom(json);
        return o!=null?new JSONObjEx((JSONObject)o):null;
    };

    public static Function<JSONObjEx,String> getProviderName = json-> {
        JSONPointer jsonPointer = new JSONPointer(JsonPath.consumerPath);
        return (String) jsonPointer.queryFrom(json);
    };
    public static Function<JSONObjEx,String> getConsumerName = json->{
        JSONPointer jsonPointer = new JSONPointer(JsonPath.providerPath);
        return (String) jsonPointer.queryFrom(json);
    };

    public static Function<JSONObjEx, JSONArray> convert = json->{
        JSONPointer jsonPointer = new JSONPointer(JsonPath.interactionPath);
        return (JSONArray) jsonPointer.queryFrom(json);
    };

    public static Function<JSONArray, List<JSONObjEx>> jsonArrayMap = (array)->{
        List<JSONObjEx> arrayObj = new ArrayList<JSONObjEx>();
        if(array!=null && !array.isEmpty()){
            for(int i = 0 ; i < array.length() ; i++){
                arrayObj.add(new JSONObjEx((JSONObject) array.get(i)));
            }
        }
        return arrayObj;
    };

    public static Function<JSONObjEx,Interaction<JSONObjEx>> convertInteractions = json->{
        InteractionParse parse = new InteractionParse();
        return new Interaction<JSONObjEx>(new RequestData<JSONObjEx>(Optional.ofNullable(parse.getBodyRequest(json)),parse.getUrl(json),parse.getHeadersRequest(json),parse.getMethod(json))
                ,new ResponseData<JSONObjEx>(parse.getStatus(json),Optional.ofNullable(parse.getBodyResponse(json)),parse.getHeadersResponse(json)));
    };

    public static Function<JSONArray,InteractionDTO<JSONObjEx>> convertObj = (array)-> new InteractionDTO(jsonArrayMap.apply(array).stream().map(obj->convertInteractions.apply(obj)).collect(Collectors.toList()));


    public static Function<JSONObjEx, InteractionDTO<JSONObjEx>> getInteractions = json-> convert.andThen(convertObj).apply(json);


    public static BiPredicate<JSONObjEx,JSONObjEx> jsonCompare = (superObj,subObj)->true;

}
