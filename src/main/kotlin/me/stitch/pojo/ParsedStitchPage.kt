package me.stitch.pojo

data class ParsedStitchPage(override val fromx: Int, override val fromy: Int, val field: List<List<Int>>) : StitchPage()