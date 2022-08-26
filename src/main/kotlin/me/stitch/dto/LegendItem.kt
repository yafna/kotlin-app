package me.stitch.dto

import java.awt.Color
import java.awt.image.BufferedImage

data class LegendItem(val index: Int, val rgb: Color, var pattern: BufferedImage, var number: BufferedImage)
