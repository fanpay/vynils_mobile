package com.uniandes.vynilsmobile.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.data.model.Prize
import com.uniandes.vynilsmobile.databinding.PrizeCreateFragmentBinding
import com.uniandes.vynilsmobile.viewmodel.PrizeCreateViewModel

class PrizeCreateFragment : Fragment() {

    private var _binding: PrizeCreateFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PrizeCreateViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PrizeCreateFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnCrearPrize: View = binding.buttonCreatePrize
        btnCrearPrize.setOnClickListener { _ ->
            val prizeName = binding.editTextName.text.toString()
            val prizeDescription = binding.editTextDescription.text.toString()
            val prizeOrganization = binding.editTextOrganization.text.toString()

            // Verificar que los campos obligatorios no estén vacíos
            if (prizeName.isEmpty()) {
                binding.editTextName.error = "El campo nombre es obligatorio"
                return@setOnClickListener
            }
            // Verificar que los campos de texto tengan la longitud adecuada
            if (prizeName.length < 5 || prizeName.length > 50) {
                binding.editTextName.error = "El campo nombre debe tener entre 5 y 50 caracteres"
                return@setOnClickListener
            }

            if (prizeDescription.isEmpty()) {
                binding.editTextDescription.error = "El campo descripción es obligatorio"
                return@setOnClickListener
            }
            if (prizeDescription.length < 5 || prizeDescription.length > 50) {
                binding.editTextDescription.error = "El campo descripción debe tener entre 5 y 50 caracteres"
                return@setOnClickListener
            }

            if (prizeOrganization.isEmpty()) {
                binding.editTextDescription.error = "El campo organización es obligatorio"
                return@setOnClickListener
            }
            if (prizeOrganization.length < 5 || prizeOrganization.length > 100) {
                binding.editTextDescription.error = "El campo organización debe tener entre 5 y 100 caracteres"
                return@setOnClickListener
            }

            val prize = Prize(
                name = prizeName,
                description = prizeDescription,
                organization = prizeOrganization
            )

            viewModel.savePrize(prize)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        val bar = (activity as? AppCompatActivity)?.supportActionBar
        bar?.title = getString(R.string.title_create_prize)

        viewModel = ViewModelProvider(this, PrizeCreateViewModel.Factory(activity.application))[PrizeCreateViewModel::class.java]
        viewModel.prize.observe(viewLifecycleOwner) { prize ->
            Log.v("PrizeCreateFragment", "Prize created successfully. ID-> ${prize.id}")
            Toast.makeText(activity, "Se ha creado el premio exitosamente", Toast.LENGTH_LONG).show()

            findNavController().popBackStack()
            /*val action = PrizeCreateFragmentDirections.actionPrizeCreateFragmentToCollectorsFragment()
            findNavController().navigate(action)*/
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

}