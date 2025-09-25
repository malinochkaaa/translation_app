package com.example.translationapp.ui.favorites.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.translationapp.R
import com.example.translationapp.databinding.FragmentFavoritesBinding
import com.example.translationapp.ui.TranslationRecyclerAdapterActionListener
import com.example.translationapp.ui.TranslationListRecyclerAdapter
import com.example.translationapp.ui.TranslationDetailsData
import com.example.translationapp.ui.TranslationListViewAction
import com.example.translationapp.ui.history.presentation.HistoryFragment
import com.example.translationapp.ui.translation.presentation.TranslationFragment
import kotlinx.coroutines.launch
import kotlin.getValue

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private var favoritesListAdapter: TranslationListRecyclerAdapter? = null
    private val favoritesViewModel by viewModels<FavoritesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.favoritesFragmentAddTranslationButton.setOnClickListener {
            favoritesViewModel.onAddButtonClicked()
        }
        setUpFavoritesListGallery()
        collectViewState()
        observeViewAction()
    }

    private fun setUpFavoritesListGallery() {
        favoritesListAdapter = TranslationListRecyclerAdapter(
            object : TranslationRecyclerAdapterActionListener {
                override fun onTranslationLike(translationData: TranslationDetailsData) {
                    favoritesViewModel.onFavoriteButtonClicked(translationData)
                }

                override fun onTranslationRemove(translationData: TranslationDetailsData) {
                    favoritesViewModel.onRemoveButtonClicked(translationData)
                }

            }
        )

        binding.favoritesFragmentGallery.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = favoritesListAdapter
        }
    }

    private fun collectViewState() {
        viewLifecycleOwner.lifecycleScope.launch {
            favoritesViewModel.favoritesData.collect { viewState ->
                favoritesListAdapter?.data = viewState
            }
        }
    }

    private fun observeViewAction() {
        viewLifecycleOwner.lifecycleScope.launch {
            favoritesViewModel.translationListViewAction.collect { action ->
                when (action) {
                    TranslationListViewAction.NavigateToTranslationScreen -> findNavController().navigate(R.id.action_favorites_to_translation)
                    is TranslationListViewAction.ShowToastError -> TODO()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}