package com.example.appkp.adapter

import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appkp.R
import kotlinx.android.synthetic.main.view_pager_item.view.*

class ViewPagerAdapter(
    val images: TypedArray,
    val title: Array<String>,
    val description: Array<String>
) : RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {


    inner class PagerViewHolder(item: View) : RecyclerView.ViewHolder(item)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_pager_item, parent, false)

        return PagerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return title.size
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val imgCurrentPositon = images.getResourceId(position, -1)
        val titleCurrentPositon = title[position]
        val descriptionCurrentPositon = description[position]

        holder.itemView.apply {
            imgViewPager.setImageResource(imgCurrentPositon)
            tvTitleViewPager.text = titleCurrentPositon
            tvDescViewPager.text = descriptionCurrentPositon
        }
    }
}
