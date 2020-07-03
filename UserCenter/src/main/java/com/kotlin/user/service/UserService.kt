package com.kotlin.user.service

import rx.Observable
import java.util.*

interface UserService {
    fun register(mobile:String,
                 pwd:String,verifyCode:String):Observable<Boolean>;

}