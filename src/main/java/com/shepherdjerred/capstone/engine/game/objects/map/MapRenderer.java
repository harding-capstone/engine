package com.shepherdjerred.capstone.engine.game.objects.map;

import com.shepherdjerred.capstone.engine.engine.graphics.RendererCoordinate;
import com.shepherdjerred.capstone.engine.engine.graphics.matrices.ModelMatrix;
import com.shepherdjerred.capstone.engine.engine.graphics.mesh.Mesh;
import com.shepherdjerred.capstone.engine.engine.graphics.mesh.TexturedMesh;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderProgram;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderProgramName;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderUniform;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.Texture;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import com.shepherdjerred.capstone.engine.engine.map.Layer;
import com.shepherdjerred.capstone.engine.engine.map.MapTile;
import com.shepherdjerred.capstone.engine.engine.object.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MapRenderer implements GameObjectRenderer<MapObject> {

  private static final int TILE_DIMENSION = 2048;

  private final ResourceManager resourceManager;
  private ShaderProgram shaderProgram;
  private MapObject map;
  private final Map<MapTile, TexturedMesh> meshes;

  public MapRenderer(ResourceManager resourceManager) {
    this.resourceManager = resourceManager;
    meshes = new HashMap<>();
  }

  @Override
  public void initialize(MapObject map) throws Exception {
    this.map = map;
    shaderProgram = resourceManager.get(ShaderProgramName.DEFAULT);

    for (Layer layer : map.getMapLayers()) {
      for (MapTile tile : layer) {
        var vertices = new float[] {
            0, 0, 0,
            0, TILE_DIMENSION, 0,
            TILE_DIMENSION, 0, 0,
            TILE_DIMENSION, TILE_DIMENSION, 0
        };

        var indices = new int[] {
            0, 1, 2,
            3, 1, 2
        };

        var mesh = new Mesh(vertices, tile.getCoordinates().asIndexedFloatArray(), indices);
        var texture = (Texture) resourceManager.get(tile.getTextureName());
        var texturedMesh = new TexturedMesh(mesh, texture);

        log.info(String.format("T: %s, C: %s", texture.getTextureName(), tile.getCoordinates()));

        meshes.put(tile, texturedMesh);
      }
    }

  }

  @Override
  public void render(WindowSize windowSize, MapObject map) {
    shaderProgram.bind();

    map.getMapLayers().forEach(layer -> {
      log.info("STARTING LAYER " + layer.getZ());

      layer.forEach(tile -> {
        var x = tile.getCoordinate().getX();
        var y = tile.getCoordinate().getY();

//        log.info(String.format("Map Location: x: %s, y: %s", x, y));
//        log.info(String.format("Texture coords: %s", tile.getCoordinates()));

        var model = new ModelMatrix(new RendererCoordinate(x * TILE_DIMENSION,
            y * TILE_DIMENSION,
            layer.getZ()),
            0,
            1).getMatrix();

        shaderProgram.setUniform(ShaderUniform.MODEL_MATRIX, model);

        var mesh = meshes.get(tile);
        mesh.render();
      });
    });

    shaderProgram.unbind();

//    System.exit(0);
  }

  @Override
  public void cleanup() {
    for (TextureName textureName : map.getMapLayers().getTextureNames()) {
      resourceManager.free(textureName);
    }
    meshes.values().forEach(texturedMesh -> {
      resourceManager.free(texturedMesh.getTexture().getTextureName());
      texturedMesh.getMesh().cleanup();
      texturedMesh.getTexture().cleanup();
    });
  }
}
