package com.example.orderdeliver.presentation.basket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.orderdeliver.R
import com.example.orderdeliver.data.models.BasketModel
import com.example.orderdeliver.data.models.FoodDataModel
import com.example.orderdeliver.databinding.BasketItemBinding
import com.example.orderdeliver.setList

interface BasketCountState{

    fun plus(foodDataModel: FoodDataModel)

    fun minus(id: Int)

}

class BasketAdapter(private val basketCountState: BasketCountState): RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    private var baskets: ArrayList<BasketModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.basket_item, parent,false)
        return BasketViewHolder(view, basketCountState = basketCountState)
    }

    override fun getItemCount(): Int = baskets.size

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
            holder.bind(baskets[position])
    }

    override fun onBindViewHolder(
        holder: BasketViewHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        if (payloads.isEmpty())onBindViewHolder(holder, position)
        else holder.updateCount(payloads.last() as Bundle)
    }

    fun updateList(newList: List<BasketModel>){
        val diffCallback = BasketDiffCallback(baskets, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback,true)
        diffResult.dispatchUpdatesTo(this)

        baskets.setList(newList)
    }

    class BasketViewHolder(view: View,private val basketCountState: BasketCountState): RecyclerView.ViewHolder(view){
        private val binding: BasketItemBinding = BasketItemBinding.bind(view)
        fun bind(basketModel: BasketModel) = with(binding){
            val foodDataModel = basketModel.foodDataModel
            imageProduct.setImageResource(foodDataModel.imageResource)
            textCountSubjects.text = basketModel.count.toString()
            textPrice.text = "${foodDataModel.price * basketModel.count} $"
            textNameSubject.text = foodDataModel.name

            minusContainer.setOnClickListener {
                basketCountState.minus(foodDataModel.id)
            }

            plusContainer.setOnClickListener {
                basketCountState.plus(foodDataModel)
            }

        }
        fun updateCount(bundle: Bundle) = with(binding){
            if (bundle.containsKey("image")){
                binding.textCountSubjects.text = bundle.getInt("image",0).toString()
            }
        }
    }

}

fun generateBasketPayload(oldItem: BasketModel, newItem: BasketModel): Bundle?{
    val bundle = Bundle()
    if (oldItem.count != newItem.count){
        bundle.putInt("image", newItem.count)
    }
    if (bundle.isEmpty) return null
    return bundle
}

private class BasketDiffCallback(
    private val oldList: List<BasketModel>,
    private val newList: List<BasketModel>,
): DiffUtil.Callback(){
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].foodDataModel.id == newList[newItemPosition].foodDataModel.id
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return generateBasketPayload(oldList[oldItemPosition], newList[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}