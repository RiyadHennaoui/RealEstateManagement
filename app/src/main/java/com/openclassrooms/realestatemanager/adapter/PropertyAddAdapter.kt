package com.openclassrooms.realestatemanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.database.Property
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel

class PropertyAddAdapter(
    var property: Property,
    private val viewModelProperty: PropertyViewModel
): RecyclerView.Adapter<PropertyAddAdapter.PropertyAddViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyAddViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_add_property, parent, false)

        return PropertyAddViewHolder(view)
    }

    override fun onBindViewHolder(holder: PropertyAddViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class PropertyAddViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}