package com.openclassrooms.realestatemanager.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.adapter.PropertiesListAdapter
import com.openclassrooms.realestatemanager.database.PropertyDatabase
import com.openclassrooms.realestatemanager.databinding.FragmentPropertiesListBinding
import com.openclassrooms.realestatemanager.repositories.PropertyRepository
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PropertiesListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PropertiesListFragment : Fragment() {

    companion object {
        lateinit var ctx:Context
    }

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: PropertiesListAdapter? = null
    private var _binding: FragmentPropertiesListBinding? = null
    private val binding get() = _binding
    private val propertyViewModel: PropertyViewModel? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPropertiesListBinding.inflate(inflater, container, false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val propertyRepository = PropertyRepository(PropertyDatabase.invoke(ctx))
        val factory = PropertyViewModelFactory(propertyRepository)
        val propertyViewModel = ViewModelProvider(this, factory).get(PropertyViewModel::class.java)

        val properties = propertyViewModel.getAllProperties2()

        binding!!.listRv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = PropertiesListAdapter(propertyViewModel)
        }
    }
}