package ru.cool.lwjgl.textures;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import ru.cool.lwjgl.utils.BuffersUtil;

import java.io.File;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Texture {
    private final String texturePath;
    private int textureIndex;
    private ByteBuffer imageData;
    private IntBuffer width, height, channels;
    private boolean isAlpha = false;
    public Texture(String texturePath) {
        this.texturePath = texturePath;
    }

    public Texture loadTexture(boolean isAlpha){
        this.isAlpha = isAlpha;
        File textureFile = new File(getClass().getClassLoader().getResource(this.texturePath).getPath());

        width = BufferUtils.createIntBuffer(1);
        height = BufferUtils.createIntBuffer(1);
        channels = BufferUtils.createIntBuffer(1);
        STBImage.stbi_info(textureFile.getPath(), width,height,channels);
        if (!textureFile.exists()) {
            System.err.println("Cannot load texture!");
            return this;
        }
        int numberChannels = isAlpha ? 4 : 3;
        STBImage.stbi_set_flip_vertically_on_load(true);
        imageData = STBImage.stbi_load(textureFile.getPath(), width, height, channels, numberChannels);
        if(imageData == null){
            System.err.println("Error when loading texture");
        }
        return this;
    }

    public void createTexture(){
        this.textureIndex = GL30.glGenTextures();
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureIndex);
        int colors = this.isAlpha ? GL30.GL_RGBA : GL30.GL_RGB;
        GL30.glTexImage2D(GL30.GL_TEXTURE_2D, 0, colors, this.width.get(0),this.height.get(0),0,colors, GL30.GL_UNSIGNED_BYTE, this.imageData);
        GL30.glGenerateMipmap(GL30.GL_TEXTURE_2D);
        GL30.glTexParameterIi(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_S, GL30.GL_MIRRORED_REPEAT);
        GL30.glTexParameterIi(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_T, GL30.GL_MIRRORED_REPEAT);
        GL30.glTexParameterIi(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_NEAREST_MIPMAP_NEAREST);
        GL30.glTexParameterIi(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_NEAREST);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);
    }

    public void bindTexture(){
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureIndex);
    }

    public void unbindTexture(){
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);
    }

    public String getTexturePath() {
        return texturePath;
    }

    public int getTextureIndex() {
        return textureIndex;
    }

    public byte getImageData() {
        return imageData.get();
    }

    public int getWidth() {
        return width.get();
    }

    public int getHeight() {
        return height.get();
    }

    public int getChannels() {
        return channels.get();
    }
}
