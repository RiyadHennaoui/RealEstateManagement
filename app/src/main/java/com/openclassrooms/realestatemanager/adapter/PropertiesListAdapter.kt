package com.openclassrooms.realestatemanager.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.database.Photo
import com.openclassrooms.realestatemanager.database.Property
import com.openclassrooms.realestatemanager.database.PropertyWithPhotos
import com.openclassrooms.realestatemanager.databinding.PropertyItemBinding
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel

class PropertiesListAdapter(
    var properties: List<Property>,
    val viewModelProperty: PropertyViewModel
) : RecyclerView.Adapter<PropertiesListAdapter.PropertyAddViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyAddViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.property_item, parent, false)

        val itemBinding =
            PropertyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PropertyAddViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PropertyAddViewHolder, position: Int) {
        val currentProperty: Property = properties[position]

        val listOfPhotos = viewModelProperty.getAllPhotosOfProperty(currentProperty)
        holder.bind(currentProperty, listOfPhotos)

    }

    override fun getItemCount(): Int {
        return properties.size
    }

    class PropertyAddViewHolder(private val itemBinding: PropertyItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root){
            fun bind(property: Property, listOfPhotos: LiveData<LiveData<PropertyWithPhotos>>){

                itemBinding.tvTitlePropertyItem.text = property.type
                itemBinding.tvPricePropertyItem.text = property.price.toString()
                itemBinding.tvAreaPropertyItem.text = property.area.toString()
                itemBinding.tvRoomPropertyItem.text = property.roomsNumber.toString()
                itemBinding.tvBathroomPropertyItem.text = property.bathRoomsNumber.toString()
                itemBinding.tvBedroomPropertyItem.text = property.bedRoomsNumber.toString()
                val photoUri = Uri.parse(listOfPhotos.value!!.value!!.photos[0].photoUri)
                itemBinding.ivPicturePropertyItem.setImageURI(photoUri)

            }
        }

}