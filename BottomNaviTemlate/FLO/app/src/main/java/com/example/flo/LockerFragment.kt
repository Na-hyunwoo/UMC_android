package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayoutMediator


class LockerFragment : Fragment() {

    lateinit var binding: FragmentLockerBinding
    private var albumData=ArrayList<Album>();

    val information= arrayListOf("저장한 곡","음악파일")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        val lockerAdapter =LockerViewpagerAdapter(this)
        binding.lockerContentVp.adapter=lockerAdapter
        TabLayoutMediator(binding.lockerMenuTl,binding.lockerContentVp){
            tab, position->
            tab.text=information[position]
        }.attach()

        return binding.root



    }




}