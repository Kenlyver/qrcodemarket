package com.example.qrcodemarket.data.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qrcodemarket.R
import kotlinx.android.synthetic.main.recycle_item.view.*

class AccessAdapter(val access:List<getDataAccess.Data>):RecyclerView.Adapter<AccessAdapter.AccessViewHolder>() {

    class AccessViewHolder(val view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccessViewHolder {
        return AccessViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycle_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: AccessViewHolder, position: Int) {
        val dataAccess = access[position]

        holder.view.txtNameMarket.text = dataAccess.marketName
        holder.view.txtInTime.text = dataAccess.timeIn
        holder.view.txtOutTime.text = dataAccess.timeOut
        holder.view.txtDateEx.text = dataAccess.date
        holder.view.txtLocationEx.text= dataAccess.location


    }

    override fun getItemCount() = access.size
}