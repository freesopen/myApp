package com.kotlin.mall.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.base.widgets.BannerImageLoader
import com.kotlin.mall.R
import com.kotlin.mall.common.*
import com.kotlin.mall.ui.adapter.HomeDiscountAdapter
import com.kotlin.mall.ui.adapter.TopicAdapter
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_home.*
import me.crosswall.lib.coverflow.CoverFlow

class HomeFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_home, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBanner();
        initNews();
        initDiscount();
        initTopic()
    }



    private fun initBanner( ) {
        mHomeBanner.setImageLoader(BannerImageLoader())
        mHomeBanner.setImages(
            listOf(
                HOME_BANNER_ONE, HOME_BANNER_TWO,
                HOME_BANNER_THREE, HOME_BANNER_FOUR
            )
        )
        mHomeBanner.setBannerAnimation(com.youth.banner.Transformer.Accordion)
        mHomeBanner.setDelayTime(2000)
        //设置指示器位置(当banner 模式中有指示器时)
        mHomeBanner.setIndicatorGravity(BannerConfig.RIGHT)
        mHomeBanner.start();
    }

    private fun initNews() {
        mNewsFlipperView.setData(arrayOf("夏日炎炎，第一波福利还有30秒" +
                "到达战场", "新用户立领1000元优惠券"))

    }
private fun initDiscount(){
    val manager= LinearLayoutManager(this.activity.applicationContext);
    manager.orientation=LinearLayoutManager.HORIZONTAL;
    mHomeDiscountRv.layoutManager=manager;

    val discountAdapter=HomeDiscountAdapter(activity)
    mHomeDiscountRv.adapter=discountAdapter;
    discountAdapter.setData(mutableListOf(HOME_DISCOUNT_ONE,
        HOME_DISCOUNT_TWO,
        HOME_DISCOUNT_THREE,
        HOME_DISCOUNT_FOUR,
        HOME_DISCOUNT_FIVE))
}
    private fun initTopic() {
        mTopicPager.adapter = TopicAdapter(this.activity.applicationContext, listOf(HOME_TOPIC_ONE, HOME_TOPIC_TWO, HOME_TOPIC_THREE, HOME_TOPIC_FOUR, HOME_TOPIC_FIVE))
        mTopicPager.currentItem = 1
        mTopicPager.offscreenPageLimit = 5

        CoverFlow.Builder().with(mTopicPager).scale(0.3f).pagerMargin(-30.0f).spaceSize(0.0f).build()
    }
}