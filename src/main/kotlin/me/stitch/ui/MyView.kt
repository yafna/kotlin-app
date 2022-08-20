package me.stitch.ui

import tornadofx.View
import tornadofx.label
import tornadofx.vbox

class MyView : View() {
    override val root = vbox {
        label("some text here")
    }
}