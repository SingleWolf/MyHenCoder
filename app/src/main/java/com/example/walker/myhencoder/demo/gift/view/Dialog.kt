package com.liao.lizhi.chat.gift.view

import android.app.Activity
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.example.walker.myhencoder.R
import com.example.walker.myhencoder.delegate.OnRecyclerItemClickListener
import com.example.walker.myhencoder.demo.gift.data.GiftCount
import com.example.walker.myhencoder.demo.gift.view.GiftCountAdapter
import com.example.walker.myhencoder.demo.gift.view.dp2px
import com.example.walker.myhencoder.view.RecycleViewDivider

class GiftSelectDialog(val context: Activity, val nickName: String, val avatarUrl: String, val coinSum: Int) : PopupWindow(context) {

    init {
        initView()
    }

    private fun initView() {
        with(this) {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            isFocusable = true
            setBackgroundDrawable(context.resources.getDrawable(android.R.color.transparent, null))
            isOutsideTouchable = true
            val contentView = GiftPopupView(context)
            contentView.setAvatar(avatarUrl)
            contentView.setNickName(nickName)
            contentView.setCoinSum(coinSum)
            setContentView(contentView)
        }
    }

    fun show() {
        showAtLocation(context.window.decorView, Gravity.BOTTOM, 0, 0)
    }

}


class GiftCountDialog(val context: Activity, val block: ((value: String) -> Unit)?) : PopupWindow(context) {

    private val countList = mutableListOf<GiftCount>()
    private val countAdapter by lazy {
        GiftCountAdapter(countList)
    }

    init {
        initView()
    }

    private fun initView() {
        with(this) {
            width = ViewGroup.LayoutParams.WRAP_CONTENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            isFocusable = true
            setBackgroundDrawable(context.resources.getDrawable(android.R.color.transparent, null))
            isOutsideTouchable = true
        }

        val contentView = LayoutInflater.from(context).inflate(R.layout.layout_gift_count_list, null)

        val recyclerView = contentView.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.run {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 1, Color.WHITE))
            adapter = countAdapter
            addOnItemTouchListener(object : OnRecyclerItemClickListener(this) {
                override fun onItemLongClick(vh: RecyclerView.ViewHolder?) {

                }

                override fun onItemClick(vh: RecyclerView.ViewHolder?) {
                    vh?.let {
                        val pos = it.layoutPosition
                        block?.invoke(countList[pos].value)
                        this@GiftCountDialog.dismiss()
                    }
                }
            })
        }

        setContentView(contentView)
    }

    fun replaceData(data: MutableList<GiftCount>?) {
        data?.run {
            countList.clear()
            countList.addAll(data)
            countAdapter.notifyDataSetChanged()
        }
    }

    fun showAttachView(attachView: View) {
        showAsDropDown(attachView, 0, (-dp2px(context,230f + 40f + 10f)))
    }
}
