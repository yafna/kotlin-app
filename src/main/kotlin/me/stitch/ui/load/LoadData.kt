package me.stitch.ui.load

import javafx.beans.binding.BooleanExpression
import javafx.scene.control.ToggleGroup
import javafx.stage.FileChooser
import tornadofx.FileChooserMode
import tornadofx.View
import tornadofx.action
import tornadofx.button
import tornadofx.checkbox
import tornadofx.chooseDirectory
import tornadofx.chooseFile
import tornadofx.enableWhen
import tornadofx.form
import tornadofx.hbox
import tornadofx.radiobutton
import tornadofx.vbox

class LoadData : View("Load") {
    val controller: WizardController by inject()
    override val complete: BooleanExpression = controller.loadComplete
    private val radiogroup = ToggleGroup()
    override val root = form {
        hbox {
            vbox {
                radiobutton("Load as Pdf", radiogroup) {
                    action {
                        controller.loadAsPdf.set(isSelected)
                        controller.loadAsImg.set(!isSelected)
                    }
                }
                button("Load as single pdf") {
                    enableWhen(controller.loadAsPdf)
                    action {
                        val dir = chooseFile("Select Target Directory", mode = FileChooserMode.Single,
                            filters = Array(1) { FileChooser.ExtensionFilter("pdf here", "*.pdf") })
                        if (!dir.isEmpty()) {
                            controller.loadPdf(dir[0])
                        } else {
                            controller.imgs.value = null
                        }
                    }
                }
                style = """
                -fx-border-width: 4;
                -fx-border-height: 5;
                -fx-padding: 10px;
            """
            }
            vbox {
                radiobutton("Load as set of images", radiogroup) {
                    action {
                        controller.loadAsPdf.set(!isSelected)
                        controller.loadAsImg.set(isSelected)
                    }
                }
                button("Load as set of images") {
                    enableWhen(controller.loadAsImg)
                    action {
                        val dir = chooseDirectory("Select Target Directory")
                        if (dir != null) {
                            controller.loadImgsDir(dir)
                        } else {
                            controller.imgs.value = null
                        }
                    }
                }
            }
            style = """
                -fx-border-width: 4;
                -fx-border-height: 5;
                -fx-padding: 10px;
            """
        }

        checkbox("Parse pages") {
            action { controller.parsePages(isSelected) }
        }
    }
}