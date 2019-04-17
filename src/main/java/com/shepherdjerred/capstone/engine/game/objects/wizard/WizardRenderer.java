package com.shepherdjerred.capstone.engine.game.objects.wizard;

import com.shepherdjerred.capstone.common.player.Element;
import com.shepherdjerred.capstone.engine.engine.graphics.RendererCoordinate;
import com.shepherdjerred.capstone.engine.engine.graphics.matrices.ModelMatrix;
import com.shepherdjerred.capstone.engine.engine.graphics.mesh.Mesh;
import com.shepherdjerred.capstone.engine.engine.graphics.mesh.TexturedMesh;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderProgram;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderProgramName;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderUniform;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.Texture;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.spritesheet.Spritesheet;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.spritesheet.SpritesheetCoordinate;
import com.shepherdjerred.capstone.engine.engine.object.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.engine.object.SceneObjectDimensions;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.objects.wizard.Wizard.SpriteState;
import com.shepherdjerred.capstone.engine.game.objects.wizard.Wizard.State;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class WizardRenderer implements GameObjectRenderer<Wizard> {

  private final ResourceManager resourceManager;
  private final Map<SpriteState, TexturedMesh> meshes;
  private ShaderProgram shaderProgram;

  public WizardRenderer(ResourceManager resourceManager) {
    this.resourceManager = resourceManager;
    this.meshes = new HashMap<>();
  }

  @Override
  public void initialize(Wizard wizard) throws Exception {
    var dimensions = wizard.getSceneObjectDimensions();
    shaderProgram = resourceManager.get(ShaderProgramName.DEFAULT);

    var element = wizard.getElement();

    createMeshForElementState(element, State.STILL, dimensions);
    createMeshForElementState(element, State.CASTING, dimensions);
    createMeshForElementState(element, State.WALKING_LEFT, dimensions);
    createMeshForElementState(element, State.WALKING_RIGHT, dimensions);
    createMeshForElementState(element, State.WALKING_UP, dimensions);
    createMeshForElementState(element, State.WALKING_DOWN, dimensions);
  }

  private void createMeshForElementState(Element element,
      State state,
      SceneObjectDimensions dimensions) throws Exception {
    var width = dimensions.getWidth();
    var height = dimensions.getHeight();

    var mapper = new WizardTextureMapper();
    var stateTextureName = mapper.getTexture(element, state);
    Texture stateTexture = resourceManager.get(stateTextureName);

    var walkingUpTextureSheet = new Spritesheet(stateTexture.getWidth(),
        stateTexture.getHeight(),
        32);

    for (int i = 0; i < walkingUpTextureSheet.getNumberOfVerticalTextures(); i++) {
      var vertices = new float[] {
          0, 0, 0,
          0, height, 0,
          width, 0, 0,
          width, height, 0
      };

      var textureCoordinates = walkingUpTextureSheet.getCoordinatesForSprite(new SpritesheetCoordinate(
          0,
          i)).asFloatArray();

      var indices = new int[] {
          0, 1, 2,
          3, 1, 2
      };

      var mesh = new Mesh(vertices, textureCoordinates, indices);
      var texturedMesh = new TexturedMesh(mesh, stateTexture);
      var spriteState = new SpriteState(state, i);

      meshes.put(spriteState, texturedMesh);
    }
  }

  @Override
  public void render(WindowSize windowSize, Wizard wizard) {
    var pos = wizard.getPosition()
        .getSceneCoordinate(windowSize, wizard.getSceneObjectDimensions());
    var model = new ModelMatrix(new RendererCoordinate(pos.getX(), pos.getY(), pos.getZ()),
        0,
        1).getMatrix();

    shaderProgram.bind();
    shaderProgram.setUniform(ShaderUniform.MODEL_MATRIX, model);

    var mesh = meshes.get(wizard.getSpriteState());
    mesh.render();
  }

  @Override
  public void cleanup() {
    meshes.values().forEach(texturedMesh -> {
      resourceManager.free(texturedMesh.getTexture().getTextureName());
      texturedMesh.getMesh().cleanup();
    });
    resourceManager.free(shaderProgram.getShaderProgramName());
  }
}
