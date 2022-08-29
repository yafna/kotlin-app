package me.stitch.parser

import javafx.scene.image.Image
import javafx.scene.image.WritableImage
import me.stitch.dto.LegendItem
import me.stitch.dto.LegendItemToStore
import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.math.abs


class ImgsParser {
    //    ..we know, that there is the grid
    //0-23  33-63 70+ 130 ?
    //480-503 513-543 550+ 610 ?
    //960-983 993-1023 1030+ 1090?
    //
    //25-55, 58-88, 91-
    fun legends(img: BufferedImage): List<LegendItemToStore> {
        val res = ArrayList<LegendItemToStore>()
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
                    LegendItemToStore(
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
        val dimg = BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB)
        val g2d = dimg.createGraphics()
        g2d.drawImage(img.getScaledInstance(newW, newH, java.awt.Image.SCALE_SMOOTH), 0, 0, null)
        g2d.dispose()
        return dimg
    }

    fun findSquare(img: Image, x: Int, y: Int, width: Int, height: Int, legends: List<LegendItem>): Int {
        val sums = DoubleArray(legends.size)
        for (i in 0 until width) {
            for (j in 0 until height) {
                for (indl in 0 until legends.size)
                    sums[indl] += distance(
                        img.pixelReader.getColor(i + x, j + y),
                        legends.get(indl).pattern.pixelReader.getColor(i, j)
                    )
            }
        }
        var min = 0;
        for (indl in 1 until legends.size) {
            if (sums[min] > sums[indl]) {
                min = indl
            }
        }
        return min
    }


    fun distance(color: javafx.scene.paint.Color, c: javafx.scene.paint.Color): Double {
        return abs(c.red - color.red) + abs(c.green - color.green) + abs(c.blue - color.blue)
    }

    fun isSquare(img: Image, x: Int, y: Int, width: Int, height: Int, legend: LegendItem): Boolean {
        var sum = 0.0;
        for (i in 0 until width) {
            for (j in 0 until height) {
                sum += distance(
                    img.pixelReader.getColor(i + x, j + y),
                    legend.pattern.pixelReader.getColor(i, j)
                )
            }
        }
        return sum < 10.0
    }

    fun indexImage(img: Image, legends: List<LegendItem>): ArrayList<ArrayList<Int>> {
        val iw = ((img.width - 47) / 33).toInt()
        val ih = ((img.height - 44) / 33).toInt()
        var res = ArrayList<ArrayList<Int>>()
        for (x in 0 until iw) {
            val line = ArrayList<Int>()
            for (y in 0 until ih) {
                line.add(findSquare(img, 47 + x * 33, 45 + y * 33, 23, 27, legends))
            }
            res.add(line)
        }
        return res
    }

    //TODO find a way to merge colors witout convertion
    fun or(a: javafx.scene.paint.Color, b: javafx.scene.paint.Color): javafx.scene.paint.Color {
        val aColor = Color(a.red.toFloat(), a.green.toFloat(), a.blue.toFloat(), a.opacity.toFloat())
        val bColor = Color(b.red.toFloat(), b.green.toFloat(), b.blue.toFloat(), b.opacity.toFloat())
        return javafx.scene.paint.Color.rgb(
            (aColor.red * 0.6 + bColor.red * 0.4).toInt(),
            (aColor.green * 0.6 + bColor.green * 0.4).toInt(),
            (aColor.blue * 0.6 + bColor.blue * 0.4).toInt(),
            a.opacity
        )
    }

    fun highlight(
        img: Image?,
        legendItem: LegendItem,
        color: javafx.scene.paint.Color = javafx.scene.paint.Color.CORAL
    ): WritableImage {
        if (img == null) {
            println("Not supposed to highlight non-selected image")
            throw IllegalStateException("empty image")
        }
        val result = WritableImage(img.width.toInt(), img.height.toInt())
        val resultWriter = result.pixelWriter

        for (x in 0 until img.width.toInt()) {
            for (y in 0 until img.height.toInt()) {
                resultWriter.setColor(x, y, img.pixelReader.getColor(x, y))
            }
        }
        val iw = ((img.width - 47) / 33).toInt()
        val ih = ((img.height - 44) / 33).toInt()
        for (x in 0 until iw) {
            for (y in 0 until ih) {
                if (isSquare(img, 47 + x * 33, 45 + y * 33, 23, 27, legendItem)) {
                    for (i in 47 + x * 33 until 72 + x * 33) {
                        for (j in 45 + y * 33 until 73 + y * 33) {
                            resultWriter.setColor(i, j, or(img.pixelReader.getColor(i, j), color))
                        }
                    }
                }
            }
        }
        return result
    }
}