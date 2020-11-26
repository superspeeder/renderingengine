#version 450 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 aNormal;

out vec3 normal;
out vec3 fPos;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

void main() {
    gl_Position = projection * view * model * vec4(position,1.0f);
    normal = mat3(transpose(inverse(model))) * aNormal;
    fPos = vec3(model * vec4(position, 1.0f));
}