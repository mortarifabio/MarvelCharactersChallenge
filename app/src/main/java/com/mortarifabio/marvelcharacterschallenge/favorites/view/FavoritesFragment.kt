package com.mortarifabio.marvelcharacterschallenge.favorites.view

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mortarifabio.marvelcharacterschallenge.databinding.FragmentFavoritesBinding
import com.mortarifabio.marvelcharacterschallenge.favorites.viewModel.FavoritesViewModel
import com.mortarifabio.marvelcharacterschallenge.model.CharactersResult

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel by lazy {
        ViewModelProvider(this).get(FavoritesViewModel::class.java)
    }
    private var favoritesData = mutableListOf<CharactersResult>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        viewModel.loadFavorites()
        setupObservables()
        return binding.root
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setupRecyclerView(favoritesData, newConfig)
    }

    private fun setupObservables() {
        viewModel.favoritesLiveData.observe(viewLifecycleOwner) { favorites ->
            setupRecyclerView(favorites, resources.configuration)
            favoritesData = favorites
        }
        viewModel.isListEmpty.observe(viewLifecycleOwner) { isEmpty ->
            if(isEmpty) {
                binding.tvNoFavoritesCharacters.visibility = View.VISIBLE
            } else {
                binding.tvNoFavoritesCharacters.visibility = View.GONE
            }
        }
    }

    private fun setupRecyclerView(favorites: MutableList<CharactersResult>, configuration: Configuration) = with(binding.rvFavorites) {
        val spanCount = when(configuration.orientation){
            Configuration.ORIENTATION_LANDSCAPE -> 4
            else -> 2
        }
        layoutManager = GridLayoutManager(this.context, spanCount)
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