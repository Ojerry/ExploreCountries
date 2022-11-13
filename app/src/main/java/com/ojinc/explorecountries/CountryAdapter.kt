package com.ojinc.explorecountries

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ojinc.explorecountries.data.CountryData
import com.ojinc.explorecountries.data.CountryDataItem
import com.squareup.picasso.Picasso

class CountryAdapter(val context: Context, var countryList: ArrayList<CountryDataItem>): RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position : Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }
    fun setFilteredList(filteredList: CountryData){
        this.countryList = filteredList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView) {
        var countryName : TextView
        var countryCapital : TextView
        var countryFlag: ImageView
        init {
            countryName = itemView.findViewById(R.id.country_name_Tv)
            countryCapital = itemView.findViewById(R.id.country_capital_Tv)
            countryFlag = itemView.findViewById(R.id.country_flag)

            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.country_items, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentItem = countryList[position]
        holder.countryName.text = countryList[position].name.common
        holder.countryCapital.text = countryList[position].capital?.get(0).toString()
//        holder.countryFlag.setImageResource(currentItem.flags)
        var countryFlag = holder.countryFlag
        Picasso.get()
            .load(currentItem.flags.png)
            .into(countryFlag)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }
}