package com.example.business_card_app.ui.family
import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.business_card_app.configs.AppDatabase
import com.example.business_card_app.databinding.FragmentFamilyBinding
import com.example.business_card_app.models.Note
import com.example.business_card_app.service.Db

class FamilyFragment : Fragment() {

    private var _binding: FragmentFamilyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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
            enesListesi = item.filter { it.group == "Aile" }
            requireActivity().runOnUiThread {
                val listView: ListView = binding.ailelistview
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    enesListesi.map { it.toString() } // İstenilen şekilde öğeleri dizeye dönüştürüyoruz
                )
                listView.adapter = adapter
            }

        }
        Thread(run).start()

        _binding = FragmentFamilyBinding.inflate(inflater, container, false)

        val ailelistview: ListView = binding.ailelistview

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_list_item_1,
            enesListesi
        )

        ailelistview.adapter = adapter
        adapter.notifyDataSetChanged()

        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}