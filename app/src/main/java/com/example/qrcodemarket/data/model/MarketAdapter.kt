package com.example.qrcodemarket.data.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qrcodemarket.R
import kotlinx.android.synthetic.main.recycle_item.view.*

class MarketAdapter (val market:List<getMarket.Data>,val listener:OnAdapterListener)
    : RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {

    interface OnAdapterListener{
        fun onCLick(dataMarket:getMarket.Data)
    }

    class MarketViewHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
        return MarketViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycle_market_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        val dataMarket = market[position]

        holder.view.txtNameMarket.text = dataMarket.marketName
        holder.view.txtLocationEx.text= dataMarket.marketLocation

        holder.view.txtLocationEx.setOnClickListener {
            listener.onCLick(dataMarket)
        }

    }

    override fun getItemCount() = market.size
}