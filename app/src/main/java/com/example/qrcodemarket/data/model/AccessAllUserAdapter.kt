package com.example.qrcodemarket.data.model

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.qrcodemarket.R
import kotlinx.android.synthetic.main.fragment_account_setting.view.*
import kotlinx.android.synthetic.main.recycle_citizen.view.*
import kotlinx.android.synthetic.main.recycle_item.view.*
import kotlinx.android.synthetic.main.recycle_item.view.txtDateEx
import kotlinx.android.synthetic.main.recycle_item.view.txtInTime
import kotlinx.android.synthetic.main.recycle_item.view.txtOutTime
import java.util.*
import kotlin.collections.ArrayList

class AccessAllUserAdapter(var access:List<getAccessAllUser.Data>): RecyclerView.Adapter<AccessAllUserAdapter.AccessViewHolder>() ,
    Filterable {
    lateinit var dataSearch: List<getAccessAllUser.Data>
    class AccessViewHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccessViewHolder {
        return AccessViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycle_citizen,parent,false)
        )
    }

    override fun onBindViewHolder(holder: AccessViewHolder, position: Int) {
        val dataAccess = access[position]


        holder.view.txtCitizenName.text = dataAccess.fullName
        holder.view.txtMarketName.text = dataAccess.marketName
        holder.view.txtInTime.text = dataAccess.timeIn
        holder.view.txtOutTime.text = dataAccess.timeOut
        holder.view.txtDateEx.text = dataAccess.date

    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults? {
                val queryString = charSequence.toString()

                val filterResults = FilterResults()
                filterResults.values =
                    if (queryString.isEmpty()) {
                        access
                    } else {
                        Log.i("Statistical","Data"+access)
                        access.filter {
                            it.fullName.contains(queryString, ignoreCase = true) || it.fullName.contains(charSequence)
                        }
                    }
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                dataSearch = filterResults.values as List<getAccessAllUser.Data>
                notifyDataSetChanged()
            }
        }
    }
    override fun getItemCount() = access.size
}