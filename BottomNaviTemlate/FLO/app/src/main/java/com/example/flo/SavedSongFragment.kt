package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentSavedSongBinding

class SavedSongFragment:Fragment() {
    lateinit var binding:FragmentSavedSongBinding
    private var albumData=ArrayList<Album>();

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSavedSongBinding.inflate(inflater,container,false)

        //데이터 리스트 생성 더미 데이터
        albumData.apply{
            add(Album("Butter","방탄소년단(BTS)",R.drawable.img_album_exp2))
            add(Album("Lilac","아이유(IU)",R.drawable.img_album_exp2))
            add(Album("Next Level","에스파(AESPA)",R.drawable.img_album_exp2))
            add(Album("Boy With Luv","방탄소년단(BTS)",R.drawable.img_album_exp2))
            add(Album("BBoom BBoom","모모랜드(MOMOLAND)",R.drawable.img_album_exp2))
            add(Album("Weekend","태연(Tae Yeon)",R.drawable.img_album_exp2))
        }



        //더미데이터와 Adapter 연결
        val lockerRVAdapter=LockerRVAdapter(albumData)
        //리사이클러뷰에 어댑터를 연결
        binding.savedSongContentRv.adapter=lockerRVAdapter

        lockerRVAdapter.setMyItemClickListener(object :LockerRVAdapter.MyItemClickListener{
            override fun onItemClick(album: Album) {
                TODO("Not yet implemented")
            }

//            override fun onItemClick(album: Album) {
//                changeAlbumFragment(album)
//            }

            override fun onRemoveAlbum(position: Int) {
                lockerRVAdapter.removeItem(position)
            }

        })
        //레이아웃 매니저 설정
        binding.savedSongContentRv.layoutManager=
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)


        return binding.root
    }
}