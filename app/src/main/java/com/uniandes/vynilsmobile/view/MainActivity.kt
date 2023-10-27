package com.uniandes.vynilsmobile.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
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

    }

    private fun initRecyclerView() {
        adapter = AlbumAdapter(albums)
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

            runOnUiThread{
                albums.clear()
                albums.addAll(response)
                adapter.notifyDataSetChanged()
            }

        }
    }
}