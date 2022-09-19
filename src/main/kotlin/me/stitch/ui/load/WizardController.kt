package me.stitch.ui.load

import javafx.beans.binding.BooleanExpression
import javafx.beans.property.SimpleObjectProperty

import me.stitch.parser.PdfParser
import tornadofx.Controller
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.IIOException
import javax.imageio.ImageIO
import mu.KotlinLogging
import java.util.LinkedHashSet

private val logger = KotlinLogging.logger {}
class WizardController : Controller() {

    //TODO merge it
    val loadAsPdf = SimpleObjectProperty(true)
    val loadAsImg = SimpleObjectProperty(false)
    var imgs = SimpleObjectProperty<Set<BufferedImage>>(null)
    val loadComplete: BooleanExpression = BooleanExpression.booleanExpression(imgs.isNotNull)

    fun loadPdf(file: File) {
//        println(file.absoluteFile)
//        val l = ImgsParser()
        val p = PdfParser()
        imgs.value = p.getImagesFromPDF(file.toURI())
//        val legends = l.legends(imgs!!.elementAt(1))
    }

    fun loadImgsDir(file: File) {
        val images: MutableSet<BufferedImage> = LinkedHashSet()
        file.walk().forEach {
            try {
                if (it.isFile)
                    images.add(ImageIO.read(it))
            } catch (ex: IIOException) {
                logger.debug("${it.absolutePath} not an image")
            } catch (ex: Exception){
                logger.error(ex){}
            }
        }
        imgs.value = images
    }

    fun parsePages(isSelected: Boolean) {
        println(isSelected)
    }
}