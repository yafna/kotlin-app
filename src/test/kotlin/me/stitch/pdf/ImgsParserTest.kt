package me.stitch.pdf

import me.stitch.parser.ImgsParser
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ImgsParserTest {
    @Test
    fun testColor(){
        val l = ImgsParser()
        val path = javaClass.getClassLoader().getResource("pixel1.pdf").toURI()
        val p = PdfParser()
        val imgs = p.getImagesFromPDF(path)
        var color = l.rgb(imgs!!.elementAt(1).getRGB(40, 30))
        assertEquals(209, color[0])
        assertEquals(208, color[1])
        assertEquals(204, color[2])
    }
    @Test
    fun testLegends(){
        val l = ImgsParser()
        val path = javaClass.getClassLoader().getResource("pixel1.pdf").toURI()
        val p = PdfParser()
        val imgs = p.getImagesFromPDF(path)
        val legends = l.legends(imgs!!.elementAt(1))
        assertEquals(35, legends.size)
    }
}