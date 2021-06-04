package com.beehavesocial.capstone.view.article

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.beehavesocial.capstone.databinding.ContentDetailSocialMediaBinding
import com.beehavesocial.capstone.model.article.DetailArticleResponse
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailSocialMediaActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = 0
    }

    lateinit var binding: ContentDetailSocialMediaBinding
    private val detailArticleViewModel: DetailArticleViewModel by viewModels()
    private val ratedViewModel: RatedViewModel by viewModels()


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
                tvViewers.text = article.data?.viewers.toString().trim()
                tvRating.text = article.data?.rating.toString().trim()
                ratingBar.rating=article.data?.rating!!
            }
        }
    }

    private fun showDetailArticle() {
        val extras = intent.extras
        if (extras != null) {
            val id = extras.getInt(EXTRA_DATA.toString(), 0)
            detailArticleViewModel.getDetailArticle(id)
            detailArticleViewModel.artData.observe(this, { article ->
                detailArticle(article)
//                Log.d("detailarticle", article.toString())
                binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                    rated(rating.toInt(), id)
                }
            })
        }
    }

    private fun rated(rating: Int, id: Int) {
        ratedViewModel.rated(id, rating)
        ratedViewModel.action.observe(this, {
            when (it) {
                RatedViewModel.ACTION_RATED_SUCCESS -> {
                    Snackbar.make(binding.root, "Berhasil Voting", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}