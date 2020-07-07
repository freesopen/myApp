package com.kotlin.base.ext

import android.view.View
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.base.rx.BaseFunc
import com.kotlin.base.rx.BaseFuncBoolean
import com.kotlin.base.rx.BaseSubscriber
import com.trello.rxlifecycle.LifecycleProvider
import com.trello.rxlifecycle.kotlin.bindToLifecycle
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

fun View.onClick(listener:View.OnClickListener){
    this.setOnClickListener(listener);
}

fun View.onClick(method:()->Unit){
    this.setOnClickListener{method};
}