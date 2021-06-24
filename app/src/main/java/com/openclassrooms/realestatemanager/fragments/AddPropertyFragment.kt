package com.openclassrooms.realestatemanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.database.PropertyDatabase
import com.openclassrooms.realestatemanager.repositories.PropertyRepository
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModelFactory

class AddPropertyFragment: Fragment(){




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        val database = PropertyDatabase(requireActivity())
        val repository = PropertyRepository(database)
        val factoryPropertyViewModel = PropertyViewModelFactory(repository)
        val propertyViewModel = ViewModelProvider(requireActivity(), factoryPropertyViewModel)
            .get(PropertyViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_property, container, false )
    }

}