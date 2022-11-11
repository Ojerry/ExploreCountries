package com.ojinc.explorecountries

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.denzcoskun.imageslider.constants.ScaleTypes

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        val detailTv: TextView = findViewById(R.id.detailTv)
        val bundle : Bundle?= intent.extras
        val name = bundle?.getString("name")
        val population = bundle?.getString("population")
        val flag = bundle?.getString("flag")
        val coatOfArms = bundle?.getString("coatOfArms")
        detailTv.text = population

        val imageSlider: ImageSlider = findViewById(R.id.image_slider)
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(flag))
        imageList.add(SlideModel(coatOfArms))
        imageList.add(SlideModel("https://goo.gl/maps/LTn417qWwBPFszuV9"))


        imageSlider.setImageList(imageList, ScaleTypes.FIT)
        imageSlider.stopSliding()
    }
}