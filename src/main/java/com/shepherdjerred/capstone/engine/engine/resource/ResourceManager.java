package com.shepherdjerred.capstone.engine.engine.resource;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;

/**
 * Handles the loading and reference counting of resources. Useful to ensure that resources are
 * shared safely and cleaned up when possible.
 */
@Log4j2
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
    var currentReferences = referenceCounter.getOrDefault(identifier, 0) + 1;
    referenceCounter.put(identifier, currentReferences);

    log.info("Allocating " + identifier + ". New usage:" + currentReferences);

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
    log.info("Freeing " + identifier + ". New usage: " + references);
    if (references < 0) {
      throw new IllegalStateException("Negative reference count");
    } else if (references == 0) {
      log.info("Resource " + identifier + " no longer in use. Cleaning up.");
      resourceCache.get(identifier).cleanup();
      resourceCache.remove(identifier);
      referenceCounter.remove(identifier);
    } else {
      referenceCounter.put(identifier, references);
    }

  }
}
