package com.image.mozaic.ui.mainActivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.image.mozaic.R
import com.image.mozaic.ui.mozaicFragment.FragmentMozaic

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.frag_container, FragmentMozaic(), FragmentMozaic.TAG).commit()
    }

}
