package me.stitch.pdf

import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.Image
import javafx.scene.paint.Color
import me.stitch.db.ImgData
import me.stitch.parser.Direction
import me.stitch.parser.ImgsParser
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileInputStream
import javax.imageio.ImageIO
import kotlin.test.assertEquals

class ImgsParserTest {
    @Test
    fun testColorMerging() {
        val l = ImgsParser()
        val test = l.or(Color.BLACK, Color.CORAL)
        assertEquals(Color.BLACK.red, test.red)
    }
@Test
fun combinetest(){
    val img = Image(FileInputStream(File(javaClass.getClassLoader().getResource("0.png").toURI())))
    val l = ImgsParser()
    val topline = l.combine(img, img, Direction.RIGHT_TOP, img.width.toInt(), img.height.toInt())
    val almostsqare =  l.combine(topline, img, Direction.LEFT_BOTTOM, img.width.toInt(), img.height.toInt())
    val square =  l.combine(almostsqare, img, Direction.RIGHT_BOTTOM, img.width.toInt(), img.height.toInt())
    ImageIO.write(SwingFXUtils.fromFXImage(topline, null), "png", File("sq1.png"))
    ImageIO.write(SwingFXUtils.fromFXImage(almostsqare, null), "png", File("sq2.png"))
    ImageIO.write(SwingFXUtils.fromFXImage(square, null), "png", File("sq3.png"))
    val data = ImgData()
    val leg = data.listLegends("pixel1")
    val himg = l.highlight(square, leg.get(12), img.width.toInt(), img.height.toInt())
    ImageIO.write(SwingFXUtils.fromFXImage(himg, null), "png", File("eee2.png"))
}

    @Test
    fun highlight() {
        val l = ImgsParser()
        val data = ImgData()
        val leg = data.listLegends("pixel1")
        val img = Image(FileInputStream(File(javaClass.getClassLoader().getResource("0.png").toURI())))
        val himg = l.highlight(img, leg.get(12), img.width.toInt(), img.height.toInt())
        val newimg = SwingFXUtils.fromFXImage(himg, null)
        ImageIO.write(newimg, "png", File("2.png"))
    }

    @Test
    fun testResize() {
        val l = ImgsParser()
        val path = javaClass.getClassLoader().getResource("zergling.pdf").toURI()
        val p = PdfParser()
        val imgs = p.getImagesFromPDF(path)
        val resized = l.resize(imgs!!.elementAt(4), 200, 200)
        assertEquals(200, resized?.height)
    }

    @Test
    fun testColor() {
        val l = ImgsParser()
        val path = javaClass.getClassLoader().getResource("zergling.pdf").toURI()
        val p = PdfParser()
        val imgs = p.getImagesFromPDF(path)
        ImageIO.write(imgs!!.elementAt(1), "PNG", File("image_zzz.png"));
        val color = l.rgb(imgs!!.elementAt(1).getRGB(40, 30))
        assertEquals(53, color[0])
        assertEquals(14, color[1])
        assertEquals(71, color[2])
    }

    @Test
    fun testImgAnalyzing() {
        val l = ImgsParser()
        val data = ImgData()
        val leg = data.listLegends("pixel1")
        val img = Image(FileInputStream(File(javaClass.getClassLoader().getResource("0.png").toURI())))
        val res = l.indexImage(img, leg)
        assertEquals(12, res.get(0).get(0))
    }

    @Test
    fun testLegends() {
        val l = ImgsParser()
        val path = javaClass.getClassLoader().getResource("zergling.pdf").toURI()
        val p = PdfParser()
        val imgs = p.getImagesFromPDF(path)
        val legends = l.legends(imgs!!.elementAt(1))
        assertEquals(17, legends.size)
    }
}