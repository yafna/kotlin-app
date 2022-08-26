package me.stitch.db

import me.stitch.dto.LegendItem
import me.stitch.parser.ImgsParser
import me.stitch.pdf.PdfParser
import java.io.File
import javax.imageio.ImageIO


class LegendData {

    private val directory = "storage"
    fun store(prefix: String, items: List<LegendItem>) {
        val dir = File("$directory/$prefix")
        if(!dir.exists()){
            dir.mkdirs()
        }
        for(item in items){
            ImageIO.write(item.number, "PNG", File("num_${item.index}.png"));
        }

    }

    fun list(): List<LegendItem> {
        val l = ImgsParser()
        val path = javaClass.getClassLoader().getResource("pixel1.pdf")?.toURI()
        val p = PdfParser()
        val imgs = path?.let { p.getImagesFromPDF(it) }
        return l.legends(imgs!!.elementAt(1))
    }


}