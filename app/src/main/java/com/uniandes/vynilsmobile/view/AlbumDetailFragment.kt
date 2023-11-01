package com.uniandes.vynilsmobile.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.uniandes.vynilsmobile.databinding.AlbumDetailFragmentBinding
import com.uniandes.vynilsmobile.viewmodel.AlbumDetailViewModel


class AlbumDetailFragment : Fragment() {
    private var _binding: AlbumDetailFragmentBinding? = null
    private val binding get() = _binding!!


    private lateinit var viewModel: AlbumDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AlbumDetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AlbumDetailViewModel::class.java)
        val args: AlbumDetailFragmentArgs by navArgs()
        binding.albumName.text = args.album.name
        Picasso.get().load(args.album.cover).into(binding.albumImage)
        binding.tvDescription.text = args.album.description
        binding.tvReleaseDate.text = args.album.releaseDate
        binding.tvGenre.text = args.album.genre
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
