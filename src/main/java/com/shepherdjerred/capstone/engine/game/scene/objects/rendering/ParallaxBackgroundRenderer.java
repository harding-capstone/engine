package com.shepherdjerred.capstone.engine.game.scene.objects.rendering;

import com.shepherdjerred.capstone.engine.engine.graphics.RendererCoordinate;
import com.shepherdjerred.capstone.engine.engine.graphics.matrices.ModelMatrix;
import com.shepherdjerred.capstone.engine.engine.graphics.matrices.ProjectionMatrix;
import com.shepherdjerred.capstone.engine.engine.graphics.mesh.Mesh;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderProgram;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderProgramName;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderUniform;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.Texture;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.scene.objects.ParallaxBackground;
import java.util.SortedMap;
import java.util.TreeMap;

public class ParallaxBackgroundRenderer implements
    GameObjectRenderer<ParallaxBackground> {

  private final ResourceManager resourceManager;
  private Mesh mesh;
  private final SortedMap<Integer, Texture> textureMap;
  private WindowSize windowSize;
  private ShaderProgram shaderProgram;

  public ParallaxBackgroundRenderer(ResourceManager resourceManager, WindowSize windowSize) {
    this.resourceManager = resourceManager;
    this.windowSize = windowSize;
    this.textureMap = new TreeMap<>();
  }

  @Override
  public void init(ParallaxBackground sceneElement) throws Exception {
    var width = windowSize.getWidth();
    var height = windowSize.getHeight();

    var type = sceneElement.getType();

    shaderProgram = resourceManager.get(ShaderProgramName.DEFAULT);

    switch (type) {
      case PURPLE_MOUNTAINS:
        var pma = (Texture) resourceManager.get(TextureName.PURPLE_MOUNTAINS_A);
        var pmb = (Texture) resourceManager.get(TextureName.PURPLE_MOUNTAINS_B);
        var pmc = (Texture) resourceManager.get(TextureName.PURPLE_MOUNTAINS_C);
        var pmd = (Texture) resourceManager.get(TextureName.PURPLE_MOUNTAINS_D);
        var pme = (Texture) resourceManager.get(TextureName.PURPLE_MOUNTAINS_E);

        textureMap.put(1, pma);
        textureMap.put(2, pmb);
        textureMap.put(3, pmc);
        textureMap.put(4, pmd);
        textureMap.put(5, pme);
        break;
      default:
        throw new UnsupportedOperationException(sceneElement.getType().toString());
    }

    var vertices = new float[] {
        0, 0, 0,
        0, height, 0,
        width, 0, 0,
        width, height, 0
    };

    var textureCoordinates = new float[] {
        0, 0,
        0, 1,
        1, 0,
        1, 1
    };

    var indices = new int[] {
        0, 1, 2,
        3, 1, 2
    };

    mesh = new Mesh(vertices, textureCoordinates, indices);
  }

  @Override
  public void render(ParallaxBackground sceneElement) {
    var model = new ModelMatrix(new RendererCoordinate(0, 0, 0), 0, 1).getMatrix();
    var projection = new ProjectionMatrix(windowSize).getMatrix();

    shaderProgram.bind();
    shaderProgram.setUniform(ShaderUniform.MODEL_MATRIX, model);
    shaderProgram.setUniform(ShaderUniform.PROJECTION_MATRIX, projection);
    // TODO move the mesh.. somehow
    sceneElement.getPositions().forEach((instance, layers) -> {
      layers.forEach((layer, position) -> {
        var texture = textureMap.get(layer);
        texture.bind();
        mesh.render();
      });
    });
    shaderProgram.unbind();
  }

  @Override
  public void cleanup() {
  }
}
