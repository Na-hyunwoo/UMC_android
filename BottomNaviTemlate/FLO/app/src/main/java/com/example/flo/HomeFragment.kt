package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentHomeBinding
import com.google.gson.Gson


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private var albumData=ArrayList<Album>();

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

//        binding.homeAlbumExp3Iv.setOnClickListener{
//            (context as MainActivity).supportFragmentManager.beginTransaction()
//                .replace(R.id.main_frm,AlbumFragment())
//                .commitAllowingStateLoss()
//        }

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
        val albumRVAdapter=AlbumRVAdapter(albumData)
        //리사이클러뷰에 어댑터를 연결
        binding.homeTodayAlbumRv.adapter=albumRVAdapter

        albumRVAdapter.setMyItemClickListener(object :AlbumRVAdapter.MyItemClickListener{

            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }

            override fun onRemoveAlbum(position: Int) {
                albumRVAdapter.removeItem(position)
            }

        })
        //레이아웃 매니저 설정
        binding.homeTodayAlbumRv.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)


        val bannerAdapter=BannerViewpagerAdapter(this)

        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))

        binding.homeBannerVp.adapter=bannerAdapter
        binding.homeBannerVp.orientation=ViewPager2.ORIENTATION_HORIZONTAL

        return binding.root
    }

    private fun changeAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val albumJson = gson.toJson(album)
                    putString("album", albumJson)
                }
            })
            .commitAllowingStateLoss()
    }
}