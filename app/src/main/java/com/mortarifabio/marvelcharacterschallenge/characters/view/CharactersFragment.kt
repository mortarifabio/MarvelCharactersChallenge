package com.mortarifabio.marvelcharacterschallenge.characters.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mortarifabio.marvelcharacterschallenge.R
import com.mortarifabio.marvelcharacterschallenge.characters.viewModel.CharactersViewModel
import com.mortarifabio.marvelcharacterschallenge.databinding.FragmentCharactersBinding
import com.mortarifabio.marvelcharacterschallenge.extensions.showInSnackBar
import com.mortarifabio.marvelcharacterschallenge.model.CharactersResult
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
    private var emptyListSnackbar: Snackbar? = null
    private val viewModel by lazy {
        ViewModelProvider(this).get(CharactersViewModel::class.java)
    }
    private val charactersAdapter by lazy {
        CharactersAdapter { character, favorite ->
            when(favorite){
                true -> viewModel.addFavorite(character)
                false -> viewModel.removeFavorite(character)
                null -> {
                    val action = CharactersFragmentDirections.actionListFragmentToDetailsFragment(character)
                    view?.findNavController()?.navigate(action)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        setupRecyclerView()
        loadContent()
        setupObservables()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.rvCharacters.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            adapter = charactersAdapter
        }
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
            if (loadState.source.refresh is LoadState.NotLoading && charactersAdapter.itemCount < 1) {
                binding.tvNoCharactersFound.visibility = VISIBLE
            } else {
                binding.tvNoCharactersFound.visibility = GONE
            }
        }
        binding.bCharactersSearchButton.setOnClickListener {
            viewModel.name = binding.tietCharactersSearch.text.toString()
            charactersAdapter.refresh()
        }
    }
}