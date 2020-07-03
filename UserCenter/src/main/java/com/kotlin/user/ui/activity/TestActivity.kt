package com.kotlin.user.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_test)
        verticalLayout{
            padding=30
            editText {
                hint="Name"
                textSize=24f
            }
            editText {
                hint="Pwd"
                textSize=24f
            }
            button{
               text="Test"
                onClick {
                    toast("${intent.getIntExtra("id",-1)}")
                }
            }

        }
    }
}