package me.stitch.ui.load

import tornadofx.Wizard

class LoadWizard: Wizard("load new schema") {
//    val controller: WizardController by inject()
    override val canGoNext = currentPageComplete
    override val canFinish = allPagesComplete
    init {
        add(LoadData::class)
        add(ArrangePages::class)
    }
}