package ru.cool.lwjgl_kotlin.loaders

import org.joml.*
import org.lwjgl.assimp.*
import org.lwjgl.assimp.Assimp.*
import org.lwjgl.system.MemoryUtil
import ru.cool.lwjgl_kotlin.animation.*
import ru.cool.lwjgl_kotlin.exceptions.IncorrectModelFileException
import ru.cool.lwjgl_kotlin.geometry.AbstractGeometry
import ru.cool.lwjgl_kotlin.geometry.BoneGeometry
import ru.cool.lwjgl_kotlin.geometry.CustomGeometry
import ru.cool.lwjgl_kotlin.loaders.types.Vertex
import ru.cool.lwjgl_kotlin.objects.*
import ru.cool.lwjgl_kotlin.utils.VertexDataHandler
import java.io.FileNotFoundException
import java.util.*

object ModelLoader {

    private val nodes = ArrayDeque<Pair<AINode, SceneObject>>()

    private lateinit var rootModel: Model
    private var childrens = mutableListOf<SceneObject>()
    private var skeleton: Skeleton? = null
    private val allBones = hashMapOf<String, Bone>()

    fun loadModel(path: String): Model{
        val flags = aiProcess_JoinIdenticalVertices or aiProcess_Triangulate or aiProcess_FixInfacingNormals or aiProcess_PopulateArmatureData
        val scene = aiImportFile(path, flags) ?: throw FileNotFoundException("Can't find model file $path")
        val rootNode = scene.mRootNode() ?: throw IncorrectModelFileException("Can't load model file")
        rootModel = Model(rootNode.mName().dataString(), buildGeometryArrayFromRawMesh(rootNode, scene)).apply {
            setMatrix(getMatrixFromNode(rootNode))
        }
        traversalNodes(rootNode, scene, rootModel)
        if (skeleton != null)
            rootModel.skeleton = skeleton
        val numAnimations = scene.mNumAnimations()
        if (numAnimations > 0){
            lateinit var animator: Animator
            val animations = setAnimations(scene, numAnimations).toMutableList()
            if (skeleton != null)
                animator = SkeletonAnimator(skeleton!!, animations)
            else
                animator = SceneObjectAnimator(animations)
            Animators.add(rootModel, animator)
        }
        aiReleaseImport(scene)
        return rootModel
    }

    private fun createObject(node: AINode, scene: AIScene): SceneObject{
        val numMeshes = node.mNumMeshes()
        val geometry = arrayOfNulls<AbstractGeometry>(numMeshes)
        for (i in 0 until numMeshes){
            val mesh = AIMesh.create(scene.mMeshes()!!.get(i))
            val numBones = mesh.mNumBones()
            var meshBones: MutableList<Bone>? = null
            var hasBones = false
            if (numBones > 0){
                hasBones = true
                meshBones = mutableListOf()
                for (j in 0 until numBones){
                    val aiBone = AIBone.create(mesh.mBones()!!.get(j))
                    val numWeights = aiBone.mNumWeights()
                    val boneName = aiBone.mName().dataString()
                    lateinit var bone: Bone
                    if (skeleton != null){
                        if (skeleton!!.bones.isNotEmpty()){
                            val existingBone = skeleton!!.findBoneByName(boneName)
                            bone = existingBone ?: Bone(boneName)
                        }
                        else{
                            bone = Bone(boneName).apply { boneId = j }
                        }
                    }else{
                        skeleton = Skeleton()
                        bone = Bone(boneName)
                    }
                    val weights = arrayOfNulls<Float>(numWeights)
                    val verticesId = arrayOfNulls<Int>(numWeights)
                    if (numWeights > 0){
                        for (w in 0 until numWeights){
                            val weight = aiBone.mWeights().get(w)
                            weights[w] = weight.mWeight()
                            verticesId[w] = weight.mVertexId()
                        }
                    }
                    bone.offsetMatrix = getMatrix(aiBone.mOffsetMatrix())
                    bone.weights = weights.requireNoNulls()
                    bone.verticesId = verticesId.requireNoNulls()
                    allBones[boneName] = bone
                    meshBones.add(bone)
                }
            }
            geometry[i] = if (hasBones) buildBoneGeometryFromRawMesh(mesh, meshBones!!.toTypedArray()) else buildGeometryFromRawMesh(mesh)
        }
        return Mesh(node.mName().dataString(), geometry.requireNoNulls())
    }

