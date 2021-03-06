package com.shepherdjerred.capstone.engine.game.objects.game.wall;

import com.shepherdjerred.capstone.engine.engine.graphics.RendererCoordinate;
import com.shepherdjerred.capstone.engine.engine.graphics.matrices.ModelMatrix;
import com.shepherdjerred.capstone.engine.engine.graphics.mesh.Mesh;
import com.shepherdjerred.capstone.engine.engine.graphics.mesh.TexturedMesh;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderProgram;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderProgramName;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderUniform;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.Texture;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import com.shepherdjerred.capstone.engine.engine.object.GameObjectRenderer;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;

public class WallRenderer implements GameObjectRenderer<Wall> {

  private final ResourceManager resourceManager;
  private TexturedMesh normalMesh;
  private ShaderProgram shaderProgram;

  public WallRenderer(ResourceManager resourceManager) {
    this.resourceManager = resourceManager;
  }

  @Override
  public void initialize(Wall gameObject) throws Exception {
    var width = gameObject.getSceneObjectDimensions().getWidth();
    var height = gameObject.getSceneObjectDimensions().getHeight();

    var normalTexture = (Texture) resourceManager.get(TextureName.GENERIC_BUTTON);

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
  }

  @Override
  public void render(WindowSize windowSize, Wall gameObject) {
    var pos = gameObject.getPosition()
        .getSceneCoordinate(windowSize, gameObject.getSceneObjectDimensions());
    var model = new ModelMatrix(new RendererCoordinate(pos.getX(), pos.getY(), pos.getZ()),
        0,
        1).getMatrix();

    shaderProgram.bind();
    shaderProgram.setUniform(ShaderUniform.MODEL_MATRIX, model);

    normalMesh.render();

    shaderProgram.unbind();
  }

  @Override
  public void cleanup() {
    normalMesh.getMesh().cleanup();
    resourceManager.free(normalMesh.getTexture().getTextureName());
    resourceManager.free(shaderProgram.getShaderProgramName());
  }
}
