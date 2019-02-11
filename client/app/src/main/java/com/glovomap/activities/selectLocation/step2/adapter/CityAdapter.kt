package com.glovomap.activities.selectLocation.step2.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.glovomap.R
import com.glovomap.sia.rest.request.City

class CityAdapter(private var dataSet: List<City>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {

        fun onItemClickInfo(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item, parent, false)
        return ViewHolderItem(view)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        var viewHolderItem = viewHolder as ViewHolderItem
        var entity: City = this.dataSet[position]
        //country
        viewHolderItem.row_item__textview_name.text = entity.name
        //city
        //viewHolderItem.row_item__textview_city.text = entity.description
    }

    override fun getItemCount(): Int {
        return dataSet.size

    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun getItem(position: Int): City {
        return this.dataSet[position]
    }

    inner class ViewHolderItem(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val row_item__textview_name: TextView
        val row_item__textview_city: TextView
        val row_item__container: View

        val view: View
            get() = itemView

        init {
            row_item__container = view.findViewById(R.id.row_item__container)
            row_item__textview_name = view.findViewById(R.id.row_item__textview_name)
            row_item__textview_city = view.findViewById(R.id.row_item__textview_city)
            row_item__container.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            if (itemClickListener != null)
                itemClickListener?.onItemClickInfo(v, adapterPosition)
        }

    }
}
