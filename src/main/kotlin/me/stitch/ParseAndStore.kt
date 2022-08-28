package me.stitch

import me.stitch.db.ImgData
import me.stitch.parser.ImgsParser
import me.stitch.pdf.PdfParser


fun main() {
    val filename = "pixel1"
    val l = ImgsParser()
    val path = l.javaClass.getClassLoader().getResource("${filename}.pdf").toURI()
    val p = PdfParser()
    val imgs = p.getImagesFromPDF(path)
    val legends = l.legends(imgs!!.elementAt(1))
    val service = ImgData()
    service.storeImgs(filename, imgs)
    service.storeLegend(filename, legends)
}
