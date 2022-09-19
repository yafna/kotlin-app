package me.stitch.db

import javafx.scene.image.Image
import me.stitch.pojo.LegendItem
import me.stitch.pojo.LegendItemToStore
import me.stitch.parser.ImgsParser
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.util.Arrays
import javax.imageio.ImageIO


class ImgData {
    private val preview = "preview_"
    private val colorTemplate = "color_"
    private val numTemplate = "num_"
    private val patternTemplate = "pattern_"
    private val png = "png"
    private val directory = "storage"

    fun storeImgs(prefix: String, imgs: Set<BufferedImage>) {
        val dir = File("$directory/$prefix/data")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val l = ImgsParser()
        var index = 0
        for (img in imgs) {
            if (index > 1) {
                val ind = index - 2;
                ImageIO.write(img, png, File(dir, "$ind.$png"))
                ImageIO.write(l.resize(img, 200, 200), png, File(dir, "$preview$ind.$png"))
            }
            index += 1
        }
    }

    fun storeLegend(prefix: String, items: List<LegendItemToStore>) {
        val dir = File("$directory/$prefix/legend")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        for (item in items) {
            Files.write(
                File(dir, "$colorTemplate${item.index}").toPath(),
                Arrays.asList("${item.rgb.red}", "${item.rgb.green}", "${item.rgb.blue}")
            )
            ImageIO.write(item.number, png, File(dir, "$numTemplate${item.index}.$png"))
            ImageIO.write(item.pattern, png, File(dir, "$patternTemplate${item.index}.$png"))
        }
    }

    fun listImgs(prefix: String): List<Pair<Image, Image>> {
        val res = ArrayList<Pair<Image, Image>>()
        val dir = File("$directory/$prefix/data")
        var index = 0
        while (File(dir, "$index.${png}").exists()) {
            res.add(
                Pair(
                    Image(FileInputStream(File(dir, "$preview$index.${png}"))),
                    Image(FileInputStream(File(dir, "$index.${png}")))
                )
            )
            index += 1
        }
        return res
    }

    fun listLegends(prefix: String): List<LegendItem> {
        val res = ArrayList<LegendItem>()
        val dir = File("$directory/$prefix/legend")
        if (!dir.exists()) {
            throw IllegalStateException("store data first")
        }
        var index = 0
        while (File(dir, "${numTemplate}${index}.${png}").exists()) {
            val colorStrs = Files.readAllLines(File(dir, "${colorTemplate}${index}").toPath())
            res.add(
                LegendItem(
                    index, Color(colorStrs[0].toInt(), colorStrs[1].toInt(), colorStrs[2].toInt()),
                    Image(FileInputStream(File(dir, "${patternTemplate}${index}.${png}"))),
                    Image(FileInputStream(File(dir, "${numTemplate}${index}.${png}")))
                )
            )
            index += 1
        }
        return res
    }
}