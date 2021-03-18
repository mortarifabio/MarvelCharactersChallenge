package com.mortarifabio.marvelcharacterschallenge.details.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.mortarifabio.marvelcharacterschallenge.databinding.FragmentDetailsBinding
import com.mortarifabio.marvelcharacterschallenge.model.CharactersResult

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        loadContent()
        return binding.root
    }

    private fun loadContent() = with(binding) {
        val character = args.character
        character?.let {
            Glide.with(this@DetailsFragment)
                .load(it.largeImage)
                .into(ivDetailsImage)
            tvDetailsName.text = it.name
            tvDetailsDescription.text = it.description
        }
    }

}