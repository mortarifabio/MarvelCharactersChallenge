package com.mortarifabio.marvelcharacterschallenge.characters.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mortarifabio.marvelcharacterschallenge.characters.viewModel.CharactersViewModel
import com.mortarifabio.marvelcharacterschallenge.databinding.FragmentCharactersBinding
import com.mortarifabio.marvelcharacterschallenge.model.CharactersResult

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

    private fun setupObservables() {
        binding.tietCharactersSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.getCharacters(text.toString())
            loadContent()
        }
    }

    private fun loadContent() {
        viewModel.charactersPagedList?.observe(viewLifecycleOwner) { pagedList ->
            charactersAdapter.submitList(pagedList)
        }
    }
}