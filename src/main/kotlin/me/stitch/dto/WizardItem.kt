package me.stitch.dto

import javafx.scene.image.Image

data class WizardItem(val legend : Image , val preview: Image?, val pages: List<StitchPage>)