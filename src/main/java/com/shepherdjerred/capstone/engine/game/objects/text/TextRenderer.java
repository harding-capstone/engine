package com.shepherdjerred.capstone.engine.game.objects.text;

import com.shepherdjerred.capstone.engine.engine.graphics.RendererCoordinate;
import com.shepherdjerred.capstone.engine.engine.graphics.font.Font;
import com.shepherdjerred.capstone.engine.engine.graphics.matrices.ModelMatrix;
import com.shepherdjerred.capstone.engine.engine.graphics.mesh.Mesh;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderProgram;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderProgramName;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderUniform;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.object.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TextRenderer implements GameObjectRenderer<Text> {

  private final ResourceManager resourceManager;
  private Font font;
  private ShaderProgram textShaderProgram;
  private Map<Integer, Mesh> characterMeshMap;

  public TextRenderer(ResourceManager resourceManager) {
    this.resourceManager = resourceManager;
    characterMeshMap = new HashMap<>();
  }

  @Override
  public void init(Text gameObject) throws Exception {
    font = resourceManager.get(gameObject.getFontName());
    textShaderProgram = resourceManager.get(ShaderProgramName.TEXT);
    var chars = gameObject.getText().toCharArray();

    var currX = 0;
    var currY = 0;
    for (int i = 0; i < chars.length; i++) {
      char c = chars[i];

      var fontChar = font.getFontCharacter(c, currX, currY);

      var vertices = fontChar.getCoordinates().toFloatArray();

      var textureCoordinates = fontChar.getTextureCoordinates().toFloatArray();

      var indices = new int[] {
          0, 1, 2,
          3, 1, 2
      };

      var mesh = new Mesh(vertices, textureCoordinates, indices);
      characterMeshMap.put(i, mesh);

      if (c == 32) {
        currX += 12;
      } else {
        currX += fontChar.getWidth() + 2;
      }
    }
  }

  @Override
  public void render(WindowSize windowSize, Text sceneElement) {
    textShaderProgram.bind();
    font.bind();

    var pos = sceneElement.getPosition()
        .getSceneCoordinate(windowSize, sceneElement.getWidth(), sceneElement.getHeight());
    var model = new ModelMatrix(new RendererCoordinate(pos.getX(), pos.getY(), pos.getZ()),
        0,
        1).getMatrix();

    textShaderProgram.setUniform(ShaderUniform.MODEL_MATRIX, model);
    textShaderProgram.setUniform(ShaderUniform.TEXT_COLOR,
        sceneElement.getColor().toRgbFloatArray());

    char[] textCharacters = sceneElement.getText().toCharArray();
    for (int i = 0; i < textCharacters.length; i++) {
      var mesh = characterMeshMap.get(i);
      mesh.render();
    }

    font.unbind();
    textShaderProgram.unbind();
  }

  @Override
  public void cleanup() {
    resourceManager.free(font.getFontName());
    resourceManager.free(textShaderProgram.getShaderProgramName());
    characterMeshMap.values().forEach(Mesh::cleanup);
  }
}
