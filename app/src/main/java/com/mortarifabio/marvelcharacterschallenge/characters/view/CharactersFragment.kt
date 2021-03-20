package com.mortarifabio.marvelcharacterschallenge.characters.view

import android.content.Context
import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.mortarifabio.marvelcharacterschallenge.R
import com.mortarifabio.marvelcharacterschallenge.characters.viewModel.CharactersViewModel
import com.mortarifabio.marvelcharacterschallenge.databinding.FragmentCharactersBinding
import com.mortarifabio.marvelcharacterschallenge.utils.NetworkUtils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
    private val viewModel by lazy {
        ViewModelProvider(this).get(CharactersViewModel::class.java)
    }
    private val charactersAdapter by lazy {
        CharactersAdapter { character, favorite ->
            when (favorite) {
                true -> viewModel.addFavorite(character)
                false -> viewModel.removeFavorite(character)
                null -> {
                    val action = CharactersFragmentDirections.actionListFragmentToDetailsFragment(character)
                    view?.findNavController()?.navigate(action)
                }
            }
        }
    }
    private lateinit var networkUtils: NetworkUtils

    override fun onAttach(context: Context) {
        super.onAttach(context)
        networkUtils = NetworkUtils(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        setupRecyclerView(resources.configuration)
        loadContent()
        setupObservables()
        return binding.root
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setupRecyclerView(newConfig)
    }

    private fun setupRecyclerView(configuration: Configuration) = with(binding.rvCharacters) {
        val spanCount = when(configuration.orientation){
            ORIENTATION_LANDSCAPE -> 4
            else -> 2
        }
        layoutManager = GridLayoutManager(this.context, spanCount)
        adapter = charactersAdapter
    }

    private fun loadContent() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                charactersAdapter.submitData(pagingData)
            }
        }
    }

    private fun setupObservables() {
        charactersAdapter.addLoadStateListener { loadState ->
            setStateMessage(loadState.refresh)
        }
        binding.bCharactersSearchButton.setOnClickListener {
            refreshList()
        }
        networkUtils.setupNetworkObserver { isConnected ->
            if (isConnected) {
                refreshList()
            }
        }
    }

    private fun setStateMessage(loadState: LoadState) {
        when (loadState) {
            is LoadState.Loading -> showListMessage(getString(R.string.loading_characters), VISIBLE)
            is LoadState.NotLoading -> {
                if (charactersAdapter.itemCount < 1) {
                    showListMessage(getString(R.string.no_characters_found), VISIBLE)
                } else {
                    showListMessage("", GONE)
                }
            }
            is LoadState.Error -> {
                showListMessage(getString(R.string.error_loading_characters), VISIBLE)
            }
            else -> showListMessage("", GONE)
        }
    }

    private fun showListMessage(message: String, visibility: Int) = with(binding) {
        tvMessages.text = message
        tvMessages.visibility = visibility
        when(visibility) {
            VISIBLE -> rvCharacters.visibility = INVISIBLE
            GONE -> rvCharacters.visibility = VISIBLE
        }
    }

    private fun refreshList() {
        viewModel.name = binding.tietCharactersSearch.text.toString()
        charactersAdapter.refresh()
    }
}