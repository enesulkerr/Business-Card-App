package com.example.business_card_app.ui.business

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.business_card_app.R
import com.example.business_card_app.configs.AppDatabase
import com.example.business_card_app.databinding.FragmentBusinessBinding
import com.example.business_card_app.databinding.FragmentFriendsBinding
import com.example.business_card_app.models.Note
import com.example.business_card_app.service.Db

class Business : Fragment() {


    private lateinit var db: AppDatabase
    private var _binding: FragmentBusinessBinding? = null
    private var enesListesi: List<Note> = listOf()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val context = requireContext()
        db = Db(context).db

        val run = Runnable {
            val item = db.noteDao().getAll()
            enesListesi = item.filter { it.group == "İş" }
            requireActivity().runOnUiThread {
                val listView: ListView = binding.islistview
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    enesListesi.map { it.toString() } // İstenilen şekilde öğeleri dizeye dönüştürüyoruz
                )
                listView.adapter = adapter
            }

        }
        Thread(run).start()

        _binding = FragmentBusinessBinding.inflate(inflater, container, false)

        val islistview: ListView = binding.islistview

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            enesListesi
        )

        islistview.adapter = adapter
        adapter.notifyDataSetChanged()


        val view = binding.root
        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}