package com.pact.stubber.util;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ListUtils {

    public static <R,T> Function<List<T>,List<R>> map(Function<T,R> convert){
        return list->{
            List<R> convertedList = Collections.emptyList();
            if(list!=null && !list.isEmpty()){
                for(T t : list){
                    convertedList.add(convert.apply(t));
                }
            }
            return convertedList;
        };

    }


    public static <T> Function<List<List<T>>,List<T>> flatMap(List<List<T>> inList){
        return list->{
            List<T> convertList = Collections.emptyList();
            if(inList!=null && !inList.isEmpty()){
                for(List<T> k : inList){
                    convertList.addAll(k);
                }
            }
            return convertList;
        };

    }

    public static <K,V,T> Function<List<T>, Map<K,V>> convertMap(List<T> inList,Function<T,K> keyFunction,Function<T,V> valueFunction){
        return list->{
            Map<K,V> mymap = Collections.emptyMap();
            if(list!=null && !list.isEmpty()){
                for(T t : list){
                    mymap.put(keyFunction.apply(t),valueFunction.apply(t));
                }
            }
            return mymap;
        };
    }

}
