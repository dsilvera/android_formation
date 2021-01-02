package com.dsilvera.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_operation.view.*

class OperationAdapter : RecyclerView.Adapter<OperationAdapter.OperationViewHolder>() {
    private val operations = arrayListOf<Operation>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperationViewHolder {
        return OperationViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun onBindViewHolder(holder: OperationViewHolder, position: Int) {
        holder.bind(operations[position])
    }

    override fun getItemCount() = operations.size


    fun add(operation: Operation) {
        operations.add(operation)
        notifyItemInserted(itemCount)
    }

    class OperationViewHolder(inflater: LayoutInflater, viewGroup: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_operation, viewGroup, false)) {
        fun bind(operation: Operation) {
            itemView.operationTv.text = operation.getOperationTemplate()
            itemView.operationResultTv.text = operation.result().toString()
        }
    }
}