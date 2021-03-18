package com.mortarifabio.marvelcharacterschallenge.favorites.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mortarifabio.marvelcharacterschallenge.R
import com.mortarifabio.marvelcharacterschallenge.characters.view.CharactersFragmentDirections
import com.mortarifabio.marvelcharacterschallenge.databinding.FragmentFavoritesBinding
import com.mortarifabio.marvelcharacterschallenge.favorites.viewModel.FavoritesViewModel
import com.mortarifabio.marvelcharacterschallenge.model.CharactersResult

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel by lazy {
        ViewModelProvider(this).get(FavoritesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        viewModel.loadFavorites()
        setupObservables()
        return binding.root
    }

    private fun setupObservables() {
        viewModel.favoritesLiveData.observe(viewLifecycleOwner) { favorites ->
            binding.rvFavorites.apply {
                layoutManager = GridLayoutManager(this.context, 2)
                adapter = FavoritesAdapter(favorites) { character, favorite ->
                    when(favorite){
                        false -> viewModel.removeFavorite(character)
                        null -> {
                            val action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailsFragment(character)
                            view?.findNavController()?.navigate(action)
                        }
                    }
                }
            }
        }
    }


}