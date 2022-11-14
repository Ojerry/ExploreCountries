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


        val populationTv: TextView = findViewById(R.id.populationValue_textView)
        val capitalTv: TextView = findViewById(R.id.capitalValue_textView)
        val continentTv: TextView = findViewById(R.id.continentValue_textView)
//        val currencyTv: TextView = findViewById(R.id.currencyValue_textView)
        val areaTv: TextView = findViewById(R.id.areaValue_textView)
        val landlockedTv: TextView = findViewById(R.id.landLockedValue_textView)
        val independentTv : TextView = findViewById(R.id.independenceValue_textView)
        val unMemberTv: TextView = findViewById(R.id.uNMemberValue_textView)
        val regionTv: TextView = findViewById(R.id.regionValue_textView)
        val subRegionTv: TextView = findViewById(R.id.subregionValue_textView)
        val timezoneTv: TextView = findViewById(R.id.timezoneValue_textView)
        val nameTv: TextView = findViewById(R.id.name_value_textView)


        val bundle : Bundle?= intent.extras
        val name = bundle?.getString("name")
        val population = bundle?.getString("population")
        val flag = bundle?.getString("flag")
        val coatOfArms = bundle?.getString("coatOfArms")
        val capital = bundle?.getString("capital")
        val continents = bundle?.getString("continents")
        val area = bundle?.getString("area")
        val landlocked = bundle?.getString("landlocked")
        val independent = bundle?.getString("independent")
        val unMember = bundle?.getString("unMember")
        val region = bundle?.getString("region")
        val subRegion = bundle?.getString("subRegion")
        val timezone = bundle?.getString("timezone")
//        val currency = bundle?.getString("currency")

        populationTv.text = population
        capitalTv.text = capital
        continentTv.text = continents
        areaTv.text = area
        landlockedTv.text = landlocked
        independentTv.text = independent
        unMemberTv.text = unMember
        regionTv.text = region
        subRegionTv.text = subRegion
        timezoneTv.text = timezone
        nameTv.text = name

//        currencyTv.text = currency


        val imageSlider: ImageSlider = findViewById(R.id.image_slider)
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(flag))
        imageList.add(SlideModel(coatOfArms))

        imageSlider.setImageList(imageList, ScaleTypes.CENTER_INSIDE)
        imageSlider.stopSliding()
    }
}