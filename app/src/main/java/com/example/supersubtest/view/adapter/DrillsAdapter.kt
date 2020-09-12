package com.example.supersubtest.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.supersubtest.R
import com.example.supersubtest.model.Drill
import com.example.supersubtest.util.Util
import kotlinx.android.synthetic.main.cv_drills.view.*

class DrillsAdapter : RecyclerView.Adapter<DrillsAdapter.DrillHolder>() {

    companion object {
        private val diffUtilCallback = object : DiffUtil.ItemCallback<Drill>() {
            override fun areItemsTheSame(oldItem: Drill, newItem: Drill): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Drill, newItem: Drill) : Boolean {
                return newItem.id == oldItem.id
            }
        }
    }

    val differ = AsyncListDiffer(this, diffUtilCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DrillHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.cv_drills, parent, false))

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: DrillHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    private var onItemClickListener: ((Drill) -> Unit)? = null

    fun setOnItemCLickListener(listener: ((Drill) -> Unit)) {
        onItemClickListener = listener
    }

    inner class DrillHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(drill: Drill) {
            itemView.tvDrillTitle.text = drill.title+" | "
            itemView.tvDrillSubtitle.text = drill.subtitle
            itemView.tvDrillDifficulty.text = drill.difficulty
            itemView.tvDuration.text = Util.getTimeForDrillFormat(drill.duration!!)
            Glide.with(itemView.ivDrill.context)
                .load(Util.getThumbnailForVideo(drill.video?.url))
                .into(itemView.ivDrill)

            itemView.setOnClickListener{ onItemClickListener?.let { drill?.let { it1 -> it(it1) } } }
        }

    }
}