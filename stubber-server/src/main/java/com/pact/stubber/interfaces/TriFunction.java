package com.pact.stubber.interfaces;

@FunctionalInterface
public interface TriFunction<T,T1,T2,T3>{
    T3 apply(T var,T1 var1,T2 var2);
}