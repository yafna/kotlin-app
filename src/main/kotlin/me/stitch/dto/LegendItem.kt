package me.stitch.dto

import javafx.scene.image.Image
import java.awt.Color

data class LegendItem(val index: Int, val rgb: Color, var pattern: Image, var number: Image)
