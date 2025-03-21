package ru.cool.lwjgl.materials;

import ru.cool.lwjgl.shaders.ShaderProgram;
import ru.cool.lwjgl.shaders.Shaders;
import ru.cool.lwjgl.textures.Texture;

import java.util.ArrayList;
import java.util.List;

public class TextureMaterial implements IMaterial {

    private List<Texture> textures;
    public TextureMaterial(Texture texture) {
        getTextures().add(texture);
    }

    public TextureMaterial addTexture(Texture texture){
        getTextures().add(texture);
        return this;
    }

    public List<Texture> getTextures() {
        if (textures == null)
            textures = new ArrayList<>();
        return textures;
    }

    public void setTextures(List<Texture> textures) {
        this.textures = textures;
    }

    public void unbindTextures(){
        textures.get(0).unbindTexture();
    }
    @Override
    public void applyMaterial() {
        textures.get(0).bindTexture();
    }
}
