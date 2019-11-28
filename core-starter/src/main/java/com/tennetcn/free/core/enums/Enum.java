package com.tennetcn.free.core.enums;

public interface Enum<K,V>{
    public abstract K getKey();

    public abstract V getValue();
}