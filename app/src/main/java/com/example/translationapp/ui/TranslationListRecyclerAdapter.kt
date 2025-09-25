package com.example.translationapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.translationapp.R
import com.example.translationapp.databinding.TranslationCardViewBinding

class TranslationListRecyclerAdapter(
    private val translationActionListener: TranslationActionListener,
) : RecyclerView.Adapter<TranslationListRecyclerAdapter.ProfileOutfitsViewHolder>() {

    class ProfileOutfitsViewHolder(val binding: TranslationCardViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    var data: List<TranslationDetailsData> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileOutfitsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TranslationCardViewBinding.inflate(inflater, parent, false)

        return ProfileOutfitsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileOutfitsViewHolder, position: Int) {
        val translatedItem = data[position]
        val context = holder.itemView.context
        holder.itemView.tag = translatedItem
        holder.binding.translationCardFavoriteButton.tag = translatedItem
        holder.binding.translationCardRemoveButton.tag = translatedItem


        with(holder.binding) {
            translationCardWordsText.text = context.getString(
                R.string.translations_list_card_view_text,
                translatedItem.searchedWord,
                translatedItem.translatedWord,
            )
            translationCardFavoriteButton.apply {
                setLiked(translatedItem.isFavorite)
                setOnClickListener {
                    translationActionListener.onTranslationLike(translatedItem)
                }
            }
            translationCardRemoveButton.setOnClickListener {
                translationActionListener.onTranslationRemove(translatedItem)
            }
        }
    }
}