package me.stitch.ui.load

import javafx.beans.binding.BooleanExpression
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.input.MouseEvent
import me.stitch.parser.PdfParser
import mu.KotlinLogging
import net.coobird.thumbnailator.Thumbnails
import tornadofx.Controller
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.IIOException
import kotlin.streams.toList

private val logger = KotlinLogging.logger {}

class WizardController : Controller() {
    val loadAsPdf = SimpleObjectProperty(true)
    val loadAsImg = SimpleObjectProperty(false)
    var imgs = SimpleObjectProperty<List<BufferedImage>>(listOf())
    val loadComplete: BooleanExpression = BooleanExpression.booleanExpression(imgs.isNotNull)

    fun loadPdf(file: File) {
        val p = PdfParser()
        imgs.value =p.getImagesFromPDF(file.toURI())?.stream()?.toList()
    }

    fun loadImgsDir(file: File) {
        val images: ArrayList<BufferedImage> = ArrayList()
        file.walk().forEach {
            try {
                if (it.isFile)
                    images.add(Thumbnails.of(it).asBufferedImage())
            } catch (ex: IIOException) {
                logger.debug("${it.absolutePath} not an image")
            } catch (ex: Exception) {
                logger.error(ex) {}
            }
        }
        imgs.value = images
        logger.info("has been loaded ${images.size} images ")
    }

    fun parsePages(isSelected: Boolean) {
        println(isSelected)
    }
}