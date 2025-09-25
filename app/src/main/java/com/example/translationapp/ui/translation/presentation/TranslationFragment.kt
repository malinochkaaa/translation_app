package com.example.translationapp.ui.translation.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.translationapp.databinding.FragmentTranslationBinding
import com.example.translationapp.ui.ViewUtils.showErrorMessage
import kotlinx.coroutines.launch

class TranslationFragment : Fragment() {

    private var _binding: FragmentTranslationBinding? = null
    private val binding get() = _binding!!

    private val translationViewModel by viewModels<TranslationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTranslationBinding.inflate(inflater, container, false)
        val root: View = binding.root
        with(binding) {
            translationFragmentSearchedWordEditText.isVisible = true
            translationFragmentButton.isVisible = true
            translationFragmentFoundWordText.isVisible = true
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        collectViewState()
    }

    private fun setupClickListeners() = with(binding) {
        translationFragmentButton.setOnClickListener {
            val searchedWord = translationFragmentSearchedWordEditText.text.toString()
            translationViewModel.onButtonClicked(searchedWord)
        }
    }

    private fun collectViewState() {
        lifecycleScope.launch {
            translationViewModel.translationViewState.collect { viewState ->
                when {
                    viewState.foundWordText?.isNotBlank() == true ->
                        binding.translationFragmentFoundWordText.text = viewState.foundWordText
                    viewState.errorMessage != null -> showErrorMessage(requireContext(), "")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}