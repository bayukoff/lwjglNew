layout(location = 0) in vec3 vertexPosition;

uniform mat4 projection;
uniform mat4 world;
uniform mat4 model;

out vec3 frVertexPosition;

void main(){
    frVertexPosition = vertexPosition;
    gl_Position = model * vec4(vertexPosition, 1.0);
}