package com.example.translationapp.ui.translation.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.translationapp.databinding.FragmentTranslationBinding

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

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() = with(binding) {
        translationFragmentButton.setOnClickListener {
            val searchedWord = translationFragmentSearchedWordEditText.text.toString()
            translationViewModel.onButtonClicked(searchedWord)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}