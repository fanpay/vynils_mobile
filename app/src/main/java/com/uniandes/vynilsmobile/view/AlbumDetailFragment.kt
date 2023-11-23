package com.uniandes.vynilsmobile.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.databinding.AlbumDetailFragmentBinding
import java.text.SimpleDateFormat
import java.util.Locale

class AlbumDetailFragment : Fragment() {
    private var _binding: AlbumDetailFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AlbumDetailFragmentBinding.inflate(inflater, container, false)

        val bar = (activity as? AppCompatActivity)?.supportActionBar
        bar?.title = getString(R.string.title_detail_album)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: AlbumDetailFragmentArgs by navArgs()
        binding.albumName.text = args.album.name
        Picasso.get()
            .load(args.album.cover)
            .placeholder(R.drawable.ic_baseline_album_24)
            .error(R.drawable.ic_baseline_android_24)
            .into(binding.albumImage)

        binding.tvDescription.text = args.album.description

        // Formatear la fecha
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

        try {
            val date = inputFormat.parse(args.album.releaseDate)
            val albumDateForUI = outputFormat.format(date)
            binding.tvReleaseDate.text = albumDateForUI
        } catch (e: Exception) {
            e.printStackTrace()
            binding.tvReleaseDate.text = args.album.releaseDate
        }

        binding.tvGenre.text = args.album.genre
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
