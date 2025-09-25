package com.example.translationapp.presentation.translation.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.translationapp.databinding.FragmentTranslationBinding

class TranslationFragment : Fragment() {

    private var _binding: FragmentTranslationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val translationViewModel =
            ViewModelProvider(this)[TranslationViewModel::class.java]

        _binding = FragmentTranslationBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textDashboard
//        translationViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    private fun setupClickListeners() = with(binding) {
        translationFragmentButton.setOnClickListener {
            val searchedWord = translationFragmentSearchedWordEditText.text.toString()
        }
        authButton.setOnClickListener {
            val login = authLoginEditText.text.toString()
            val password  = authPasswordEditText.text.toString()
            val nickname  = authNameEditText.text.toString()
            val passwordValidation = authPasswordValidationEditText.text.toString()
            authVewModel.onButtonClicked(login, nickname, password, passwordValidation)
        }
        authRegisterLinkedTextView.setOnClickListener {
            authVewModel.onLinkedTextClicked()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}