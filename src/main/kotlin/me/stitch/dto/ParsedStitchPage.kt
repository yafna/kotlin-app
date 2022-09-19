package me.stitch.dto

data class ParsedStitchPage(override val fromx: Int, override val fromy: Int, val field: List<List<Int>>) : StitchPage()