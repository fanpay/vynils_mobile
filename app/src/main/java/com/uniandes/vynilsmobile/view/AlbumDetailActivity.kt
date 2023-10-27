package com.uniandes.vynilsmobile.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uniandes.vynilsmobile.R

class AlbumDetailActivity : AppCompatActivity() {

    companion object{
        const val ALBUM = "album_detail"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_detail)
    }
}