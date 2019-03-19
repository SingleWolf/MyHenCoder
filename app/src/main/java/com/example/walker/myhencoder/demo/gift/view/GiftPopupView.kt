package com.liao.lizhi.chat.gift.view

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.walker.myhencoder.R
import com.example.walker.myhencoder.demo.gift.data.GiftCount
import com.example.walker.myhencoder.demo.gift.view.page.PageIndicatorView
import com.example.walker.myhencoder.demo.gift.view.page.PageRecyclerView
import com.example.walker.myhencoder.demo.gift.view.page.PageRecyclerView.PageAdapter
import com.walker.core.util.ToastUtils


/**
 * @author walker zheng
 * @date 2019/3/13
 * @desc 礼物选择泡泡层
 */
class GiftPopupView : LinearLayout {

    private lateinit var tvNickName: TextView
    private lateinit var ivAvatar: ImageView
    private lateinit var tvGiftCount: TextView
    private lateinit var tvSend: TextView
    private lateinit var tvEnableCoin: TextView
    private lateinit var contentGift: PageRecyclerView

    private val dataList = mutableListOf<String>()
    private lateinit var giftShowAdapter: PageAdapter<String>

    private var selectedGiftCount = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    ) {
        init(context, attrs)
    }

    fun init(context: Context, attrs: AttributeSet?) {
        val contentView = LayoutInflater.from(context).inflate(R.layout.layout_gift_popup, this)
        tvNickName = contentView.findViewById(R.id.tvNickName) as TextView
        ivAvatar = contentView.findViewById(R.id.ivReceiverAvatar) as ImageView
        tvGiftCount = contentView.findViewById(R.id.tvGiftCount) as TextView
        tvSend = contentView.findViewById(R.id.tvSend) as TextView
        tvEnableCoin = contentView.findViewById(R.id.tvEnableCoin) as TextView
        contentGift = contentView.findViewById(R.id.contentGift) as PageRecyclerView
        tvGiftCount.setOnClickListener { v -> showCountSelectDialog(v) }
        tvSend.setOnClickListener { onSendGift() }

        initData()

        initPageRecyclerView()
    }

    private fun initPageRecyclerView() {
        // 设置指示器
        contentGift.setIndicator(findViewById<View>(R.id.indicator) as PageIndicatorView)
        // 设置行数和列数
        contentGift.setPageSize(2, 4)
        // 设置页间距
        contentGift.setPageMargin(15)

        giftShowAdapter = contentGift.PageAdapter(dataList, object : PageRecyclerView.CallBack {
            override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
                val view = LayoutInflater.from(context).inflate(R.layout.item_gift_show, parent, false)
                return MyHolder(view)
            }

            override fun onItemClickListener(view: View?, position: Int) {

            }

            override fun onItemLongClickListener(view: View?, position: Int) {

            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

            }
        })
        // 设置数据
        contentGift.adapter = giftShowAdapter
    }

    private fun initData() {
        dataList.add("1")
        dataList.add("2")
        dataList.add("3")
        dataList.add("4")
        dataList.add("5")
        dataList.add("6")
        dataList.add("7")
        dataList.add("8")
        dataList.add("9")
        dataList.add("10")
        dataList.add("11")
        dataList.add("12")
        dataList.add("13")
        dataList.add("14")
        dataList.add("15")
        dataList.add("16")
        dataList.add("17")
        dataList.add("18")
        dataList.add("19")
        dataList.add("20")
        dataList.add("21")
        dataList.add("22")
    }

    private fun onSendGift() {
        ToastUtils.showShort("送🎁喽")
    }

    private fun showCountSelectDialog(v: View?) {
        v?.run {
            val dialog = GiftCountDialog(context as Activity) { value ->
                tvGiftCount.text = value
            }
            val dataList = mutableListOf<GiftCount>()
            dataList.add(GiftCount("1", "一心一意"))
            dataList.add(GiftCount("10", "十全十美"))
            dataList.add(GiftCount("66", "好运连连"))
            dataList.add(GiftCount("520", "我爱你"))
            dataList.add(GiftCount("1314", "一生一世"))
            dataList.add(GiftCount("All in", "清空余额"))
            dialog.replaceData(dataList)
            dialog.showAttachView(v)
        }
    }

    fun setNickName(nickName: String?) {
        nickName?.let {
            tvNickName.text = it
        }
    }

    fun setAvatar(avatarUrl: String?) {
        avatarUrl?.let {
        }
    }

    fun setCoinSum(value: Int) {
        tvEnableCoin.text = value.toString()
    }

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
        var contentLayout = view.findViewById<LinearLayout>(com.example.walker.myhencoder.R.id.contentLayout)
        var tvIcon = view.findViewById<ImageView>(com.example.walker.myhencoder.R.id.ivIcon)
        var tvDesc = view.findViewById<TextView>(com.example.walker.myhencoder.R.id.tvDesc)
        var tvPrice = view.findViewById<TextView>(com.example.walker.myhencoder.R.id.tvPrice)
    }
}