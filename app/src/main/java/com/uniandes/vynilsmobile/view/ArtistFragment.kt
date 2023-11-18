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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.data.model.Artist
import com.uniandes.vynilsmobile.databinding.ArtistFragmentBinding
import com.uniandes.vynilsmobile.view.adapters.ArtistsAdapter
import com.uniandes.vynilsmobile.viewmodel.ArtistViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ArtistFragment : Fragment(R.layout.artist_fragment) {
    private var _binding: ArtistFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ArtistViewModel
    private var artistAdapter: ArtistsAdapter? = null
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
        _binding = ArtistFragmentBinding.inflate(inflater, container, false)

        val bar = (activity as AppCompatActivity).supportActionBar
        bar?.title = getString(R.string.title_artists)

        progressBar = binding.progressBar
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.artistRv
        recyclerView.layoutManager = LinearLayoutManager(context)


        val onItemClick: (Artist) -> Unit = {
        }

        artistAdapter = ArtistsAdapter(progressBar, onItemClick)
        recyclerView.adapter = artistAdapter


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_artists)
        viewModel = ViewModelProvider(this, ArtistViewModel.Factory(activity.application))[ArtistViewModel::class.java]
        viewModel.artists.observe(viewLifecycleOwner) { artists ->
            artistAdapter?.artists = artists
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
}