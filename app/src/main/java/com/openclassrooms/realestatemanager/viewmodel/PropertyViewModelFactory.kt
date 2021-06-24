package com.openclassrooms.realestatemanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.repositories.PropertyRepository

@Suppress("UNCHECKED_CAST")
class PropertyViewModelFactory(
    private val repository: PropertyRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PropertyViewModel(repository) as T
    }
}