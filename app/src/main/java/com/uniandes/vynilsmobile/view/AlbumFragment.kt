package com.uniandes.vynilsmobile.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.databinding.AlbumFragmentBinding
import com.uniandes.vynilsmobile.view.adapters.AlbumsAdapter
import com.uniandes.vynilsmobile.viewmodel.AlbumViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class AlbumFragment : Fragment() {
    private var _binding: AlbumFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AlbumViewModel
    private var albumAdapter: AlbumsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AlbumFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        albumAdapter = AlbumsAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.albumsRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = albumAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_albums)
        viewModel = ViewModelProvider(this, AlbumViewModel.Factory(activity.application))[AlbumViewModel::class.java]
        viewModel.albums.observe(viewLifecycleOwner) { albums ->
            albumAdapter?.albums = albums
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
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}