package com.peihua8858.roundview.compiler

import javax.tools.JavaFileManager.Location


enum class AndroidResourceLocation : Location {
    RESOURCE_LOCATION;
    override fun getName(): String {
        return "res"
    }

    override fun isOutputLocation(): Boolean {
        return true
    }

    override fun isModuleOrientedLocation(): Boolean {
        return true
    }
}