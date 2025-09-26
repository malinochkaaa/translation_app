package com.example.translationapp.ui.history.presentation

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
import com.example.translationapp.databinding.FragmentHistoryBinding
import com.example.translationapp.ui.TranslationDetailsData
import com.example.translationapp.ui.TranslationListRecyclerAdapter
import com.example.translationapp.ui.TranslationListViewAction
import com.example.translationapp.ui.TranslationRecyclerAdapterActionListener
import com.example.translationapp.ui.utils.ViewUtils.showErrorMessage
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private var translationsListAdapter: TranslationListRecyclerAdapter? = null
    private val historyViewModel by viewModels<HistoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.historyFragmentGallery.isVisible = true
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.historyFragmentAddTranslationButton.setOnClickListener {
            historyViewModel.onAddButtonClicked()
        }
        setUpTranslationsListRecyclerView()
        collectRecyclerViewState()
        observeViewAction()
    }


    private fun setUpTranslationsListRecyclerView() {
        translationsListAdapter = TranslationListRecyclerAdapter(
            object : TranslationRecyclerAdapterActionListener {
                override fun onTranslationLike(translationData: TranslationDetailsData) {
                    historyViewModel.onFavoriteButtonClicked(translationData)
                }

                override fun onTranslationRemove(translationData: TranslationDetailsData) {
                    historyViewModel.onRemoveButtonClicked(translationData)
                }

            }
        )

        binding.historyFragmentGallery.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = translationsListAdapter
        }
    }

    private fun collectRecyclerViewState() {
        viewLifecycleOwner.lifecycleScope.launch {
            historyViewModel.translationDetailsData.collect { listViewState ->
                translationsListAdapter?.data = listViewState
                binding.historyFragmentEmptyListText.isVisible = listViewState.isEmpty()
                binding.historyFragmentGallery.isVisible = listViewState.isNotEmpty()
            }
        }
    }

    private fun observeViewAction() {
        viewLifecycleOwner.lifecycleScope.launch {
            historyViewModel.translationListViewAction.collect { action ->
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
        translationsListAdapter = null
    }
}