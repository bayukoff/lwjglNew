#version 330 core
precision highp float;
in vec3 v_VertexPosition;
in vec3 v_Normal;
in vec2 v_TexCoords;
in vec3 v_fragPosition;

out vec4 FragColor;

uniform vec3 color;
uniform sampler2D textureSampler;
uniform float u_lightStrength;
uniform vec3 u_ambientLightColor;
uniform vec3 u_diffuseLightColor;
uniform vec3 u_lightPosition;
uniform vec3 u_cameraPosition;

//Material flags
uniform bool u_hasColor;
uniform bool u_hasTexture;

vec3 createLight(vec3 color){
    float specularStrength = 0.5;
    vec3 viewDirection = normalize(u_cameraPosition - v_fragPosition);
    vec3 ambient = u_ambientLightColor * u_lightStrength;
    vec3 normalizedNormal = normalize(v_Normal);
    vec3 lightDirection = normalize(u_lightPosition - v_fragPosition);
    vec3 reflectVector = reflect(-lightDirection, normalizedNormal);
    float spec = pow(max(dot(viewDirection, reflectVector), 0.0), 32);
    vec3 specular = specularStrength * spec * u_diffuseLightColor;
    float diffAmount = max(dot(normalizedNormal, lightDirection), 0.0);
    vec3 diffuse = (diffAmount * u_diffuseLightColor);
    vec3 lightResult = (ambient + diffuse + specular) * color;
    return lightResult;
}

void main() {
    vec3 resultColor;
    if (!u_hasColor) {
        resultColor = vec3(1.0, 1.0, 1.0);
    }else{
        resultColor = color;
    }
    vec3 lightResult = createLight(resultColor);
    if (u_hasTexture){
        resultColor = resultColor * texture(textureSampler, v_TexCoords).rgb;
    }
    FragColor = vec4(resultColor * lightResult, 1.0);
}