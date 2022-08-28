package me.stitch.ui

import javafx.beans.property.SimpleObjectProperty
import javafx.scene.image.Image
import me.stitch.db.ImgData
import tornadofx.Controller

class MainController : Controller() {
    val prefix = "pixel1"
    val imgs = ImgData().listImgs(prefix)
    val currentImageProperty = SimpleObjectProperty(imgs[0].second)
    val resizedWProperty = SimpleObjectProperty(imgs[0].second.width * 1.0)
    val resizedHProperty = SimpleObjectProperty(imgs[0].second.height * 1.0)

    fun allImgs(): List<Pair<Image, Image>> {
        return imgs
    }
}






