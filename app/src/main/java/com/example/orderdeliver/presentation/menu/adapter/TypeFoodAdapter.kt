package com.example.orderdeliver.presentation.menu.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.orderdeliver.R
import com.example.orderdeliver.databinding.TypeSubjectItemBinding
import com.example.orderdeliver.presentation.menu.models.TypeFoodModel
import com.example.orderdeliver.utils.setList

interface TypeFoodState {
    fun tap(id: String, foodTypeId: String)
}

class TypeFoodAdapter(private val typeFoodState: TypeFoodState) :
    RecyclerView.Adapter<TypeFoodAdapter.TypeFoodHolder>() {

    private var foods: ArrayList<TypeFoodModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeFoodHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.type_subject_item, parent, false)
        return TypeFoodHolder(view,typeFoodState)
    }

    override fun getItemCount(): Int = foods.size

    override fun onBindViewHolder(
        holder: TypeFoodHolder,
        position: Int,
    ) {
        println(foods[position])
        holder.bind(foods[position])
    }

    fun updateList(newList: List<TypeFoodModel>){
        val diffCallback = TypeFoodDiffCallback(foods, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        foods.setList(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    class TypeFoodHolder(view: View,private val typeFoodState: TypeFoodState) : RecyclerView.ViewHolder(view) {

        private val binding = TypeSubjectItemBinding.bind(view)

        private val context = binding.root.context

        @SuppressLint("ResourceAsColor")
        fun bind(type: TypeFoodModel) = with(binding) {


            val colorBackground = ContextCompat.getColor(
                context,
                if (type.isActivated) R.color.type_subject_color_pressed else R.color.type_subject_color_default
            )
            val colorText = ContextCompat.getColor(
                context,
                if (type.isActivated) R.color.white else R.color.grey
            )

            typeItemText.text = type.foodType
            typeItemText.setTextColor(ColorStateList.valueOf(colorText))
            itemCard.setCardBackgroundColor(ColorStateList.valueOf(colorBackground))

            binding.root.setOnClickListener {
                typeFoodState.tap(type.id, type.id)
            }

        }

    }

}


private class TypeFoodDiffCallback(
    val oldList: List<TypeFoodModel>,
    val newList: List<TypeFoodModel>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}


