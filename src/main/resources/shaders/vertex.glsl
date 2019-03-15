#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 inTextureCoordinate;

out vec2 outTextureCoordinate;

uniform mat4 projectionMatrix;
uniform mat4 modelMatrix;

void main() {
    gl_Position = projectionMatrix * modelMatrix * vec4(position, 1.0);
    outTextureCoordinate = inTextureCoordinate;
}
