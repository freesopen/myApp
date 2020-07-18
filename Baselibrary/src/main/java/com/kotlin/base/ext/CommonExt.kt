package com.kotlin.base.ext

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.base.rx.BaseFunc
import com.kotlin.base.rx.BaseFuncBoolean
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.utils.DefaultTextWatcher
import com.kotlin.base.utils.GlideUtils
import com.trello.rxlifecycle.LifecycleProvider
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

fun <T> Observable<T>.execute(subscriber: BaseSubscriber<T>
                              ,lifecycleProvider: LifecycleProvider<*>
){
  this.observeOn(AndroidSchedulers.mainThread())
      .compose(lifecycleProvider.bindToLifecycle())
        .subscribeOn(Schedulers.io())//订阅
        .subscribe(subscriber)
}

fun <T> Observable<BaseResp<T>>.convert():Observable<T>{
  return  this.flatMap(BaseFunc())
}
fun <T> Observable<BaseResp<T>>.convertBoolean():Observable<Boolean>{
    return  this.flatMap(BaseFuncBoolean())
}

fun View.onClick(listener:View.OnClickListener):View{
    this.setOnClickListener(listener);
    return this;
}

fun View.onClick(method:()->Unit):View{
   setOnClickListener{method()};
    return this;
}
fun Button.enable(et:EditText,method:()->Boolean){
    val btn=this;
    et.addTextChangedListener(object :DefaultTextWatcher(){
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            super.onTextChanged(s, start, before, count);
            btn.isEnabled=method();
        }
    })
}
/*
    ImageView加载网络图片
 */
fun ImageView.loadUrl(url: String) {
    GlideUtils.loadUrlImage(context, url, this)
}


