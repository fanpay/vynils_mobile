package com.uniandes.vynilsmobile.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.databinding.CollectorDetailFragmentBinding

class CollectorDetailFragment : Fragment() {
    private var _binding: CollectorDetailFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CollectorDetailFragmentBinding.inflate(inflater, container, false)

        val bar = (activity as? AppCompatActivity)?.supportActionBar
        bar?.title = getString(R.string.title_detail_collector)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: CollectorDetailFragmentArgs by navArgs()
        binding.collectorName.text = args.collector.name
        binding.tvTelephone.text = args.collector.telephone
        binding.tvEmail.text = args.collector.email
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
