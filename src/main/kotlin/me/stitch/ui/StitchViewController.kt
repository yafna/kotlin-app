package me.stitch.ui

import javafx.beans.property.DoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.image.Image
import me.stitch.db.ImgData
import me.stitch.dto.LegendItem
import me.stitch.parser.Direction
import me.stitch.parser.ImgsParser
import tornadofx.Controller

class StitchViewController : Controller() {
    val prefix = "pixel1"
    private val imgParser = ImgsParser()
    private val imgData = ImgData()
    private val imgs = imgData.listImgs(prefix)
    private val legends = imgData.listLegends(prefix)
    private var sliderProp: DoubleProperty? = null
    var workingImage: Image? = null
    var storedImage: Image? = imgs[0].second;
    val currentImageProperty = SimpleObjectProperty(imgs[0].second)
    val resizedWProperty = SimpleObjectProperty(imgs[0].second.width * 1.0)
    val resizedHProperty = SimpleObjectProperty(imgs[0].second.height * 1.0)
    val selectedLegend = SimpleObjectProperty<LegendItem>(null)

    fun sliderMove(e: DoubleProperty) {
        sliderProp = e
        resizedWProperty.set(currentImageProperty.value.width * e.value)
        resizedHProperty.set(currentImageProperty.value.height * e.value)
    }

    fun combineRightTop(item: Image) {
        workingImage = imgParser.combine(
            storedImage!!,
            item,
            Direction.RIGHT_TOP,
            storedImage!!.width.toInt(),
            storedImage!!.height.toInt()
        )
        currentImageProperty.set(workingImage)
        sliderProp?.let { sliderMove(it) }
    }

    fun combineLeftBottom(item: Image) {
        workingImage = imgParser.combine(
            workingImage!!,
            item,
            Direction.LEFT_BOTTOM,
            storedImage!!.width.toInt(),
            storedImage!!.height.toInt()
        )
        currentImageProperty.set(workingImage)
        sliderProp?.let { sliderMove(it) }
    }

    fun combineRightBottom(item: Image) {
        workingImage = imgParser.combine(
            workingImage!!,
            item,
            Direction.RIGHT_BOTTOM,
            storedImage!!.width.toInt(),
            storedImage!!.height.toInt()
        )
        currentImageProperty.set(workingImage)
        sliderProp?.let { sliderMove(it) }
    }

    fun drop() {
        currentImageProperty.set(storedImage)
        sliderProp?.let { sliderMove(it) }
        workingImage = null
    }

    fun loadPatterns() {
        //todo
    }

    fun saveState() {
        //todo
    }

    fun legendClicked(legendItem: LegendItem) {
        if (selectedLegend.value != null && selectedLegend.value.index == legendItem.index) {
            selectedLegend.set(null)
            if (workingImage == null) {
                currentImageProperty.set(storedImage)
            } else {
                currentImageProperty.set(workingImage)
            }
        } else {
            selectedLegend.set(legendItem)
            if (workingImage == null) {
                currentImageProperty.set(
                    imgParser.highlight(
                        storedImage, legendItem, storedImage!!.width.toInt(), storedImage!!.height.toInt()
                    )
                )
            } else {
                currentImageProperty.set(
                    imgParser.highlight(
                        workingImage, legendItem, storedImage!!.width.toInt(), storedImage!!.height.toInt()
                    )
                )
            }
        }
    }

    fun legends(): List<LegendItem> {
        return legends
    }

    fun pageSelected(img: Image) {
        currentImageProperty.set(img)
        sliderProp?.let { sliderMove(it) }
        workingImage = null
        storedImage = img
    }

    fun allImgs(): List<Pair<Image, Image>> {
        return imgs
    }
}






