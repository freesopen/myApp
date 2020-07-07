package com.kotlin.user.service.impl

import com.kotlin.base.ext.convertBoolean
import com.kotlin.user.data.repository.UserRepository
import com.kotlin.user.service.UserService
import rx.Observable
import javax.inject.Inject
class UserServiceImpl @Inject constructor() : UserService {

    @Inject
    lateinit var repository: UserRepository;
    override fun register(
        mobile: String, pwd: String, verifyCode: String
    ): Observable<Boolean> {
        return repository.register(mobile, pwd, verifyCode)
            .convertBoolean();
    }


}

