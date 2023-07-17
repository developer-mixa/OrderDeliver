package com.example.orderdeliver.presentation.menu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.orderdeliver.R
import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.databinding.FoodItemBinding

class MenuAdapter: RecyclerView.Adapter<MenuAdapter.MenuHolder>() {

    var foods = mutableListOf<FoodDataModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.food_item, parent,false)
        return MenuHolder(view)
    }

    override fun getItemCount(): Int = foods.size

    override fun onBindViewHolder(holder: MenuHolder, position: Int) = holder.bind(foods[position])

    class MenuHolder(view: View): RecyclerView.ViewHolder(view){

        val binding = FoodItemBinding.bind(view)

        fun bind(foodDataModel: FoodDataModel) = with(binding){
            nameFood.text = foodDataModel.name
            descriptionFood.text = foodDataModel.description
            imageView.setImageResource(foodDataModel.imageResource)
            priceText.text = "от ${foodDataModel.price} $"
        }

    }

}