package com.example.translationapp.ui.history.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.translationapp.databinding.FragmentHistoryBinding
import com.example.translationapp.ui.TranslationRecyclerAdapterActionListener
import com.example.translationapp.ui.TranslationDetailsData
import com.example.translationapp.ui.TranslationListRecyclerAdapter
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

        binding.favoritesFragmentGallery.isVisible = true
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpTranslationsList()
        collectViewState()
    }


    private fun setUpTranslationsList() {
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

        binding.favoritesFragmentGallery.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = translationsListAdapter
        }
    }

    private fun collectViewState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                historyViewModel.translationDetailsData.collect { viewState ->
                    translationsListAdapter?.data = viewState
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