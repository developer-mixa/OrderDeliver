package com.example.orderdeliver.presentation.menu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.orderdeliver.R
import com.example.orderdeliver.domain.models.FoodDataModel
import com.example.orderdeliver.databinding.FoodItemBinding
import com.example.orderdeliver.presentation.models.FoodListItem

interface FoodActionState{
    fun select(foodDataModel: FoodDataModel)
    fun addBasket(foodDataModel: FoodDataModel)
}
class MenuAdapter(private val foodActionState: FoodActionState): PagingDataAdapter<FoodListItem,MenuAdapter.MenuHolder>(MenuItemCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.food_item, parent,false)
        return MenuHolder(view,foodActionState)
    }


    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
    }

    class MenuHolder(view: View, private val foodActionState: FoodActionState): RecyclerView.ViewHolder(view){

        private val binding = FoodItemBinding.bind(view)

        fun bind(foodListItem: FoodListItem) = with(binding){
            nameFood.text = foodListItem.name
            descriptionFood.text = foodListItem.description
            imageView.setImageResource(foodListItem.imageResource)

            priceText.text = foodListItem.priceText

            if (foodListItem.priceWithDiscountText != null) {
                textDiscount.text = foodListItem.priceWithDiscountText
            }

            textDiscount.isVisible = foodListItem.priceWithDiscountText != null

            buttonAddBasket.setOnClickListener {
                foodActionState.addBasket(foodListItem.original)
            }

            binding.root.setOnClickListener { foodActionState.select(foodListItem.original) }

        }

    }

}

private class MenuItemCallback : DiffUtil.ItemCallback<FoodListItem>(){
    override fun areItemsTheSame(oldItem: FoodListItem, newItem: FoodListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FoodListItem, newItem: FoodListItem): Boolean {
        return oldItem == newItem
    }

}