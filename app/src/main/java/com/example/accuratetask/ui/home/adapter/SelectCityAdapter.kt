package com.example.accuratetask.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.example.accuratetask.databinding.ItemCityBinding
import com.example.accuratetask.model.entity.City

class SelectCityAdapter : RecyclerView.Adapter<SelectCityAdapter.ViewHolder>() {

    private val mList: MutableList<City> = mutableListOf()

    companion object {
        var sListener: OnItemClickListener? = null
    }

    fun setCityList(city: List<City>) {
        mList.clear()
        if (city.isNotEmpty()) {
            mList.addAll(city)
            notifyItemRangeInserted(0, mList.size)
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        sListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city : City = mList[position]
        holder.populateUI(city)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    @Nullable
    fun getItem(position: Int): City? {
        return if (itemCount > 0 && position >= 0) mList[position] else null
    }

    class ViewHolder(private var mBinding : ItemCityBinding) : RecyclerView.ViewHolder(mBinding.root) {

        init {
            mBinding.rvListCity.setOnClickListener {
                if (sListener != null) sListener?.onItemClick(
                    it, bindingAdapterPosition
                )
            }
        }

        fun populateUI(city: City?) {
            mBinding.rvListCity.text = city?.name
        }
    }

    interface OnItemClickListener {

        fun onItemClick(view: View?, position: Int)
    }

}