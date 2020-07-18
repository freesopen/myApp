package com.kotlin.mall.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.utils.GlideUtils
import com.kotlin.mall.R
import kotlinx.android.synthetic.main.layout_home_discount_item.view.*

class HomeDiscountAdapter(context: Context) : BaseRecyclerViewAdapter
<String, HomeDiscountAdapter.ViewHoler>(context) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        iewType: Int
    ): ViewHoler {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_home_discount_item, parent, false)

        return ViewHoler(view);
    }

    override fun onBindViewHolder(holder: ViewHoler, position: Int) {
        super.onBindViewHolder(holder, position)
        GlideUtils.loadUrlImage(mContext,dataList[position],
            holder.itemView.mGoodsIconIv)
        holder.itemView.mDiscountAfterTv.text="$400"
        holder.itemView.mDiscountBeforeTv.text="$1000"
    }
    class ViewHoler(view: View) : RecyclerView.ViewHolder(view) {
init {
    view.mDiscountBeforeTv.paint.flags=Paint.STRIKE_THRU_TEXT_FLAG
    view.mDiscountBeforeTv.paint.isAntiAlias=true
}

    }

}