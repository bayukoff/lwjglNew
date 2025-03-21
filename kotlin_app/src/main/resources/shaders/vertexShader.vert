#version 330 core
precision highp float;
layout(location = 0) in vec3 a_vertexPosition;
layout(location = 1) in vec3 a_normal;
layout(location = 2) in vec2 a_texCoords;

uniform mat4 u_projection;
uniform mat4 u_view;
uniform mat4 u_model;
uniform mat3 u_normalMatrix;

out vec3 v_VertexPosition;
out vec3 v_Normal;
out vec2 v_TexCoords;
out vec3 v_fragPosition;

void main(){
    v_VertexPosition = a_vertexPosition;
    v_Normal = u_normalMatrix * a_normal;
    v_TexCoords = a_texCoords;
    gl_Position = u_projection * u_view * u_model * vec4(a_vertexPosition, 1.0);
    v_fragPosition = vec3(u_model * vec4(a_vertexPosition, 1.0));

}