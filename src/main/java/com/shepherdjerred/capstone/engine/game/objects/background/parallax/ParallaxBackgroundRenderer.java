package com.shepherdjerred.capstone.engine.game.objects.background.parallax;

import com.shepherdjerred.capstone.engine.engine.graphics.RendererCoordinate;
import com.shepherdjerred.capstone.engine.engine.graphics.matrices.ModelMatrix;
import com.shepherdjerred.capstone.engine.engine.graphics.mesh.Mesh;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderProgram;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderProgramName;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderUniform;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.Texture;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.object.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import java.util.SortedMap;
import java.util.TreeMap;
import lombok.extern.log4j.Log4j2;

@Log4j2
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
  public void init(ParallaxBackground gameObject) throws Exception {
    var width = windowSize.getWidth();
    var height = windowSize.getHeight();

    var type = gameObject.getType();

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
      case PLAINS:
        var pa = (Texture) resourceManager.get(TextureName.PLAINS_A);
        var pb = (Texture) resourceManager.get(TextureName.PLAINS_B);
        var pc = (Texture) resourceManager.get(TextureName.PLAINS_C);
        var pd = (Texture) resourceManager.get(TextureName.PLAINS_D);
        var pe = (Texture) resourceManager.get(TextureName.PLAINS_E);

        textureMap.put(1, pa);
        textureMap.put(2, pb);
        textureMap.put(3, pc);
        textureMap.put(4, pd);
        textureMap.put(5, pe);
        break;
      default:
        throw new UnsupportedOperationException(gameObject.getType().toString());
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
  public void render(WindowSize windowSize, ParallaxBackground gameObject) {
    shaderProgram.bind();

    gameObject.getInstances().forEach((instance, layers) -> {
      layers.forEach((layer, position) -> {
        var xpos = position * windowSize.getWidth();
        var model = new ModelMatrix(new RendererCoordinate(xpos, 0, layer),
            0,
            1).getMatrix();
        shaderProgram.setUniform(ShaderUniform.MODEL_MATRIX, model);
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
