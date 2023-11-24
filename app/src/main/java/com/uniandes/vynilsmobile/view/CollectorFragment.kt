package com.uniandes.vynilsmobile.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.data.model.Collector
import com.uniandes.vynilsmobile.databinding.CollectorFragmentBinding
import com.uniandes.vynilsmobile.view.adapters.CollectorsAdapter
import com.uniandes.vynilsmobile.viewmodel.CollectorViewModel


class CollectorFragment : Fragment(R.layout.collector_fragment) {
    private var _binding: CollectorFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: CollectorViewModel
    private var collectorAdapter: CollectorsAdapter? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mainActivity = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CollectorFragmentBinding.inflate(inflater, container, false)

        progressBar = binding.progressBar
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.collectorsRv
        recyclerView.layoutManager = LinearLayoutManager(context)

        val onItemClick: (Collector) -> Unit = {collector ->
            //findNavController().popBackStack()
            val action = CollectorFragmentDirections.actionCollectorFragmentToCollectorDetailFragment(collector)
            findNavController().navigate(action)

        }

        collectorAdapter = CollectorsAdapter(progressBar, onItemClick)
        recyclerView.adapter = collectorAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        val bar = (activity as AppCompatActivity).supportActionBar
        bar?.title = getString(R.string.title_vista_collectors)

        viewModel = ViewModelProvider(this, CollectorViewModel.Factory(activity.application))[CollectorViewModel::class.java]
        viewModel.collectors.observe(viewLifecycleOwner) { collectors ->
            collectorAdapter?.collectors = collectors
        }

        viewModel.eventNotDataFound.observe(viewLifecycleOwner) { isNotDataFoundShown ->
            if (isNotDataFoundShown) notDataFound()
            else  mainActivity.showErrorLayout(false, "")
        }

        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
            else  mainActivity.showErrorLayout(false, "")
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, resources.getString(R.string.error_network_connection), Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
            mainActivity.showErrorLayout(true, resources.getString(R.string.error_network_connection))
        }
    }

    private fun notDataFound() {
        if(!viewModel.isNotDataFoundShown.value!!) {
            Toast.makeText(
                activity,
                resources.getString(R.string.not_data_found),
                Toast.LENGTH_LONG
            ).show()
            viewModel.onNotDataFoundShown()
            mainActivity.showNotDataFoundLayout(
                true,
                resources.getString(R.string.not_data_found)
            )
        }
    }
}