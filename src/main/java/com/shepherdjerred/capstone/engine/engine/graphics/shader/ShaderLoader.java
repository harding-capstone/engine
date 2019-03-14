package com.shepherdjerred.capstone.engine.engine.graphics.shader;

import java.io.IOException;

public interface ShaderLoader {

  String loadShaderCode(String shaderName) throws IOException;
}
