package com.example.orderdeliver.presentation.menu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.orderdeliver.R
import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.databinding.FoodItemBinding

interface FoodActionState{
    fun select(foodDataModel: FoodDataModel)
}
class MenuAdapter(private val foodActionState: FoodActionState): ListAdapter<FoodDataModel,MenuAdapter.MenuHolder>(MenuItemCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.food_item, parent,false)
        return MenuHolder(view,foodActionState)
    }


    override fun onBindViewHolder(holder: MenuHolder, position: Int) = holder.bind(getItem(position))

    class MenuHolder(view: View, private val foodActionState: FoodActionState): RecyclerView.ViewHolder(view){

        private val binding = FoodItemBinding.bind(view)

        fun bind(foodDataModel: FoodDataModel) = with(binding){
            nameFood.text = foodDataModel.name
            descriptionFood.text = foodDataModel.description
            imageView.setImageResource(foodDataModel.imageResource)
            priceText.text = "от ${foodDataModel.price} $"

            binding.root.setOnClickListener { foodActionState.select(foodDataModel) }

        }

    }

}

private class MenuItemCallback : DiffUtil.ItemCallback<FoodDataModel>(){
    override fun areItemsTheSame(oldItem: FoodDataModel, newItem: FoodDataModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FoodDataModel, newItem: FoodDataModel): Boolean {
        return oldItem == newItem
    }

}