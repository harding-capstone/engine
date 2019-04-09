#version 330 core

in vec2 outTextureCoordinate;
out vec4 outColor;

uniform sampler2D texture_sampler;
uniform vec3 textColor;

// https://learnopengl.com/In-Practice/Text-Rendering
void main() {
    float alpha = texture(texture_sampler, outTextureCoordinate).r;
    vec4 sampled = vec4(1.0, 1.0, 1.0, alpha);
    outColor = vec4(textColor, 1.0) * sampled;
}
