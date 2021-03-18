package com.mortarifabio.marvelcharacterschallenge.details.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.mortarifabio.marvelcharacterschallenge.databinding.FragmentDetailsBinding
import com.mortarifabio.marvelcharacterschallenge.details.viewModel.DetailsViewModel
import com.mortarifabio.marvelcharacterschallenge.model.CharactersResult

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()
    private var character: CharactersResult? = null
    private val viewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        character = args.character
        loadContent()
        setupObservables()
        return binding.root
    }

    private fun loadContent() = with(binding) {
        character?.let {
            Glide.with(this@DetailsFragment)
                .load(it.largeImage)
                .into(ivDetailsImage)
            tvDetailsName.text = it.name
            tvDetailsDescription.text = it.description
            cbDetailsFavorite.isChecked = it.favorite
        }
    }

    private fun setupObservables() {
        binding.cbDetailsFavorite.setOnClickListener {
            when(character?.favorite){
                true -> viewModel.removeFavorite(character)
                false -> viewModel.addFavorite(character)
            }
        }
    }

}