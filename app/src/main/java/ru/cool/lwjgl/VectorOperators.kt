package ru.cool.lwjgl

import org.joml.Vector2d
import org.joml.Vector2f
import org.joml.Vector3d
import org.joml.Vector3f

/*
    Float vector
 */

operator fun Vector3f.plus(other: Vector3f): Vector3f{
    return this.add(other)
}

operator fun Vector3f.minus(other: Vector3f): Vector3f{
    return this.div(other)
}

operator fun Vector3f.minus(scalar: Float): Vector3f{
    return this.div(scalar)
}

operator fun Vector3f.div(other: Vector3f): Vector3f{
    return this.div(other)
}

operator fun Vector3f.div(scalar: Float): Vector3f{
    return this.div(scalar)
}

operator fun Vector3f.times(other: Vector3f): Vector3f{
    return this.mul(other)
}

operator fun Vector3f.times(scalar: Float): Vector3f{
    return this.mul(scalar)
}

/*
    Double vector
 */

operator fun Vector3d.plus(other: Vector3d): Vector3d{
    return this.add(other)
}

operator fun Vector3d.minus(other: Vector3d): Vector3d{
    return this.div(other)
}

operator fun Vector3d.minus(scalar: Double): Vector3d{
    return this.div(scalar)
}

operator fun Vector3d.div(other: Vector3d): Vector3d{
    return this.div(other)
}

operator fun Vector3d.div(scalar: Double): Vector3d{
    return this.div(scalar)
}

operator fun Vector3d.times(other: Vector3d): Vector3d{
    return this.mul(other)
}

operator fun Vector3d.times(scalar: Double): Vector3d{
    return this.mul(scalar)
}

/*
    Vector2 double
 */

operator fun Vector2d.plus(other: Vector2d): Vector2d{
    return this.add(other)
}

operator fun Vector2d.minus(other: Vector2d): Vector2d{
    return this.div(other)
}

operator fun Vector2d.minus(scalar: Double): Vector2d{
    return this.div(scalar)
}

operator fun Vector2d.div(other: Vector2d): Vector2d{
    return this.div(other)
}

operator fun Vector2d.div(scalar: Double): Vector2d{
    return this.div(scalar)
}

operator fun Vector2d.times(other: Vector2d): Vector2d{
    return this.mul(other)
}

operator fun Vector2d.times(scalar: Double): Vector2d{
    return this.mul(scalar)
}

/*
    Vector2 float
 */

operator fun Vector2f.plus(other: Vector2f): Vector2f{
    return this.add(other)
}

operator fun Vector2f.minus(other: Vector2f): Vector2f{
    return this.div(other)
}

operator fun Vector2f.minus(scalar: Float): Vector2f{
    return this.div(scalar)
}

operator fun Vector2f.div(other: Vector2f): Vector2f{
    return this.div(other)
}

operator fun Vector2f.div(scalar: Float): Vector2f{
    return this.div(scalar)
}

operator fun Vector2f.times(other: Vector2f): Vector2f{
    return this.mul(other)
}

operator fun Vector2f.times(scalar: Float): Vector2f{
    return this.mul(scalar)
}