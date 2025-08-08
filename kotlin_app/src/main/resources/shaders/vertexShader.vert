#version 330 core
precision highp float;
layout(location = 0) in vec3 a_vertexPosition;
layout(location = 1) in vec3 a_normal;
layout(location = 2) in vec2 a_texCoords;
layout(location = 3) in vec4 boneIds;
layout(location = 4) in vec4 boneWeights;

uniform mat4 u_projection;
uniform mat4 u_view;
uniform mat4 u_model;
uniform mat3 u_normalMatrix;
uniform mat4 u_boneMatrices[100];
uniform bool u_useSkinning;

out vec3 v_VertexPosition;
out vec3 v_Normal;
out vec2 v_TexCoords;
out vec3 v_fragPosition;

void main(){

    vec4 vertPos = vec4(a_vertexPosition, 1.0);
    vec3 normal = a_normal;
    if (u_useSkinning){
        mat4 skinMatrix =
            u_boneMatrices[int(boneIds.x)] * boneWeights.x +
            u_boneMatrices[int(boneIds.y)] * boneWeights.y +
            u_boneMatrices[int(boneIds.z)] * boneWeights.z +
            u_boneMatrices[int(boneIds.w)] * boneWeights.w;

        vertPos = vec4(skinMatrix * vertPos);
        normal = normalize(
            mat3(skinMatrix) * a_normal
        );
    }

    v_VertexPosition = a_vertexPosition;
    v_Normal = u_normalMatrix * normal;

    v_TexCoords = a_texCoords;
    gl_Position = vec4(u_projection * u_view * u_model * vertPos);
    v_fragPosition = vec3(u_model * vertPos);
}