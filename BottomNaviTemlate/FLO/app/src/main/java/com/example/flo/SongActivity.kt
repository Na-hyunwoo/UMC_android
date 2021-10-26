package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity:AppCompatActivity() {

    lateinit var binding : ActivitySongBinding

    private val song:Song=Song()
    private lateinit var player:Player
    private val handler= Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSong()

        player=Player(song.playTime,song.isPlaying)
        player.start()



        binding.songBtnArrowIv.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }

        binding.songPauseIv.setOnClickListener{
            player.isPlaying=false
            setPlayerStatus(false)
        }

        binding.songPlayIv.setOnClickListener{
            player.isPlaying=true
            setPlayerStatus(true)
        }

        if (intent.hasExtra("name")) {
            binding.songNameTv.text = intent.getStringExtra("name")
        }
        if (intent.hasExtra("artist")) {
            binding.songArtistTv.text = intent.getStringExtra("artist")
        }
    }

    private fun initSong(){
        if(intent.hasExtra("title")&&intent.hasExtra("singer")&&intent.hasExtra("playTime")&&intent.hasExtra("isPlaying")){
            song.title=intent.getStringExtra("title")!!
            song.singer=intent.getStringExtra("singer")!!
            song.playTime=intent.getIntExtra("playTime",0)
            song.isPlaying=intent.getBooleanExtra("isPlaying",false)

            binding.songTimeEndTv.text=String.format("%02d:%02d",song.playTime/60,song.playTime%60)
            binding.songNameTv.text=song.title
            binding.songArtistTv.text=song.singer
            setPlayerStatus(song.isPlaying)
        }
    }

//    코드의 직관성을 위해 함수를 작성해 줍니다. 그렇게 되면 가독성도 높아진다. 이렇게 코드를 짜야겠네 앞으로는.
    fun setPlayerStatus(isPlaying : Boolean){
        if(isPlaying){
            binding.songPlayIv.visibility= View.INVISIBLE
            binding.songPauseIv.visibility=View.VISIBLE
        }else{
            binding.songPlayIv.visibility= View.VISIBLE
            binding.songPauseIv.visibility=View.INVISIBLE
        }
    }

    inner class Player(private val playTime:Int,var isPlaying: Boolean) : Thread(){
        private var second = 0

        override fun run() {
            try{
                while(true) {
                    if(second>=playTime){
                        break
                    }


                    if(isPlaying){
                        sleep(1000)
                        second++

                        runOnUiThread{
                            binding.songBarSb.progress=second*1000/playTime
                            binding.songTimeStartTv.text=String.format("%02d:%02d",second/60,second%60)
                        }
                    }
                }
            }catch (e:InterruptedException){
                Log.d("interrupt","쓰레드가 종료되었습니다.")
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        player.interrupt()
    }

}