    private fun getAllBonesFromNode(rootNode: AINode, index: Int){
        if (skeleton == null)
            skeleton = Skeleton()
        lateinit var rootBone: Bone
        val boneName = rootNode.mName().dataString()
        if (allBones.containsKey(boneName)){
            rootBone = allBones[boneName]!!
        }else{
            rootBone = Bone(boneName).apply {
                startMatrix = getMatrixFromNode(rootNode)
                boneId = index
            }
            allBones[boneName] = rootBone
        }
        val q = ArrayDeque<Pair<AINode, Bone>>()
        q.addLast(rootNode to rootBone)
        while (q.isNotEmpty()) {
            val (node, bone) = q.removeFirst()
            val numChild = node.mNumChildren()
            if (numChild > 0) {
                for (i in 0 until numChild) {
                    val child = AINode.create(node.mChildren()!![i])
                    val childName = child.mName().dataString()
                    lateinit var childBone: Bone
                    if (allBones.containsKey(childName)) {
                        childBone = allBones[childName]!!
                    } else {
                        childBone = Bone(childName).apply { boneId = index + i }
                        allBones[childName] = childBone
                    }
                    childBone.parentBone = bone
                    bone.children.add(childBone)
                    if (child.mNumChildren() > 0)
                        q.addLast(child to childBone)
                }
            }
        }
        skeleton!!.bones.add(rootBone)
    }


    private fun traversalNodes(rootNode: AINode, scene: AIScene, sceneObject: SceneObject){
        val numChildren = rootNode.mNumChildren()
        var mesh: SceneObject?
        for (childrenIndex in 0 until numChildren){
            val node = AINode.create(rootNode.mChildren()!!.get(childrenIndex))
            if (node.mNumMeshes() == 0){
                getAllBonesFromNode(node, childrenIndex)
                continue
            }else{
                mesh = createObject(node, scene)
                val localMatrix = getMatrixFromNode(node)
                mesh.setMatrix(localMatrix)
                mesh.parent = sceneObject
                childrens += mesh
            }
            val numChildrenOfNode = node.mNumChildren()
            if (numChildrenOfNode > 0){
                nodes.addLast(node to mesh)
            }
        }
        sceneObject.children = childrens.toTypedArray()
        if (nodes.size > 0){
            val nodeAndMesh = nodes.removeFirst()
            val node = nodeAndMesh.first
            val mesh1 = nodeAndMesh.second
            traversalNodes(node, scene, mesh1)
        }
    }

    private fun buildGeometryArrayFromRawMesh(aiNode: AINode, scene: AIScene): Array<AbstractGeometry>{
        val geometryArray = arrayOfNulls<AbstractGeometry>(aiNode.mNumMeshes())
        val numMeshes = aiNode.mNumMeshes()
        for(index in 0 until numMeshes){
            val aiMesh = AIMesh.create(scene.mMeshes()!!.get(aiNode.mMeshes()!!.get(index)))
            geometryArray[index] = buildGeometryFromRawMesh(aiMesh)
        }
        return geometryArray.requireNoNulls()
    }

    private fun buildGeometryFromRawMesh(aiMesh: AIMesh): AbstractGeometry{
        val vertices = VertexDataHandler.getVertices(aiMesh)
        val normals = VertexDataHandler.getNormals(aiMesh)
        val textureCoords = VertexDataHandler.getTexCoords(aiMesh)
        val indices = VertexDataHandler.getIndices(aiMesh)
        return CustomGeometry(vertices, normals, textureCoords, indices)
    }

    private fun associateVerticesWithBones(vertices: Array<Vertex>, bones: Array<Bone>): Pair<Array<Vector4i>, Array<Vector4f>>{
        val vertexPerBones = mutableMapOf<Int, Pair<MutableList<Int>, MutableList<Float>>>()
        for (vertexId in vertices.indices){
            val bonesList = mutableListOf<Int>()
            val weightList = mutableListOf<Float>()
            for (bone in bones){
                for ((index, boneVertexId) in bone.verticesId.withIndex()){
                    if (boneVertexId == vertexId){
                        bonesList += bone.boneId
                        weightList += bone.weights[index]
                        break
                    }
                }
            }
            vertexPerBones[vertexId] = bonesList to weightList
        }
        return createVectors(vertexPerBones)
    }

    private fun createVectors(vertexPerBones: MutableMap<Int, Pair<MutableList<Int>, MutableList<Float>>>): Pair<Array<Vector4i>, Array<Vector4f>>{
        val bonesIndices = Array(vertexPerBones.size){Vector4i()}
        val weights = Array(vertexPerBones.size){Vector4f()}
        for ((index, vertexId) in vertexPerBones.keys.withIndex()){
            val bonesId = vertexPerBones[vertexId]?.first
            val weight = vertexPerBones[vertexId]?.second
            val bonesIndicesVector = Vector4i()
            val weightVector = Vector4f()
            if (bonesId != null){
                bonesIndicesVector.x = bonesId.getOrElse(0){0}
                bonesIndicesVector.y = bonesId.getOrElse(1){0}
                bonesIndicesVector.z = bonesId.getOrElse(2){0}
                bonesIndicesVector.w = bonesId.getOrElse(3){0}
            }
            if (weight != null){
                weightVector.x = weight.getOrElse(0){0f}
                weightVector.y = weight.getOrElse(1){0f}
                weightVector.z = weight.getOrElse(2){0f}
                weightVector.w = weight.getOrElse(3){0f}
            }
            bonesIndices[index] = bonesIndicesVector
            weights[index] = weightVector
        }
        return bonesIndices to weights
    }

