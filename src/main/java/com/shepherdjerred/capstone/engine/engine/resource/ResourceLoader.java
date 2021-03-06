package com.shepherdjerred.capstone.engine.engine.resource;

public interface ResourceLoader<I extends ResourceIdentifier, R extends Resource> {

  R get(I identifier) throws Exception;
}
