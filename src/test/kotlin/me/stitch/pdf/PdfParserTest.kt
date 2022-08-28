package me.stitch.pdf

import org.junit.jupiter.api.Test
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
    }
}