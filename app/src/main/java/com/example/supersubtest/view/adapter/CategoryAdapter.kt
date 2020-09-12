package com.example.supersubtest.view.adapter

import android.graphics.drawable.PictureDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.supersubtest.R
import com.example.supersubtest.model.Category
import kotlinx.android.synthetic.main.cv_category.view.*


class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    // Diff util call back
    companion object {
        private val diffUtilCallback = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Category, newItem: Category) : Boolean {
                return newItem.id == oldItem.id
            }
        }
    }

    inner class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view)

    val differ = AsyncListDiffer(this, diffUtilCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoryHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.cv_category, parent, false)
    )
    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class CategoryHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(category: Category) {
            itemView.tvCategoryName.text = category.name
        }
    }
}