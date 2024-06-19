#version 330 core

in vec2 vTexCoords;
out vec4 fragColor;

uniform vec4 customColor;
uniform sampler2D texture0;
uniform sampler2D texture1;
uniform sampler2D texture2;
uniform sampler2D texture3;
uniform sampler2D texture4;
uniform sampler2D texture5;
uniform sampler2D texture6;
uniform sampler2D texture7;
uniform sampler2D texture8;

void main(){
    ivec2 texSize = textureSize(texture0, 0);
    ivec2 texCoordsInt = ivec2(vTexCoords * texSize);
//    vec4 firstTexture = texture(texture0, oTexCoords);
//    vec4 secondTexture = texture(texture1, oTexCoords);
//
//    vec4 texel1 = texelFetch(texture0, texCoordsInt, 0);
//    vec4 texel2 = texelFetch(texture1, texCoordsInt, 0);
//    if(texel2.rgb != vec3(0,0,0)){
//        texel1.rgb = texel2.rgb;
//    }
//    fragColor = texel1 + customColor;
      vec4 texel = texelFetch(texture0, texCoordsInt, 0);
      if (texel.a == 0) {
          discard;
      }
          fragColor = texel;

}