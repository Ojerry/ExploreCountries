package com.ojinc.explorecountries

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ojinc.explorecountries.data.CountryDataItem

class CountryAdapter(val context: Context, val countryList: ArrayList<CountryDataItem>): RecyclerView.Adapter<CountryAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var countryName : TextView
        var countryCapital : TextView
        init {
            countryName = itemView.findViewById(R.id.country_name_Tv)
            countryCapital = itemView.findViewById(R.id.country_capital_Tv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.country_items, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.countryName.text = countryList[position].name.common
        holder.countryCapital.text = countryList[position].capital?.toString()
    }

    override fun getItemCount(): Int {
        return countryList.size
    }
}