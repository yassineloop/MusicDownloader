package com.andreacioccarelli.musicdownloader.ui.adapters

import android.app.Activity
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.andreacioccarelli.musicdownloader.R
import com.andreacioccarelli.musicdownloader.data.serializers.Result
import com.andreacioccarelli.musicdownloader.data.serializers.YoutubeSearchResponse
import com.andreacioccarelli.musicdownloader.ui.fragments.BottomDialogFragment
import com.bumptech.glide.Glide

/**
 *  Designed and developed by Andrea Cioccarelli
 */

class ResultsAdapter(response: YoutubeSearchResponse, private val activity: Activity, private val fragmentManager: FragmentManager) : RecyclerView.Adapter<ResultsAdapter.ViewHolder>() {

    val data by lazy { ArrayList<Result>() }

    init {
        data.clear()
        data.addAll(response.items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.result_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        Glide.with(activity)
                .load(data[i].snippet.thumbnails.medium.url)
                .thumbnail(0.1F)
                .into(holder.icon)

        holder.title.text = data[i].snippet.title

        holder.card.setOnClickListener {
            val bottomSheetFragment = BottomDialogFragment(data[i])
            bottomSheetFragment.show(fragmentManager, bottomSheetFragment.tag)
        }

        Handler().post {
            if (holder.title.lineCount == 1) {
                holder.title.height = activity.resources.getDimension(R.dimen.result_thumb_width).toInt()
            }

            holder.titleLayout.visibility = View.VISIBLE
        }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var card: CardView = v.findViewById(R.id.card)
        var icon: ImageView = v.findViewById(R.id.icon)
        var titleLayout: RelativeLayout = v.findViewById(R.id.titleLayout)
        var title: TextView = v.findViewById(R.id.title)
    }
}