package com.beehavesocial.capstone.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.beehavesocial.capstone.R
import com.beehavesocial.capstone.databinding.FragmentSocialMediaBinding
import com.beehavesocial.capstone.view.article.ArticleAdapter
import com.beehavesocial.capstone.view.article.ArticleViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SocialMediaFragment : Fragment() {

    private lateinit var binding: FragmentSocialMediaBinding
    private val articleViewModel: ArticleViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSocialMediaBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showArticle()
    }

    private fun showArticle(){
        if (activity != null){
            val articleAdapter = ArticleAdapter()
            articleViewModel.getArticle().observe(viewLifecycleOwner,{ article ->
                articleAdapter.setArticle(article)
                with(binding.lvSocialMedia){
                    adapter = articleAdapter
                    layoutManager = GridLayoutManager(context,2)
                    setHasFixedSize(true)
//
//                    articleViewModel.getArticle().observe(viewLifecycleOwner, {
//                        if (it.isNotEmpty()) {
                            articleAdapter.setArticle(article)
//                        }
//                    })

                }
            })
        }
    }
}