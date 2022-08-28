package me.stitch.pdf

import me.stitch.parser.ImgsParser
import org.junit.jupiter.api.Test
import java.io.File
import javax.imageio.ImageIO
import kotlin.test.assertEquals

class ImgsParserTest {
    @Test
    fun testResize(){
        val l = ImgsParser()
        val path = javaClass.getClassLoader().getResource("zergling.pdf").toURI()
        val p = PdfParser()
        val imgs = p.getImagesFromPDF(path)
        val resized = l.resize(imgs!!.elementAt(4), 200, 200)
        assertEquals(200, resized?.height)
    }
    @Test
    fun testColor(){
        val l = ImgsParser()
        val path = javaClass.getClassLoader().getResource("zergling.pdf").toURI()
        val p = PdfParser()
        val imgs = p.getImagesFromPDF(path)
        ImageIO.write(imgs!!.elementAt(1), "PNG", File("image_zzz.png"));
        var color = l.rgb(imgs!!.elementAt(1).getRGB(40, 30))
        assertEquals(53, color[0])
        assertEquals(14, color[1])
        assertEquals(71, color[2])
    }
    @Test
    fun testLegends(){
        val l = ImgsParser()
        val path = javaClass.getClassLoader().getResource("zergling.pdf").toURI()
        val p = PdfParser()
        val imgs = p.getImagesFromPDF(path)
        val legends = l.legends(imgs!!.elementAt(1))
        assertEquals(17, legends.size)
    }
}