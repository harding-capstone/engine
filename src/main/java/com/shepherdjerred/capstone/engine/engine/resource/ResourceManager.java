package com.shepherdjerred.capstone.engine.engine.resource;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles the loading and reference counting of resources. Useful to ensure that resources are
 * shared safely and cleaned up when possible.
 */
public class ResourceManager {

  private final Map<ResourceIdentifier, Resource> resourceCache;
  private final Map<ResourceIdentifier, Integer> referenceCounter;
  private final Map<Class<ResourceIdentifier>, ResourceLoader> loaders;

  public ResourceManager() {
    resourceCache = new HashMap<>();
    referenceCounter = new HashMap<>();
    loaders = new HashMap<>();
  }

  @SuppressWarnings("unchecked")
  public <R extends Resource, I extends ResourceIdentifier> void registerLoader(Class<I> resourceType,
      ResourceLoader<I, R> provider) {
    loaders.put((Class<ResourceIdentifier>) resourceType, provider);
  }

  @SuppressWarnings("unchecked")
  public <R extends Resource, I extends ResourceIdentifier> R get(I identifier) throws Exception {
    var currentReferences = referenceCounter.getOrDefault(identifier, 0);
    referenceCounter.put(identifier, currentReferences + 1);

    if (resourceCache.containsKey(identifier)) {
      return (R) resourceCache.get(identifier);
    } else {
      var loader = loaders.get(identifier.getClass());

      if (loader == null) {
        throw new UnsupportedOperationException("No loader exists for " + identifier);
      }

      var resource = loader.get(identifier);
      resourceCache.put(identifier, resource);
      return (R) resource;
    }
  }

  public void free(ResourceIdentifier identifier) {
    var references = referenceCounter.get(identifier) - 1;
    if (references < 0) {
      throw new IllegalStateException("Negative reference count");
    } else if (references == 0) {
      resourceCache.get(identifier).cleanup();
      resourceCache.remove(identifier);
      referenceCounter.remove(identifier);
    } else {
      referenceCounter.put(identifier, references);
    }
  }
}
