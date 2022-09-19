package me.stitch.pojo

import javafx.scene.image.Image

data class ImageStitchPage(override val fromx: Int, override val fromy: Int, val field: Image) : StitchPage()