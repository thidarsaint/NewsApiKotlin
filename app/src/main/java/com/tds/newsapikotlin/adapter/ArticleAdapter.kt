package com.tds.newsapikotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tds.newsapikotlin.R
import com.tds.newsapikotlin.model.Article
import com.tds.newsapikotlin.toSimpleString
import kotlinx.android.synthetic.main.item_article_list.view.*


class ArticleAdapter(var articleList: List<Article> = ArrayList()): RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>(){

    var mClickListener: ClickListener? = null

    fun setOnClickListener(clickListener: ClickListener){
        this.mClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_article_list, parent, false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bindArticle(articleList.get(position))
    }

    fun updateList(article: List<Article>){
        this.articleList = article
        notifyDataSetChanged()
    }

    inner class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        private var view: View = itemView
        private lateinit var article : Article

        init {
            itemView.setOnClickListener(this)
        }

        fun bindArticle(article: Article){
            this.article = article
            Picasso.get().load(article.urlToImage)
                .placeholder(R.drawable.loadingcolor)
                .into(view.image)

            view.article_title.text = article.title
            view.article_description.text = article.description
            view.article_date.text = toSimpleString(article.publishedAt)
        }

        override fun onClick(v: View?) {
            mClickListener?.onClick(article)
        }
    }

    interface ClickListener{
        fun onClick(article: Article)
    }
}