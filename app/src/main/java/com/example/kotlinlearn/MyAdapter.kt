package com.example.kotlinlearn

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinlearn.models.Country
import com.squareup.picasso.Picasso

class MyAdapter(private val myList: List<Country>?): RecyclerView.Adapter<MyAdapter.MyViewHolder>(), Filterable {
    private var filteredData: List<Country>? = myList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album,parent,false)
        return MyViewHolder(view)
    }


    override fun getItemCount(): Int {
       return filteredData?.size ?: 0
    }
    fun updateData(newData: List<Country>?) {
        filteredData = newData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val country :  Country = (filteredData?.get(position) ?: 0) as Country
        Picasso.get().load(country.flags.png).into(holder.flag)
        holder.name.text = country.name.common
        if (country.capital != null) {
            holder.capital.text = "Capital : " + country.capital[0]
        } else {
            holder.capital.text = "N/A" // Handle the case where there's no capital
        }
        holder.continents.text = "Continent: "+country.continents[0]
        if (country.currencies != null && country.currencies.isNotEmpty()){
            holder.currency.text = "Currency: "+ country.currencies.keys.joinToString(", ")
        }else{
            holder.currency.text = "N/A"
        }
        holder.population.text = "Population: " +country.population.toString()

    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val flag: ImageView = itemView.findViewById(R.id.imageFlag)
        val name: TextView = itemView.findViewById(R.id.textCountryName)
        val capital: TextView = itemView.findViewById(R.id.textCapital)
        val continents: TextView = itemView.findViewById(R.id.textContinent)
        val currency: TextView = itemView.findViewById(R.id.textCurrency)
        val population: TextView = itemView.findViewById(R.id.textPopulation)

    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                if (constraint.isNullOrEmpty()) {

                    results.values = myList
                } else {
                    val filteredList = myList?.filter { item ->
                       item.name.common.contains(constraint, ignoreCase = true)
                    }
                    results.values = filteredList

                }
                return results
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                if (p1 != null) {
                    updateData(p1.values as List<Country>)
                }
            }

        }
    }
}