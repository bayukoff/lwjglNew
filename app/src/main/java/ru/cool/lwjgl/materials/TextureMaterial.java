package ru.cool.lwjgl.materials;

import ru.cool.lwjgl.textures.Texture;

import java.util.ArrayList;
import java.util.List;

public class TextureMaterial extends Material{
    private List<Texture> textures;

    public void addTexture(Texture texture){
        getTextures().add(texture);
    }

    public List<Texture> getTextures() {
        if (textures == null)
            textures = new ArrayList<>();
        return textures;
    }

    public void setTextures(List<Texture> textures) {
        this.textures = textures;
    }
}
