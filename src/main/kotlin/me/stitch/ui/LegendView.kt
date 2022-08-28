package me.stitch.ui

import javafx.beans.property.SimpleObjectProperty
import javafx.embed.swing.SwingFXUtils
import javafx.scene.paint.Color
import me.stitch.db.ImgData
import tornadofx.View
import tornadofx.action
import tornadofx.borderpane
import tornadofx.button
import tornadofx.center
import tornadofx.hbox
import tornadofx.hboxConstraints
import tornadofx.imageview
import tornadofx.scrollpane
import tornadofx.style
import tornadofx.vbox
import tornadofx.visibleWhen

class LegendView() : View() {

    val prefix = "pixel1"
    val data = ImgData()
    val fooProperty = SimpleObjectProperty(true)
    val b = button(">") {
        action {
            if (">".equals(text, false)) {
                fooProperty.set(false)
                text = "<"
            } else {
                fooProperty.set(true)
                text = ">"
            }
        }
    }
    override val root = borderpane() {
        top = b
        center {
            scrollpane {
                visibleWhen { fooProperty }
                vbox {
                    for (item in data.listLegends(prefix)) {
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
                            imageview(SwingFXUtils.toFXImage(item.number, null))
                        }
                    }
                }
            }
        }
    }
}