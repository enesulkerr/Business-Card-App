    package com.example.business_card_app.ui.home
    import android.app.AlertDialog
    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.ArrayAdapter
    import android.widget.ListView
    import android.widget.SearchView
    import androidx.fragment.app.Fragment
    import com.example.business_card_app.configs.AppDatabase
    import com.example.business_card_app.databinding.FragmentHomeBinding
    import com.example.business_card_app.service.Db

    class HomeFragment : Fragment() {

        private var _binding: FragmentHomeBinding? = null
        private val binding get() = _binding!!
        private lateinit var db: AppDatabase
        private var isimlistesi: List<String> = emptyList()
        private lateinit var adapter: ArrayAdapter<String>

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {

            val context = requireContext()
            db = Db(context).db

            _binding = FragmentHomeBinding.inflate(inflater, container, false)
            val homelistview: ListView = binding.homeListview
            val searchview: SearchView = binding.searchview

            adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                isimlistesi.toMutableList() // read-only listeyi mutable listeye dönüştür
            )

            homelistview.adapter = adapter

            val run = Runnable {
                val item = db.noteDao().getAll()
                val newIsimListesi = item.map { it.isim!! }

                activity?.runOnUiThread {
                    isimlistesi = newIsimListesi
                    adapter.clear()
                    adapter.addAll(isimlistesi)
                    adapter.notifyDataSetChanged()
                }
            }
            Thread(run).start()


            searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.filter.filter(newText)
                    return true
                }
            })

            val root: View = binding.root

            homelistview.setOnItemClickListener { parent, view, position, id ->
                showDeleteConfirmationDialog(position)
                // selectedNid değişkenine seçilen öğenin nid değerini atayabilirsiniz
                // İstediğiniz işlemleri burada yapabilirsiniz
            }

            return root


        }

        override fun onResume() {
            super.onResume()
            adapter.notifyDataSetChanged()
        }

        override fun onDestroyView() {
            super.onDestroyView()
            adapter.notifyDataSetChanged()
            _binding = null
        }



        private fun showDeleteConfirmationDialog(position: Int) {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("Silme İşlemi")
            alertDialogBuilder.setMessage("Seçili öğeyi silmek istediğinizden emin misiniz?")
            alertDialogBuilder.setPositiveButton("Evet") { dialog, _ ->
                var run1 = Runnable {
                    val selectedIsim = isimlistesi[position]
                    val noteDao = db.noteDao()
                    if(selectedIsim != null) {
                        noteDao.deleteById(selectedIsim)
                        adapter.notifyDataSetChanged()
                    }}
                Thread(run1).start()
                adapter.notifyDataSetChanged()
            }
            alertDialogBuilder.setNegativeButton("Hayır") { dialog, _ ->
                dialog.dismiss()
            }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

    }

