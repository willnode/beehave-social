package com.beehavesocial.capstone.view.article

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.beehavesocial.capstone.R
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
                tvContent.text =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Html.fromHtml(article.data?.content, Html.FROM_HTML_MODE_COMPACT)
                    } else {
                        Html.fromHtml(article.data?.content)
                    }

                ratingBarTotal.rating = article.data?.rating!!
                ratingBarUser.rating =
                    if (article.data?.userRating == null) {
                        0F
                    } else {
                        article.data?.userRating
                    }

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
                binding.ratingBarUser.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
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
                    val snackbar =
                        Snackbar.make(binding.root, R.string.successVoting, Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.parseColor("#ffb74d"))
                    snackbar.show()
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}