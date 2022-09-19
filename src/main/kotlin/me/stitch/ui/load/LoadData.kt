package me.stitch.ui.load

import javafx.beans.binding.BooleanExpression
import javafx.stage.FileChooser
import tornadofx.FileChooserMode
import tornadofx.Stylesheet.Companion.checked
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

class LoadData : View("Load") {
    val controller: WizardController by inject()
    override val complete: BooleanExpression = controller.loadComplete

    override val root = form {
        radiobutton("Load as Pdf") {
            checked
            action {
                controller.loadAsPdf.set(isSelected)
                controller.loadAsImg.set(!isSelected)
            }
        }
        hbox {
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

        checkbox("Parse pages") {
            action { controller.parsePages(isSelected) }
        }
    }
}