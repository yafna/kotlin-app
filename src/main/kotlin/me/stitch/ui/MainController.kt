package me.stitch.ui

import javafx.beans.property.SimpleObjectProperty
import javafx.scene.image.Image
import me.stitch.db.ImgData
import me.stitch.dto.LegendItem
import me.stitch.parser.ImgsParser
import tornadofx.Controller

class MainController : Controller() {
    val prefix = "pixel1"
    private val imgParser = ImgsParser()
    private val imgData = ImgData()
    private val imgs = imgData.listImgs(prefix)
    private val legends = imgData.listLegends(prefix)
    var workingImage: Image? = null
    var storedImage: Image? = imgs[0].second;
    val currentImageProperty = SimpleObjectProperty(imgs[0].second)
    val resizedWProperty = SimpleObjectProperty(imgs[0].second.width * 1.0)
    val resizedHProperty = SimpleObjectProperty(imgs[0].second.height * 1.0)
    val selectedLegend = SimpleObjectProperty<LegendItem>(null)

    fun legendClicked(legendItem: LegendItem) {
        if (selectedLegend.value != null && selectedLegend.value.index == legendItem.index) {
            selectedLegend.set(null)
            //restore
            currentImageProperty.set(storedImage)
        } else {
            selectedLegend.set(legendItem)
            currentImageProperty.set(imgParser.highlight(storedImage, legendItem))
        }
    }

    fun legends(): List<LegendItem> {
        return legends
    }

    fun pageSelected(img: Image){
        currentImageProperty.set(img)
        storedImage=img
    }
    fun allImgs(): List<Pair<Image, Image>> {
        return imgs
    }
}






