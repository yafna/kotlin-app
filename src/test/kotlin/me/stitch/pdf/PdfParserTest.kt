package me.stitch.pdf

import org.junit.jupiter.api.Test
import java.io.File
import javax.imageio.ImageIO
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class PdfParserTest {
    @Test
    fun testSingleSuccessTest() {
        val p = PdfParser()
        val path = javaClass.getClassLoader().getResource("zergling.pdf").toURI()
        val imgs = p.getImagesFromPDF(path)
        assertNotNull(imgs)
        assertEquals(8, imgs.size)
        var k: Int = 0
        for( img in imgs){
            ImageIO.write(img, "PNG", File("image_$k.png"));
            k += 1
        }
    }
}