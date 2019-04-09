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
  private Map<Integer, Mesh> characterMeshMap;

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
    for (int i = 0; i < chars.length; i++) {
      char c = chars[i];
//      log.info(currX);

      var fontChar = font.getFontCharacter(c, currX, currY);

//      log.info(fontChar);

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
  public void render(Text sceneElement) {
    font.bind();
    char[] charArray = sceneElement.getText().toCharArray();
    for (int i = 0; i < charArray.length; i++) {
      char c = charArray[i];
      var mesh = characterMeshMap.get(i);
      mesh.render();
    }
  }

  @Override
  public void cleanup() {
  }
}