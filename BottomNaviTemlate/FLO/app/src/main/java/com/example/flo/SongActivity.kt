package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity:AppCompatActivity() {

    lateinit var binding : ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.songBtnArrowIv.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }

        binding.songPlayIv.setOnClickListener{
            setPlayerStatus(false)
        }

        binding.songPauseIv.setOnClickListener{
            setPlayerStatus(true)
        }

        if (intent.hasExtra("name")) {
            binding.songNameTv.text = intent.getStringExtra("name")
        }
        if (intent.hasExtra("artist")) {
            binding.songArtistTv.text = intent.getStringExtra("artist")
        }
    }
//    코드의 직관성을 위해 함수를 작성해 줍니다. 그렇게 되면 가독성도 높아진다. 이렇게 코드를 짜야겠네 앞으로는.
    fun setPlayerStatus(isPlaying : Boolean){
        if(isPlaying){
            binding.songPlayIv.visibility= View.VISIBLE
            binding.songPauseIv.visibility=View.GONE
        }else{
            binding.songPlayIv.visibility= View.GONE
            binding.songPauseIv.visibility=View.VISIBLE
        }
    }
}