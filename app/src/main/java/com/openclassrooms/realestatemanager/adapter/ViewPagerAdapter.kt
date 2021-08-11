package com.openclassrooms.realestatemanager.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.database.Photo

class ViewPagerAdapter(val photos: List<Photo>) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewPagerAdapter.ViewPagerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.viewpager_item, parent, false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.ViewPagerViewHolder, position: Int) {

        val imageView: ImageView = holder.itemView.findViewById(R.id.ivProperties)
        val textView: TextView = holder.itemView.findViewById(R.id.tvPictureTitle)

        val currentPhotoUri = Uri.parse(photos[position].photoUri)
        imageView.setImageURI(currentPhotoUri)
        textView.text = photos[position].shortDescription



    }

    override fun getItemCount(): Int {

        return photos.size

    }


}