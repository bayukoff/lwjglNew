package ru.cool.lwjgl_kotlin.loaders

data class Accessor(
    val bufferView: Int,
    val byteOffset: Int,
    val componentType: Int,
    val count: Int,
    val type: String,
    val name: String
    ) {

    override fun toString(): String {
        return "bufferView: $bufferView" +
                "byteOffset: $byteOffset, " +
                "componentType: $componentType, " +
                "count: $count, " +
                "type: $type" +
                "name: $name"
    }
}