package com.beehavesocial.capstone.view.article

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.beehavesocial.capstone.databinding.ActivityDetailSocialMediaBinding
import com.beehavesocial.capstone.databinding.ContentDetailSocialMediaBinding
import com.beehavesocial.capstone.model.article.DetailArticleResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailSocialMediaActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = 0
    }

    lateinit var binding: ContentDetailSocialMediaBinding
    private val detailArticleViewModel: DetailArticleViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContentDetailSocialMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showDetailArticle()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun detailArticle(article: DetailArticleResponse) {
        if (article != null) {
            with(binding) {
                tvTitle.text = article.data?.title
                source2.text = article.data?.source
                tvCreated.text = article.data?.createdAt
                tvUpdated.text = article.data?.updatedAt
                tvContent.text = article.data?.content
                tvKeyword.text = article.data?.keyword
                tvViewers.text =article.data?.viewers.toString().trim()
                tvRating.text=article.data?.rating.toString().trim()

            }
        }
    }

    private fun showDetailArticle() {
        val extras = intent.extras
        if (extras != null) {
            val id = extras.getInt(EXTRA_DATA.toString(), 0)
            detailArticleViewModel.getDetailArticle(id).observe(this, { article ->
                detailArticle(article)
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()

    }
}