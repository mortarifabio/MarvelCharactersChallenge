package com.mortarifabio.marvelcharacterschallenge.characters.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.mortarifabio.marvelcharacterschallenge.characters.viewModel.CharactersViewModel
import com.mortarifabio.marvelcharacterschallenge.databinding.FragmentCharactersBinding

class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
    private val viewModel by lazy {
        ViewModelProvider(this).get(CharactersViewModel::class.java)
    }
    private val charactersAdapter by lazy {
        //todo: Configurar evento no click
        CharactersAdapter {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupObservables()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadContent()
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