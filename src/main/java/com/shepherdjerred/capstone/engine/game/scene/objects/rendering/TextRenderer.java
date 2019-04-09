package com.shepherdjerred.capstone.engine.game.scene.objects.rendering;

import com.shepherdjerred.capstone.engine.engine.graphics.font.Font;
import com.shepherdjerred.capstone.engine.engine.graphics.font.FontLoader;
import com.shepherdjerred.capstone.engine.engine.graphics.mesh.Mesh;
import com.shepherdjerred.capstone.engine.engine.scene.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.game.scene.objects.Text;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TextRenderer implements GameObjectRenderer<Text> {

  private final FontLoader fontLoader;
  private Font font;
  private Map<Character, Mesh> characterMeshMap;

  public TextRenderer(FontLoader fontLoader) {
    this.fontLoader = fontLoader;
    characterMeshMap = new HashMap<>();
  }

  @Override
  public void init(Text sceneElement) throws Exception {
    font = fontLoader.load(sceneElement.getFontName());
    var chars = sceneElement.getText().toCharArray();

    var currX = 0;
    var currY = 0;
    for (char c : chars) {
      if (characterMeshMap.containsKey(c)) {
        continue;
      }
//      log.info(currX);

      var fontChar = font.getFontCharacter(c, currX, currY);

//      log.info(fontChar);

      var vertices = new float[] {
          currX, currY, 0,
          currX, fontChar.getHeight(), 0,
          currX + fontChar.getWidth(), currY, 0,
          currX + fontChar.getWidth(), fontChar.getHeight(), 0
      };

      var textureCoordinates = fontChar.getTextureCoordinates().toFloatArray();

      var indices = new int[] {
          0, 1, 2,
          3, 1, 2
      };

      var mesh = new Mesh(vertices, textureCoordinates, indices);
      characterMeshMap.put(c, mesh);

      if (c == 32) {
        currX += 12;
      } else {
        currX += fontChar.getWidth() + 2;
      }
    }
  }

  @Override
  public void render(Text sceneElement) {
    font.bind();
    for (char c : sceneElement.getText().toCharArray()) {
      var mesh = characterMeshMap.get(c);
      mesh.render();
    }
  }

  @Override
  public void cleanup() {
  }
}
