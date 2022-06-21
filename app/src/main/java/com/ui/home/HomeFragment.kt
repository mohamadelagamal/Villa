package com.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ui.R
import com.ui.databinding.FragmentHomeBinding
import com.ui.home.beach_house.BeachHouse
import com.ui.home.house_details.HomeDetails
import com.ui.home.villa.VillaActivity
import com.ui.room.RoomActivity

class HomeFragment : Fragment() {
    lateinit var floatingAdd:FloatingActionButton
    // .. get room from fireStore
    private var _binding : FragmentHomeBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        // add value about items
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniRecycle_View()
        binding.houseCardView.setOnClickListener {
            startHouse()
        }
        binding.villaCardView.setOnClickListener {
            startVilla()
        }
        binding.beachHouseCardView.setOnClickListener {
            startBeachHouseCardView()
        }
    }

    private fun startBeachHouseCardView() {
        val intent = Intent(requireContext(), BeachHouse::class.java)
        startActivity(intent)
    }

    private fun startVilla() {
        val intent = Intent(requireContext(), VillaActivity::class.java)
        // send data
        //intent.putExtra(Constant.EXTRA_ROOM, room)
        startActivity(intent)
    }

    private fun iniRecycle_View() {
        floatingAdd = requireActivity().findViewById(R.id.floatingAdd)
        floatingAdd.setOnClickListener {
            val intent = Intent(requireContext(), RoomActivity::class.java)
            startActivity(intent)
        }
    }
    private fun startHouse() {
        val intent = Intent(requireContext(), HomeDetails::class.java)
        // send data
        //intent.putExtra(Constant.EXTRA_ROOM, room)
        startActivity(intent)
    }
}