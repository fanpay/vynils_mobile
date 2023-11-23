package com.uniandes.vynilsmobile.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.data.model.Album
import com.uniandes.vynilsmobile.databinding.AlbumFragmentCreateBinding
import com.uniandes.vynilsmobile.viewmodel.AlbumCreateViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

/**
 * A simple [Fragment] subclass.
 * Use the [AlbumCreateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlbumCreateFragment : Fragment() {

    private var _binding: AlbumFragmentCreateBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumCreateViewModel

    private var dateSelected = false
    private var selectedDate: Calendar = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AlbumFragmentCreateBinding.inflate(inflater, container, false)

        var datePickerField: EditText = binding.editFechaEstrenoAlbum

        datePickerField.setOnClickListener {
            onDatePickerFieldClick(it)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnCrearAlbum: View = binding.btnCrearAlbum
        btnCrearAlbum.setOnClickListener { _ ->
            val albumName = binding.editNombreAlbum.text.toString()
            val albumCover = binding.editCoverAlbum.text.toString()
            val albumDescription = binding.editDescripcionAlbum.text.toString().trim()
            val albumGenre = binding.albumGeneros.selectedItem.toString()
            val albumRecordLabel = binding.etiquetasGrabaciones.selectedItem.toString()

            // Verificar que se haya seleccionado una fecha
            if (!dateSelected) {
                binding.editFechaEstrenoAlbum.error = "Campo fecha obligatorio"
                return@setOnClickListener
            } else {
                binding.editFechaEstrenoAlbum.error = null
            }

            // Verificar que los campos obligatorios no estén vacíos
            if (albumName.isEmpty()) {
                binding.editNombreAlbum.error = "El campo nombre es obligatorio"
                return@setOnClickListener
            }
            if (albumCover.isEmpty()) {
                binding.editCoverAlbum.error = "El campo cover es obligatorio"
                return@setOnClickListener
            }
            if (albumDescription.isEmpty()) {
                binding.editDescripcionAlbum.error = "El campo descripción es obligatorio"
                return@setOnClickListener
            }

            // Verificar que los campos de texto tengan la longitud adecuada
            if (albumName.length < 5 || albumName.length > 50) {
                binding.editNombreAlbum.error = "El campo nombre debe tener entre 5 y 50 caracteres"
                return@setOnClickListener
            }
            if (albumDescription.length < 5 || albumDescription.length > 50) {
                binding.editDescripcionAlbum.error = "El campo descripción debe tener entre 5 y 50 caracteres"
                return@setOnClickListener
            }


            val albumDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val albumDate = albumDateFormat.format(selectedDate.time) + "T00:00:00-05:00"
            // Crear un objeto Album a partir de los valores ingresados
            val album = Album(
                name = albumName,
                cover = albumCover,
                releaseDate = albumDate,
                description = albumDescription,
                genre = albumGenre,
                recordLabel = albumRecordLabel
            )

            viewModel.saveAlbum(album)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        val bar = (activity as AppCompatActivity).supportActionBar
        bar?.title = getString(R.string.title_albums)

        viewModel = ViewModelProvider(this, AlbumCreateViewModel.Factory(activity.application))[AlbumCreateViewModel::class.java]
        viewModel.album.observe(viewLifecycleOwner) { album ->
            Log.v("AlbumCreateFragment", "Album created successfully. ID-> ${album.id}")
            Toast.makeText(activity, "Se ha creado el album exitosamente", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_albumCreateFragment_to_albumFragment)
        }

        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(
                activity,
                resources.getString(R.string.error_network_connection),
                Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    private fun onDatePickerFieldClick(view: View) {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(view.context,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth)
                (view as EditText).setText("$selectedDayOfMonth-${selectedMonth + 1}-$selectedYear")
                dateSelected = true
            }, year, month, day)

        datePickerDialog.show()
        dateSelected = false
    }

}