package com.example.orderdeliver.presentation.menu.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.orderdeliver.R
import com.example.orderdeliver.data.models.FoodType
import com.example.orderdeliver.databinding.TypeSubjectItemBinding
import com.example.orderdeliver.presentation.menu.models.TypeFoodModel

class TypeFoodAdapter: RecyclerView.Adapter<TypeFoodAdapter.TypeFoodHolder>() {

    var types = mutableListOf<TypeFoodModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeFoodHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.type_subject_item, parent,false)
        return TypeFoodHolder(view)
    }

    override fun getItemCount(): Int = types.size

    override fun onBindViewHolder(holder: TypeFoodHolder, position: Int){
        holder.bind(types[position])
    }

    class TypeFoodHolder(view: View): RecyclerView.ViewHolder(view){

        private val binding = TypeSubjectItemBinding.bind(view)

        private val context = binding.root.context

        @SuppressLint("ResourceAsColor")
        fun bind(type: TypeFoodModel) = with(binding){

            val text = when(type.foodType){
                FoodType.ALL -> "Всё"
                FoodType.FOOD -> "Еда"
                FoodType.COMBO -> "Комбо"
                FoodType.SAUCE -> "Соусы"
                FoodType.DRINK -> "Коктейли"
            }

            val colorBackground = ContextCompat.getColor(context,if (type.isActivated)R.color.type_subject_color_pressed else R.color.type_subject_color_default)
            val colorText = ContextCompat.getColor(context,if (type.isActivated)R.color.white else R.color.grey)

            typeItemText.text = text
            typeItemText.setTextColor(ColorStateList.valueOf(colorText))
            itemCard.setCardBackgroundColor(ColorStateList.valueOf(colorBackground))

        }

    }

}
