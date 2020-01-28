package com.l3ios.androidpaginglibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.l3ios.androidpaginglibrary.ui.itemkeyed.ItemKeyedFragment
import com.l3ios.androidpaginglibrary.ui.pagekeyed.PageKeyedFragment

class PageListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_list_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ItemKeyedFragment.newInstance())
                .commitNow()
        }

//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, PageKeyedFragment.newInstance())
//                .commitNow()
//        }
    }
}
