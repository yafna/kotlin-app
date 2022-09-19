package me.stitch.parser

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDResources
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.net.URI


class PdfParser() {

    @Throws(IOException::class)
    fun getImagesFromPDF(path: URI): Set<BufferedImage>? {
        val images: MutableSet<BufferedImage> = LinkedHashSet()
        val doc = PDDocument.load(File(path))
        for (page in doc.pages) {
            images.addAll(getImagesFromResources(page.resources)!!)
        }
        doc.close()
        return images
    }

    @Throws(IOException::class)
    private fun getImagesFromResources(resources: PDResources): Set<BufferedImage>? {
        val images: MutableSet<BufferedImage> = LinkedHashSet()
        for (xObjectName in resources.xObjectNames) {
            val xObject = resources.getXObject(xObjectName)
            if (xObject is PDFormXObject) {
                images.addAll(getImagesFromResources(xObject.resources)!!)
            } else if (xObject is PDImageXObject) {
                images.add(xObject.image)
            }
        }
        return images
    }
}