package com.example.forest.ui.main.hoem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.forest.R
import com.example.forest.databinding.ItemHomeReceivedEventBinding
import com.example.forest.entity.ArticleEntity

class HomeAdapter : PagingDataAdapter<ArticleEntity, HomePagedViewHolder>(diffCallback) {
    companion object {

        private val diffCallback: DiffUtil.ItemCallback<ArticleEntity> =
            object : DiffUtil.ItemCallback<ArticleEntity>() {

                override fun areItemsTheSame(
                    oldItem: ArticleEntity,
                    newItem: ArticleEntity
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: ArticleEntity,
                    newItem: ArticleEntity
                ): Boolean {
                    return oldItem.title == newItem.title
                }
            }
    }

    override fun onBindViewHolder(holder: HomePagedViewHolder, position: Int) {
        getItem(position)?.let { holder.binds(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePagedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_received_event, parent, false)
        return HomePagedViewHolder(view)
    }
}

class HomePagedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemHomeReceivedEventBinding.bind(view)

    fun binds(data: ArticleEntity) {
        binding.tvEventContent.text = data.title
        binding.tvEventTime.text = data.niceDate;

//        with(binding.ivAvatar) {
//            Glide.with(context)
//                .load(data.link)
//                .apply(RequestOptions().circleCrop())
//                .into(this)
//        }
    }
}