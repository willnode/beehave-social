package com.beehavesocial.capstone.view.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beehavesocial.capstone.databinding.ItemSocialMediaBinding
import com.beehavesocial.capstone.model.article.DataItem

class ArticleAdapter:RecyclerView.Adapter<ArticleAdapter.GridViewHolder>() {

    private var listArticle = ArrayList<DataItem>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleAdapter.GridViewHolder {
       val binding = ItemSocialMediaBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return GridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleAdapter.GridViewHolder, position: Int) {
       holder.bind(listArticle[position])
    }

    override fun getItemCount(): Int {
        return listArticle.size
    }

    class GridViewHolder(private val binding:ItemSocialMediaBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(article : DataItem ){
            with(binding){
                tvTitle.text = article.title
                tvDesctiption.text = article.excerpt
                tvViewers.text=article.viewers.toString().trim()
                tvRating.text=article.rating.toString().trim()

            }
        }
    }
    fun setArticle(article: List<DataItem>?){
        if (article == null) return
        this.listArticle.clear()
        this.listArticle.addAll(article)
    }
}