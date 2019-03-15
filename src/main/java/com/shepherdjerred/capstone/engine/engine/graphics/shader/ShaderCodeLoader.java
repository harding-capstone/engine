package com.shepherdjerred.capstone.engine.engine.graphics.shader;

import java.io.IOException;

public interface ShaderCodeLoader {

  String getShaderCode(String shaderName) throws IOException;
}
