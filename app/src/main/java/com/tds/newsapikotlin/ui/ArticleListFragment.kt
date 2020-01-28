package com.tds.newsapikotlin.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tds.dailynews.viewmodel.ArticleViewModel

import com.tds.newsapikotlin.R
import com.tds.newsapikotlin.adapter.ArticleAdapter
import com.tds.newsapikotlin.model.ArticleResult
import kotlinx.android.synthetic.main.fragment_article_list.*

/**
 * A simple [Fragment] subclass.
 */
class ArticleListFragment : Fragment() {
    private lateinit var articleAdapter : ArticleAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var articleViewModel: ArticleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewManager = LinearLayoutManager(activity)
        articleAdapter = ArticleAdapter()
        recycler_article.adapter = articleAdapter
        recycler_article.layoutManager = viewManager

        observeViewModel()
    }

    fun observeViewModel(){
        articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)
        articleViewModel.getResults().observe(this, Observer<ArticleResult> {result->
            recycler_article.visibility = View.VISIBLE
            articleAdapter.updateList(result.articles)
        })

        articleViewModel.getError().observe(this, Observer<Boolean> {isError ->
            if(isError) {
                error_msg.visibility = View.VISIBLE
                recycler_article.visibility = View.GONE
            }else{
                error_msg.visibility = View.GONE
            }
        })

        articleViewModel.getLoading().observe(this, Observer<Boolean> {isLoading ->
            loadingView.visibility = (if(isLoading) View.VISIBLE else View.INVISIBLE)
            if(isLoading){
                error_msg.visibility = View.GONE
                recycler_article.visibility = View.GONE
            }
        })
    }

    override fun onResume() {
        super.onResume()
        articleViewModel.loadResults()
    }


}
