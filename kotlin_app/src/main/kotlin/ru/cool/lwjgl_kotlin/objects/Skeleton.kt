package ru.cool.lwjgl_kotlin.objects

import org.joml.Matrix4f

class Skeleton {
    var bones: MutableList<Bone> = mutableListOf()

    fun getBoneMatrices(): MutableList<Matrix4f> {
        val matrices = MutableList(100) { Matrix4f() }

        fun recurse(bone: Bone) {
            matrices[bone.boneId] = bone.calculateFinalMatrix()
            for (child in bone.children) recurse(child)
        }

        for (rootBone in bones) {
            recurse(rootBone)
        }

        return matrices
    }

    fun findBoneByName(name: String): Bone? {
        for (rootBone in bones) {
            val found = findBoneRecursive(rootBone, name)
            if (found != null) return found
        }
        return null
    }

    fun traverseBones(bone: Bone, block: (Bone) -> Unit = { }) {
        block(bone)  // вызываем для текущей кости
        if (bone.children.isNotEmpty()) {
            bone.children.forEach { child ->
                traverseBones(child, block)  // рекурсивно проходим по детям
            }
        }
    }

    private fun findBoneRecursive(bone: Bone, name: String): Bone? {
        if (bone.name == name) return bone
        bone.children.forEach { child ->
            val found = findBoneRecursive(child, name)
            if (found != null) return found
        }
        return null
    }
}