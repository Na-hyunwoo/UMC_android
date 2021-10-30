package com.example.flo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    //Gson
    private var gson: Gson =Gson()
    //Song
    private var song: Song=Song()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val song = Song("라일락","아이유(IU)",0,215,false,"music_goosebumps")
        setMiniPlayer(song)

        binding.mainPlayerLayout.setOnClickListener{
            val intent=Intent(this,SongActivity::class.java)

            intent.putExtra("title",song.title)
            intent.putExtra("singer",song.singer)
            intent.putExtra("second",song.second)
            intent.putExtra("playTime",song.playTime)
            intent.putExtra("isPlaying",song.isPlaying)
            intent.putExtra("music",song.music)

            startActivity(intent)



        }


        initNavigation()

        binding.mainBnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

            }
            false
        }

    }

    private fun initNavigation() {
        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

    }

    fun setMiniPlayer(song: Song){
        binding.albumNameTv.text=song.title
        binding.albumArtist6Tv.text=song.singer
        binding.mainBarSb.progress=(song.second*1000/song.playTime)
    }

    override fun onStart() {
        super.onStart()
        val sharedPreferences =getSharedPreferences("song", MODE_PRIVATE)
        val jsonSong=sharedPreferences.getString("song",null)

        song=if(jsonSong==null){
            Song("라일락","아이유(IU)",0,215,false,"music_goosebumps")
        }else{
            gson.fromJson(jsonSong,Song::class.java)
        }

        setMiniPlayer(song)

    }

}

