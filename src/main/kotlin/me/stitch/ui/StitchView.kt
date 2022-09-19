package me.stitch.ui

import javafx.beans.property.DoubleProperty
import javafx.scene.Cursor
import javafx.scene.paint.Color
import me.stitch.ui.load.LoadWizard
import tornadofx.View
import tornadofx.action
import tornadofx.borderpane
import tornadofx.bottom
import tornadofx.button
import tornadofx.center
import tornadofx.contextmenu
import tornadofx.hbox
import tornadofx.hboxConstraints
import tornadofx.imageview
import tornadofx.item
import tornadofx.left
import tornadofx.menu
import tornadofx.menubar
import tornadofx.scrollpane
import tornadofx.slider
import tornadofx.style
import tornadofx.top
import tornadofx.vbox


class StitchView : View() {
    val main: StitchViewController by inject()
    override val root = borderpane() {
        top {
            menubar {
                menu("File") {
                    item("Load").action {
                        find<LoadWizard> {
                            openModal()
                        }
                    }

                    item("Save").action {
                        main.saveState()
                    }
                }
            }
        }
        prefHeight = 700.0
        prefWidth = 700.0
        left {
            scrollpane {
                vbox {
                    for (item in main.legends()) {
                        hbox {
                            imageview(item.pattern)
                            button {
                                style {
                                    backgroundColor += Color.rgb(item.rgb.red, item.rgb.green, item.rgb.blue)
                                }
                                hboxConstraints {
                                    prefHeight = 30.0
                                    prefWidth = 30.0
                                }
                                action {
                                    main.legendClicked(item)
                                }
                            }
                            imageview(item.number)
                        }
                    }
                }
            }
        }
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
                        main.sliderMove(e as DoubleProperty)
                    }
                }
                scrollpane {
                    hbox {
                        for (i in 0 until main.allImgs().size) {
                            button {
                                imageview(main.allImgs()[i].first) {
                                    this.preserveRatioProperty().set(true)
                                    fitWidth = width * 0.01
                                    fitHeight = height * 0.01
                                }
                                action {
                                    main.pageSelected(main.allImgs()[i].second)
                                    println("img  $i")
                                }
                                contextmenu {
                                    item("right top >").action { main.combineRightTop(main.allImgs()[i].second) }
                                    item("< left bottom").action { main.combineLeftBottom(main.allImgs()[i].second) }
                                    item("right bottom >").action { main.combineRightBottom(main.allImgs()[i].second) }
                                    item("drop").action { main.drop() }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}