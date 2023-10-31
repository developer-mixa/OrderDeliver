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

class CityAdapter(private val cityListener: SelectCityListener) : RecyclerView.Adapter<CityAdapter.CityHolder>() {

    private val suggests =  ArrayList<CityModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.city_item, parent, false)
        return CityHolder(view)
    }

    override fun getItemCount(): Int = suggests.size

    override fun onBindViewHolder(holder: CityHolder, position: Int) = holder.bind(suggests[position], cityListener)

    class CityHolder(view: View) : ViewHolder(view){
        private val binding: CityItemBinding = CityItemBinding.bind(view)

        fun bind(cityModel: CityModel, selectCityListener: SelectCityListener) = with(binding){
            textTitle.text = cityModel.title
            textSubTitle.text = cityModel.subTitle
            cardCity.setOnClickListener {
                selectCityListener.onTap(cityModel)
            }
        }

    }

    fun updateList(newList: List<CityModel>){
        val diffCallback = CityDiffCallback(suggests, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        suggests.setList(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    interface SelectCityListener {
        fun onTap(cityModel: CityModel)
    }

}

private class CityDiffCallback(
    val oldList: List<CityModel>,
    val newList: List<CityModel>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].uri == newList[newItemPosition].uri
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}