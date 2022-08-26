package me.stitch.ui

import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.Image
import javafx.scene.image.WritableImage
import javafx.scene.paint.Color
import me.stitch.db.LegendData
import tornadofx.View
import tornadofx.action
import tornadofx.borderpane
import tornadofx.button
import tornadofx.center
import tornadofx.hbox
import tornadofx.hboxConstraints
import tornadofx.imageview
import tornadofx.label
import tornadofx.left
import tornadofx.scrollpane
import tornadofx.style
import tornadofx.vbox

class MyView : View() {
    private val img = Image("part11.png")
    private val imgh = img.height
    private val imgw = img.width
    private val writableImage = WritableImage(img.pixelReader, imgw.toInt(), imgh.toInt())
    val data = LegendData()
    override val root = borderpane() {
        left {
            scrollpane {
                vbox {
                    for (item in data.list()) {
                        println(" $item.rgb.red ${item.rgb.green} ${item.rgb.blue}")
                        hbox {
                            imageview(SwingFXUtils.toFXImage(item.pattern, null))
                            button {
                                style {
                                    backgroundColor += Color.rgb(item.rgb.red, item.rgb.green, item.rgb.blue)
                                }
                                hboxConstraints {
                                    prefHeight = 30.0
                                    prefWidth = 30.0
                                }
                                action {
                                    println(item.index)
                                }
                            }
//                            label(item.index.toString())
                            imageview(SwingFXUtils.toFXImage(item.number, null))
                            label("    ")
                        }

                    }
                }
            }
        }
        center {
            scrollpane {
                imageview(writableImage)
                hboxConstraints {
                    prefHeight = imgh
                    prefWidth = imgw
                }
            }
            hboxConstraints {
                prefHeight = 800.0
                prefWidth = 900.0
            }
        }
    }
}