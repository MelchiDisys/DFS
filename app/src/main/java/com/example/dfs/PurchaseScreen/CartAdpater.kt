package com.example.dfs.PurchaseScreen
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dfs.PurchaseScreen.model.Result
import com.example.dfs.R
import com.example.dfs.databinding.CartSingleViewBinding
import kotlin.collections.ArrayList


class CartAdpater(
    var myContext: Context,
    var purchaseList: List<com.example.dfs.PurchaseScreen.model.Result>,
    var onItemClicked: OnItemClicked,
): RecyclerView.Adapter<CartAdpater.ViewHolder>() {

    var context: Context = myContext
    var purchaseFilterList: ArrayList<com.example.dfs.PurchaseScreen.model.Result> = ArrayList()

    init {
        purchaseFilterList = purchaseList as ArrayList<Result>
    }

    interface OnItemClicked {
        fun deleteItem(result: Result)
        fun updateItem(result: Result, count: String)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: CartSingleViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(myContext), R.layout.cart_single_view, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(myContext, purchaseFilterList, position,onItemClicked)
    }

    override fun getItemCount(): Int {
        return purchaseFilterList.size
    }

    class ViewHolder(var binding: CartSingleViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            myContext: Context,
            purchaseList: ArrayList<com.example.dfs.PurchaseScreen.model.Result>,
            position: Int,
            onItemClicked: OnItemClicked,

            ) {

            binding.apply {
                itemName.text = myContext.getString(R.string.item_name).plus(" ").plus(purchaseList[position].itemName)
                descriptionTxt.text =myContext.getString(R.string.description).plus(" ").plus(purchaseList[position].description)
                amountTxt.text = myContext.getString(R.string.amount).plus(myContext.getString(R.string.Rs)).plus("").plus(" ").plus(purchaseList[position].amount)
                display(purchaseList[position].cartCount,myContext)

            }
            binding.addBtn.setOnClickListener {
                display(binding.itemQuanEt.text.toString().toInt() + 1,myContext)
            }

            binding.removeBtn.setOnClickListener {

                if (binding.itemQuanEt.text.toString() == "0"){
                    return@setOnClickListener
                }else{
                    display(binding.itemQuanEt.text.toString().toInt() - 1,myContext)
                } }

            binding.deleteItem.setOnClickListener {
                onItemClicked.deleteItem(result = purchaseList[position])
            }
            binding.deleteBtn.setOnClickListener {
                onItemClicked.deleteItem(result = purchaseList[position])
            }
            binding.updateBtn.setOnClickListener {
                onItemClicked.updateItem(result = purchaseList[position],binding.itemQuanEt.text.toString())
            }

        }

        @RequiresApi(Build.VERSION_CODES.M)
        private fun display(number: Int, myContext: Context) {
            if (number == 0){
                binding.removeBtn.visibility = View.VISIBLE
                binding.deleteBtn.visibility = View.GONE
                binding.removeBtn.background = myContext!!.getDrawable(R.drawable.ic_minus_new)
            }else if (number == 1) {
                binding.removeBtn.visibility = View.INVISIBLE
                binding.deleteBtn.visibility = View.VISIBLE
            }else{
                binding.removeBtn.visibility = View.VISIBLE
                binding.deleteBtn.visibility = View.GONE
                binding.removeBtn.background = myContext!!.getDrawable(R.drawable.ic_minus_red)
            }
            binding.itemQuanEt.text = "$number"
        }

    }




}
