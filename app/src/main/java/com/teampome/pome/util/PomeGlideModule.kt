package com.teampome.pome.util

import android.content.Context
import android.graphics.drawable.PictureDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.caverock.androidsvg.SVG
import com.teampome.pome.util.svg.SvgDecoder
import com.teampome.pome.util.svg.SvgDrawableTranscoder
import java.io.InputStream

@GlideModule
class PomeGlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry
            .register(SVG::class.java, PictureDrawable::class.java, SvgDrawableTranscoder())
            .append(InputStream::class.java, SVG::class.java, SvgDecoder())
    }
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}