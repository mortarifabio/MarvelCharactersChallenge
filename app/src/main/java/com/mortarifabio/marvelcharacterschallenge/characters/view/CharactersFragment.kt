package com.mortarifabio.marvelcharacterschallenge.characters.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mortarifabio.marvelcharacterschallenge.R
import com.mortarifabio.marvelcharacterschallenge.characters.viewModel.CharactersViewModel
import com.mortarifabio.marvelcharacterschallenge.databinding.FragmentCharactersBinding
import com.mortarifabio.marvelcharacterschallenge.extensions.showInSnackBar

class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
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
    private val adapterObserver = object : RecyclerView.AdapterDataObserver() {
        private var snackBar: Snackbar? = null
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            if (itemCount == 0 && charactersAdapter.itemCount == 0) {
                snackBar = showEmptyListSnackbar()
            } else {
                snackBar?.dismiss()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservables()
        loadContent()
    }

    private fun setupRecyclerView() {
        charactersAdapter.registerAdapterDataObserver(adapterObserver)
        binding.rvCharacters.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            adapter = charactersAdapter
        }
    }

    private fun loadContent() {
        viewModel.charactersPagedList?.observe(viewLifecycleOwner) { pagedList ->
            charactersAdapter.submitList(pagedList)
        }
    }

    private fun setupObservables() {
        binding.tietCharactersSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.getCharacters(text.toString())
        }
    }

    private fun showEmptyListSnackbar(): Snackbar? {
        return activity?.getString(R.string.no_characters_found)?.showInSnackBar(binding.rvCharacters)
    }
}