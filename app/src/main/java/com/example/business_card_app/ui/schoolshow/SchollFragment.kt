package com.example.business_card_app.ui.schoolshow

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.business_card_app.configs.AppDatabase
import com.example.business_card_app.databinding.FragmentSchoolshowBinding
import com.example.business_card_app.models.Note
import com.example.business_card_app.service.Db

class SchollFragment : Fragment() {

    private var _binding: FragmentSchoolshowBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: AppDatabase
    private var enesListesi: List<Note> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val context = requireContext()
        db = Db(context).db

        val run = Runnable {
            val item = db.noteDao().getAll()
            enesListesi = item.filter { it.group == "Okul" }
            requireActivity().runOnUiThread {
                val listView: ListView = binding.okullistview
                val adapter = ArrayAdapter(
                    requireContext(),
                    R.layout.simple_list_item_1,
                    enesListesi.map { it.toString() } // İstenilen şekilde öğeleri dizeye dönüştürüyoruz
                )
                listView.adapter = adapter
            }

        }
        Thread(run).start()



        _binding = FragmentSchoolshowBinding.inflate(inflater, container, false)

        val okullistview: ListView = binding.okullistview

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_list_item_1,
            enesListesi
        )

        okullistview.adapter = adapter
        adapter.notifyDataSetChanged()



        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}