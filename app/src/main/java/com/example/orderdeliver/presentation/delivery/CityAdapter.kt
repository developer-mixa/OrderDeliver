package com.example.orderdeliver.presentation.delivery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.orderdeliver.R
import com.example.orderdeliver.databinding.CityItemBinding
import com.example.orderdeliver.presentation.delivery.models.CityModel
import com.example.orderdeliver.utils.setList

class CityAdapter : RecyclerView.Adapter<CityAdapter.CityHolder>() {

    private val suggests =  ArrayList<CityModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.city_item, parent, false)
        return CityHolder(view)
    }

    override fun getItemCount(): Int = suggests.size

    override fun onBindViewHolder(holder: CityHolder, position: Int) = holder.bind(suggests[position])

    class CityHolder(view: View) : ViewHolder(view){
        private val binding: CityItemBinding = CityItemBinding.bind(view)

        fun bind(cityModel: CityModel) = with(binding){
            textTitle.text = cityModel.title
            textSubTitle.text = cityModel.subTitle
        }

    }

    fun updateList(newList: List<CityModel>){
        val diffCallback = CityDiffCallback(suggests, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        suggests.setList(newList)
        diffResult.dispatchUpdatesTo(this)
    }

}

private class CityDiffCallback(
    val oldList: List<CityModel>,
    val newList: List<CityModel>,
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