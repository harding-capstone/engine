package com.shepherdjerred.capstone.engine.game.objects.button;

import com.shepherdjerred.capstone.engine.engine.graphics.RendererCoordinate;
import com.shepherdjerred.capstone.engine.engine.graphics.matrices.ModelMatrix;
import com.shepherdjerred.capstone.engine.engine.graphics.mesh.Mesh;
import com.shepherdjerred.capstone.engine.engine.graphics.mesh.TexturedMesh;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderProgram;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderProgramName;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderUniform;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.Texture;
import com.shepherdjerred.capstone.engine.engine.object.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.engine.object.ClickableAbstractGameObject.State;

public class ButtonRenderer implements GameObjectRenderer<Button> {

  private final ResourceManager resourceManager;
  private TexturedMesh normalMesh;
  private TexturedMesh hoveredMesh;
  private TexturedMesh clickedMesh;
  private ShaderProgram shaderProgram;

  public ButtonRenderer(ResourceManager resourceManager) {
    this.resourceManager = resourceManager;
  }

  @Override
  public void initialize(Button gameObject) throws Exception {
    var width = gameObject.getSceneObjectDimensions().getWidth();
    var height = gameObject.getSceneObjectDimensions().getHeight();

    var mapper = new ButtonTextureMapper();
    var textures = mapper.get(gameObject.getType());

    var normalTexture = (Texture) resourceManager.get(textures.getInactiveTexture());
    var hoveredTexture = (Texture) resourceManager.get(textures.getHoveredTexture());
    var clickedTexture = (Texture) resourceManager.get(textures.getClickedTexture());

    shaderProgram = resourceManager.get(ShaderProgramName.DEFAULT);

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

    var mesh = new Mesh(vertices, textureCoordinates, indices);

    normalMesh = new TexturedMesh(mesh, normalTexture);
    hoveredMesh = new TexturedMesh(mesh, hoveredTexture);
    clickedMesh = new TexturedMesh(mesh, clickedTexture);
  }

  @Override
  public void render(WindowSize windowSize, Button gameObject) {
    var pos = gameObject.getPosition()
        .getSceneCoordinate(windowSize, gameObject.getSceneObjectDimensions());
    var model = new ModelMatrix(new RendererCoordinate(pos.getX(), pos.getY(), pos.getZ()),
        0,
        1).getMatrix();

    shaderProgram.bind();
    shaderProgram.setUniform(ShaderUniform.MODEL_MATRIX, model);

    if (gameObject.getState() == State.INACTIVE) {
      normalMesh.render();
    } else if (gameObject.getState() == State.HOVERED) {
      hoveredMesh.render();
    } else if (gameObject.getState() == State.CLICKED) {
      clickedMesh.render();
    }

    shaderProgram.unbind();
  }

  @Override
  public void cleanup() {
    clickedMesh.getMesh().cleanup();
    hoveredMesh.getMesh().cleanup();
    normalMesh.getMesh().cleanup();
    resourceManager.free(normalMesh.getTexture().getTextureName());
    resourceManager.free(clickedMesh.getTexture().getTextureName());
    resourceManager.free(hoveredMesh.getTexture().getTextureName());
    resourceManager.free(shaderProgram.getShaderProgramName());
  }
}
