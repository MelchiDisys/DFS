package com.example.dfs.Service

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dfs.R
import com.example.dfs.Service.model.Result
import com.example.dfs.databinding.ServiceListSingleViewBinding

class ServiceAdapter(
    var myContext: Context,
    var serviceList: List<Result>,
    ): RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {

    var context: Context = myContext
    var serviceListDetail: List<Result> = ArrayList()

    init {
        serviceListDetail = serviceList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ServiceListSingleViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(myContext), R.layout.service_list_single_view, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(myContext, serviceListDetail as ArrayList<Result>, position)
    }

    override fun getItemCount(): Int {
        return serviceListDetail.size
    }

    class ViewHolder(var binding: ServiceListSingleViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            myContext: Context,
            serviceList: ArrayList<Result>,
            position: Int,
            ) {

                binding.apply {
                    textViewServiceNumber.text = serviceList[position].serialNo
                    textServiceTypeValue.text = serviceList[position].serviceTypeName
                    textServiceValue.text = serviceList[position].serviceName
                    textStatusValue.text = serviceList[position].status
                    textServiceProviderValue.text = serviceList[position].serviceProviderName
                }


              }



    }




}
