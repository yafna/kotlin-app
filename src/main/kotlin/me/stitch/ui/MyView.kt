package me.stitch.ui

import javafx.beans.property.DoubleProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.scene.Cursor
import tornadofx.View
import tornadofx.action
import tornadofx.borderpane
import tornadofx.bottom
import tornadofx.button
import tornadofx.center
import tornadofx.hbox
import tornadofx.imageview
import tornadofx.scrollpane
import tornadofx.slider
import tornadofx.vbox


class MyView : View() {
    private val legendView = find(LegendView::class)
    val main: MainController by inject()
    override val root = borderpane() {
        prefHeight = 700.0
        prefWidth = 700.0
        left = legendView.root

        center {
            scrollpane {
                this.pannableProperty().set(true)
                imageview(main.currentImageProperty) {
                    this.imageProperty().bind(main.currentImageProperty)
                    fitHeightProperty().bind(main.resizedHProperty)
                    fitWidthProperty().bind(main.resizedWProperty)
                    this.setOnMouseEntered { e -> this.setCursor(Cursor.OPEN_HAND) }
                    this.setOnMousePressed { e -> this.setCursor(Cursor.CLOSED_HAND) }
                    this.setOnMouseReleased { e -> this.setCursor(Cursor.OPEN_HAND) }
                    this.setOnMouseExited { e -> this.setCursor(Cursor.DEFAULT) }
                }
                this.isPannable = true;
                this.hvalue = 0.5;
                this.vvalue = 0.5;
            }
        }
        bottom {
            vbox {
                slider(0.1, 1.0, 1.0) {
                    blockIncrement = 0.1
                    valueProperty().addListener { e ->
                        main.resizedWProperty.set(main.currentImageProperty.value.width * (e as DoubleProperty).value)
                        main.resizedHProperty.set(main.currentImageProperty.value.height * e.value)
                    }
                }
                scrollpane {
                    hbox {
                        for (i in 1 until main.allImgs().size) {
                            button {
                                imageview(main.allImgs()[i].first) {
                                    this.preserveRatioProperty().set(true)
                                    fitWidth = width * 0.01
                                    fitHeight = height * 0.01
                                }
                                action {
                                    main.currentImageProperty.set(main.allImgs()[i].second)
                                    println("img  $i")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}