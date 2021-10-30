package com.example.flo

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity:AppCompatActivity() {

    lateinit var binding : ActivitySongBinding

    private val song:Song=Song()
    private lateinit var player:Player
    private val handler= Handler(Looper.getMainLooper())

    //미디어 플레이어
    private var mediaPlayer : MediaPlayer? = null
    //Gson
    private var gson: Gson =Gson()

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
            song.isPlaying=false
            mediaPlayer?.pause()
        }

        binding.songPlayIv.setOnClickListener{
            player.isPlaying=true
            setPlayerStatus(true)
            song.isPlaying=true
            mediaPlayer?.start()
        }

        if (intent.hasExtra("name")) {
            binding.songNameTv.text = intent.getStringExtra("name")
        }
        if (intent.hasExtra("artist")) {
            binding.songArtistTv.text = intent.getStringExtra("artist")
        }
    }

    private fun initSong(){
        if(intent.hasExtra("title")&&intent.hasExtra("singer")&&intent.hasExtra("second") && intent.hasExtra("playTime")&&intent.hasExtra("isPlaying")&&intent.hasExtra("music")){
            song.title=intent.getStringExtra("title")!!
            song.singer=intent.getStringExtra("singer")!!
            song.second=intent.getIntExtra("second",0)
            song.playTime=intent.getIntExtra("playTime",0)
            song.isPlaying=intent.getBooleanExtra("isPlaying",false)
            song.music=intent.getStringExtra("music")!!
            val music=resources.getIdentifier(song.music,"raw",this.packageName)

            binding.songTimeEndTv.text=String.format("%02d:%02d",song.playTime/60,song.playTime%60)
            binding.songNameTv.text=song.title
            binding.songArtistTv.text=song.singer
            setPlayerStatus(song.isPlaying)
            mediaPlayer=MediaPlayer.create(this,music)
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

    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause() //미디어 플레이어 중지
        player.isPlaying=false //쓰레드 중지
        song.isPlaying=false
        song.second=(binding.songBarSb.progress*song.playTime)/1000
        setPlayerStatus(false) //정지상태일 때의 이미지로 전환
        //sharedPreference를 사용 해봅시다. 간단한 데이터 저장!
        val sharedPreferences=getSharedPreferences("song", MODE_PRIVATE)
        val editor=sharedPreferences.edit() //sharedPreferences 조작할 때 사용!
        //Gson을 써보자. 객체를 json으로 바꿔주는 중간다리 !
        val json=gson.toJson(song)
        editor.putString("song",json)
        editor.apply()

    }

    override fun onDestroy() {
        super.onDestroy()
        player.interrupt() //스레드 해제
        mediaPlayer?.release() //미디어 플레이어가 갖고있떤 리소스 해제.
        mediaPlayer=null //미디어 플레이어 해제
    }

}























