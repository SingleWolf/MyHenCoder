package com.example.walker.myhencoder.demo.gift.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.walker.myhencoder.R
import com.example.walker.myhencoder.demo.gift.data.GiftCount

class GiftCountAdapter(private val mData: List<GiftCount>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_gift_count, parent,
                false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ItemViewHolder) {
            val giftCount = mData[position]
            holder.tvCountDesc.text = giftCount.desc
            holder.tvCountValue.text = giftCount.value
        }
    }

    override fun getItemCount(): Int {
        return if (mData.isEmpty()) 0 else mData.size
    }

    internal class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvCountValue = view.findViewById<TextView>(R.id.tvCountValue)
        var tvCountDesc = view.findViewById<TextView>(R.id.tvCountDesc)
    }
}

class GiftShowAdapter(private val mData: List<String>, private val clip: Pair<Int, Int>? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gift_show, parent,
                false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ItemViewHolder) {
            clip?.run {
//                holder.contentLayout.layoutParams = LinearLayout.LayoutParams(first, second)
            }
            val giftCount = mData[position]
        }
    }

    override fun getItemCount(): Int {
        return if (mData.isEmpty()) 0 else mData.size
    }

    internal class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var contentLayout = view.findViewById<LinearLayout>(R.id.contentLayout)
        var tvIcon = view.findViewById<ImageView>(R.id.ivIcon)
        var tvDesc = view.findViewById<TextView>(R.id.tvDesc)
        var tvPrice = view.findViewById<TextView>(R.id.tvPrice)
    }
}
