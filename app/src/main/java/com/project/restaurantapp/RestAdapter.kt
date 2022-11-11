package com.project.restaurantapp


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.restaurantapp.databinding.LiRestaurantBinding
import kotlinx.android.synthetic.main.fragment_first.view.*
import kotlinx.android.synthetic.main.li_restaurant.view.*

class RestAdapter(val categories: List<Category>, val recyclerViewHome: RecyclerViewHomeClickListener) : RecyclerView.Adapter<ViewHolder>(){
    private lateinit var recyclerView: RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LiRestaurantBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = categories.get(position)
        item.let {
            holder.apply {
                bind(item, isLinearLayoutManager())
                itemView.tag = item
            }
        }

        holder.itemView.restSelect.setOnClickListener {
            recyclerViewHome.clickOnItem(
                item,
                holder.itemView
            )
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    private fun isLinearLayoutManager() = recyclerView.layoutManager is LinearLayoutManager
}

class ViewHolder(private val binding: LiRestaurantBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Category, isLinearLayoutManager: Boolean) {
        binding.apply {
            restName.text = data.categoryName
        }
    }
}
interface RecyclerViewHomeClickListener {
    fun clickOnItem(data: Category, card: View)
}


