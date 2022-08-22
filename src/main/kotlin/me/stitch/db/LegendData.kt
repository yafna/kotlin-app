package me.stitch.db

import javafx.scene.image.Image
import javafx.scene.paint.Color
import me.stitch.dto.LegendItem
import java.io.ByteArrayInputStream


class LegendData {
    //dummy fill
     public  fun list() :List<LegendItem> {
         val res =  ArrayList<LegendItem>()
          for (i in 1..35 ){
             res.add(LegendItem(i, Color.rgb(i * 7, i * 7, i * 7), Image("test30.png")))
          }
         return  res
     }
}