package com.uniandes.vynilsmobile.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.uniandes.vynilsmobile.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import android.widget.Toast
import com.uniandes.vynilsmobile.view.AlbumDetailActivity.Companion.ALBUM
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.launch




private lateinit var binding:ActivityMainBinding
private lateinit var adapter: AlbumAdapter
private val albums = mutableListOf<Album>()
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        getAlbums()
        binding.btAlbums.setOnClickListener { getAlbums() }

    }

    private fun initRecyclerView() {
        adapter = AlbumAdapter(albums) { album ->
            navigateToDetail(album)
        }
        binding.rvAlbumLista.layoutManager = LinearLayoutManager(this)
        binding.rvAlbumLista.adapter = adapter

    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://vynils-back-heroku.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getAlbums() {
        CoroutineScope(Dispatchers.IO).launch {
            // Realiza la solicitud GET
            val apiService = getRetrofit().create(ApiService::class.java)
            val response = apiService.getAlbums()

            runOnUiThread {
                albums.clear()
                albums.addAll(response)
                adapter.notifyDataSetChanged()
            }

        }
    }

    private fun navigateToDetail(album: Album) {
        val intent = Intent(this, AlbumDetailActivity::class.java)
        intent.putExtra(ALBUM, album)
        startActivity(intent)
    }
}
