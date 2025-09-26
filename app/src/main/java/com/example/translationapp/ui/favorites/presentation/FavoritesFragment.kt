package com.example.translationapp.ui.favorites.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.translationapp.R
import com.example.translationapp.databinding.FragmentFavoritesBinding
import com.example.translationapp.ui.actions.TranslationListViewAction
import com.example.translationapp.ui.entities.TranslationDetailsData
import com.example.translationapp.ui.utils.ViewUtils.showErrorMessage
import com.example.translationapp.ui.views.recycler_view.TranslationListRecyclerAdapter
import com.example.translationapp.ui.views.recycler_view.TranslationRecyclerAdapterActionListener
import kotlinx.coroutines.launch

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
        setUpFavoritesListRecyclerView()
        collectRecyclerViewState()
        observeViewAction()
    }

    private fun setUpFavoritesListRecyclerView() {
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

    private fun collectRecyclerViewState() {
        viewLifecycleOwner.lifecycleScope.launch {
            favoritesViewModel.favoritesData.collect { listViewState ->
                favoritesListAdapter?.data = listViewState
                binding.favoritesFragmentEmptyListText.isVisible = listViewState.isEmpty()
                binding.favoritesFragmentGallery.isVisible = listViewState.isNotEmpty()
            }
        }
    }

    private fun observeViewAction() {
        viewLifecycleOwner.lifecycleScope.launch {
            favoritesViewModel.translationListViewAction.collect { action ->
                when (action) {
                    TranslationListViewAction.NavigateToTranslationScreen -> findNavController().navigate(
                        R.id.action_favorites_to_translation
                    )

                    is TranslationListViewAction.ShowToastError -> showErrorMessage(
                        requireContext(),
                        action.errorMessage
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        favoritesListAdapter = null
    }
}