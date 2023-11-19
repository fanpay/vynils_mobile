package com.uniandes.vynilsmobile.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.data.model.Artist
import com.uniandes.vynilsmobile.databinding.ArtistDetailFragmentBinding

class ArtistDetailFragment : Fragment() {
    private var _binding: ArtistDetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ArtistDetailFragmentBinding.inflate(inflater, container, false)

        val bar = (activity as? AppCompatActivity)?.supportActionBar
        bar?.title = getString(R.string.title_detail_artist)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: ArtistDetailFragmentArgs by navArgs()
        binding.artistName.text = args.artist.name
        Picasso.get()
            .load(args.artist.image)
            .placeholder(R.drawable.ic_baseline_artist_24)
            .error(R.drawable.ic_baseline_android_24)
            .into(binding.artistImage)

        binding.tvDescription.text = args.artist.description
        binding.tvBirthDate.text = args.artist.birthDate
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
