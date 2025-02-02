#version 330 core

in vec3 frVertexPosition;

out vec4 FragColor;

void main(){
    FragColor = vec4(abs(frVertexPosition), 1.0);
}