    private fun buildBoneGeometryFromRawMesh(aiMesh: AIMesh, bones: Array<Bone>): BoneGeometry {
        val vertices = VertexDataHandler.getVertices(aiMesh)
        val normals = VertexDataHandler.getNormals(aiMesh)
        val textureCoords = VertexDataHandler.getTexCoords(aiMesh)
        val indices = VertexDataHandler.getIndices(aiMesh)
        val (boneIndices, boneWeights) = associateVerticesWithBones(vertices, bones)
        println(boneIndices.contentToString())
        return BoneGeometry(vertices, normals, textureCoords, indices, boneIndices, boneWeights)
    }


    private fun setAnimations(scene: AIScene, numAnimations: Int): Array<Animation>{
        val animationsArray = arrayOfNulls<Animation>(numAnimations)
        for (animIdx in 0 until numAnimations){
            val aiAnimation = AIAnimation.create(scene.mAnimations()!!.get(animIdx))
            val animation = Animation(aiAnimation.mName().dataString())
            animation.duration = aiAnimation.mDuration()
            val numChannels = aiAnimation.mNumChannels()
            val animationNodes = arrayOfNulls<AnimationNode>(numChannels)
            if (numChannels > 0){
                for (channelIdx in 0 until numChannels){
                    val aiNodeAnim = AINodeAnim.create(aiAnimation.mChannels()!!.get(channelIdx))
                    val nodeName = aiNodeAnim.mNodeName().dataString()
                    val aiTranslateKeys = aiNodeAnim.mPositionKeys()
                    val aiScalingKeys = aiNodeAnim.mScalingKeys()
                    val aiRotationKeys = aiNodeAnim.mRotationKeys()
                    val translationKeys: Array<VectorKey>? = getTransformKeys(aiTranslateKeys)
                    val scalingKeys: Array<VectorKey>? = getTransformKeys(aiScalingKeys)
                    val rotationKeys: Array<QuaternionKey>? = getQuaternionKeys(aiRotationKeys)
                    val animationNode = AnimationNode(nodeName,
                        aiNodeAnim.mNumPositionKeys(),
                        aiNodeAnim.mNumScalingKeys(),
                        aiNodeAnim.mNumRotationKeys(),
                        translationKeys, scalingKeys, rotationKeys,
                        findTransformableObject(nodeName)
                    )
                    animationNodes[channelIdx] = animationNode
                }
                animation.animationNodes = animationNodes.requireNoNulls()
            }
            animationsArray[animIdx] = animation
        }
        return animationsArray.requireNoNulls()
    }

    private fun findTransformableObject(objectName: String): TransformableObject?{
        var someTransformableObject: TransformableObject? = null
        someTransformableObject = rootModel.findObjectByName(objectName)
        return someTransformableObject
            ?: if (skeleton != null){
                someTransformableObject = skeleton!!.findBoneByName(objectName)
                someTransformableObject
            }else{
                println("Не удалось найти объект для анимационной ноды!")
                null
            }
    }

    private fun getQuaternionKeys(aiQuaternionKey: AIQuatKey.Buffer?): Array<QuaternionKey>? {
        if (aiQuaternionKey == null)
            return null
        val amountKeys = aiQuaternionKey.capacity()
        if (amountKeys <= 0)
            return null
        val keysArray = arrayOfNulls<QuaternionKey>(amountKeys)
        for (key in 0 until amountKeys){
            val aiKey = aiQuaternionKey.get(key)
            keysArray[key] = QuaternionKey(Quaternionf(aiKey.mValue().x(),aiKey.mValue().y(),aiKey.mValue().z(),aiKey.mValue().w()), aiKey.mTime())
        }
        return keysArray.requireNoNulls()
    }

    private fun getTransformKeys(aiTransformKey: AIVectorKey.Buffer?): Array<VectorKey>?{
        if (aiTransformKey == null)
            return null
        val amountKeys = aiTransformKey.capacity()
        if (amountKeys <= 0)
            return null
        val keysArray = arrayOfNulls<VectorKey>(amountKeys)
        for (key in 0 until amountKeys){
            val aiKey = aiTransformKey.get(key)
            keysArray[key] = VectorKey(Vector3f(aiKey.mValue().x(),aiKey.mValue().y(),aiKey.mValue().z()), aiKey.mTime())
        }
        return keysArray.requireNoNulls()
    }

    private fun getMatrixFromNode(rootNode: AINode): Matrix4f{
        return getMatrix(rootNode.mTransformation())
    }

    private fun getMatrix(aiMatrix4x4: AIMatrix4x4): Matrix4f{
        val rootMatrixBuffer = MemoryUtil.memFloatBuffer(aiMatrix4x4.address(), 16)
        return Matrix4f(rootMatrixBuffer).transpose()
    }


}