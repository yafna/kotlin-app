package me.stitch.parser

import me.stitch.dto.LegendItem
import java.awt.Color
import java.awt.Image
import java.awt.image.BufferedImage


class ImgsParser {
    //    ..we know, that there is the grid
    //0-23  33-63 70+ 130 ?
    //480-503 513-543 550+ 610 ?
    //960-983 993-1023 1030+ 1090?
    //
    //25-55, 58-88, 91-
    fun legends(img: BufferedImage): List<LegendItem> {
        val res = ArrayList<LegendItem>()
        val x = intArrayOf(0, 480, 960)
        var ind = 0;
        for (xx in x) {
            var y = 25
            var color = rgb(img.getRGB(xx + 40, y))
            while (color[0] < 245 || color[1] < 245 || color[2] < 245) {
                var count = 0
                var endline = xx + 70
                for (i in (xx + 70)..(xx + 150)) {
                    var allW = true
                    for (j in y..(y + 30)) {
                        val check = rgb(img.getRGB(i, j))
                        if ((check[0] < 245) || (check[1] < 245) || (check[2] < 245)) {
                            allW = false
                        }
                    }
                    if (allW) {
                        count += 1
                    }
                    if (count > 3) {
                        endline = i - xx - 70 // we need width here
                        break
                    }
                }
                res.add(
                    LegendItem(
                        ind, Color(color[0], color[1], color[2]),
                        img.getSubimage(xx, y, 23, 30), img.getSubimage(xx + 70, y, endline, 30)
                    )
                )
                ind += 1
                y += 33
                color = rgb(img.getRGB(xx + 40, y))
            }

        }
        return res
    }

    fun rgb(pixel: Int): IntArray {
        val rgb = IntArray(4)
        rgb[3] = pixel shr 24 and 0xff
        rgb[0] = pixel shr 16 and 0xff
        rgb[1] = pixel shr 8 and 0xff
        rgb[2] = pixel and 0xff
        return rgb
    }

    fun resize(img: BufferedImage, newW: Int, newH: Int): BufferedImage? {
        val tmp: Image = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH)
        val dimg = BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB)
        val g2d = dimg.createGraphics()
        g2d.drawImage(tmp, 0, 0, null)
        g2d.dispose()
        return dimg
    }
}