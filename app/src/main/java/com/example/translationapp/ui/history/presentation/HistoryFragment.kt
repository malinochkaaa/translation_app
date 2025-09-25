package com.example.translationapp.ui.history.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.translationapp.databinding.FragmentHistoryBinding
import com.example.translationapp.ui.TranslationActionListener
import com.example.translationapp.ui.TranslationDetailsData
import com.example.translationapp.ui.TranslationListRecyclerAdapter
import com.example.translationapp.ui.TranslationService

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private var translationsListAdapter: TranslationListRecyclerAdapter? = null
    private var translationService = TranslationService()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val historyViewModel =
            ViewModelProvider(this)[HistoryViewModel::class.java]

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.favoritesFragmentGallery.isVisible = true
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpTranslationsList()
    }

    private fun setUpTranslationsList() {
        translationsListAdapter = TranslationListRecyclerAdapter(
            object : TranslationActionListener {
                override fun onTranslationLike(translation: TranslationDetailsData) {
                    translationService.likePerson(translation)
                }

                override fun onTranslationRemove(translation: TranslationDetailsData) {
                    translationService.removePerson(translation)
                }

            }
        )

        binding.favoritesFragmentGallery.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = translationsListAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        translationsListAdapter = null
    }
}