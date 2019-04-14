@file:Suppress("UNCHECKED_CAST")
@file:JvmName("RibExtensions")

package com.uber.rib.core

fun Bundle.toAndroidBundle(): android.os.Bundle {
    return this.wrappedBundle
}