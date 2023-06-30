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
import com.example.dfs.databinding.ProductListSingleViewBinding
import kotlin.collections.ArrayList


class PurchaseAdapter(
    var myContext: Context,
    var purchaseList: List<Result>,
    var onItemClicked: OnItemClicked,
): RecyclerView.Adapter<PurchaseAdapter.ViewHolder>() {

    var context: Context = myContext
    var purchaseFilterList: ArrayList<Result> = ArrayList()

    init {
        purchaseFilterList = purchaseList as ArrayList<Result>
    }

    interface OnItemClicked {
        fun addToCart(result: Result, toString: String)
        fun deleteItem(result: Result)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ProductListSingleViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(myContext), R.layout.product_list_single_view, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(myContext, purchaseFilterList, position,onItemClicked)
    }

    override fun getItemCount(): Int {
        return purchaseFilterList.size
    }

    class ViewHolder(var binding: ProductListSingleViewBinding) : RecyclerView.ViewHolder(binding.root) {

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
                purchaseList[position].internalCount = purchaseList[position].cartCount
            }
            binding.addBtn.setOnClickListener {
                display(binding.itemQuanEt.text.toString().toInt() + 1,myContext)
                purchaseList[position].internalCount = binding.itemQuanEt.text.toString().toInt() + 1

            }

            binding.deleteBtn.setOnClickListener {

                onItemClicked.deleteItem(result = purchaseList[position])
            }

            binding.removeBtn.setOnClickListener {

                if (binding.itemQuanEt.text.toString() == "0"){
                    return@setOnClickListener
                }else{
                    display(binding.itemQuanEt.text.toString().toInt() - 1,myContext)
                    purchaseList[position].internalCount = binding.itemQuanEt.text.toString().toInt() -1

                }

            }
            binding.addToCart.setOnClickListener {

                onItemClicked.addToCart(result = purchaseList[position],binding.itemQuanEt.text.toString())
            }

        }

        @RequiresApi(Build.VERSION_CODES.M)
        private fun display(number: Int, myContext: Context) {
            if (number == 0){
                binding.removeBtn.visibility = View.VISIBLE
                binding.deleteBtn.visibility = View.GONE
                binding.removeBtn.background = myContext!!.getDrawable(R.drawable.ic_minus_new)
                binding.addToCart.isEnabled = false
                binding.addToCart.setBackgroundColor(myContext.getColor(R.color.light_grey))
                binding.addToCart.setTextColor(myContext.getColor(R.color.dark_grey))

            }else if (number == 1){
                binding.removeBtn.visibility = View.INVISIBLE
                binding.deleteBtn.visibility = View.VISIBLE
                binding.addToCart.isEnabled = true
                binding.addToCart.setBackgroundColor(myContext.getColor(R.color.theme_color))
                binding.addToCart.setTextColor(myContext.getColor(R.color.white))
            }
            else{
                binding.removeBtn.visibility = View.VISIBLE
                binding.deleteBtn.visibility = View.GONE
                binding.removeBtn.background = myContext!!.getDrawable(R.drawable.ic_minus_red)
                binding.addToCart.isEnabled = true
                binding.addToCart.setBackgroundColor(myContext.getColor(R.color.theme_color))
                binding.addToCart.setTextColor(myContext.getColor(R.color.white))


            }

            binding.itemQuanEt.text = "$number"

        }

    }




}
