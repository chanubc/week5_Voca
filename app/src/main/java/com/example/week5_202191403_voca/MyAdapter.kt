package com.example.week5_202191403_voca

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.week5_202191403_voca.databinding.RowBinding


class MyAdapter(val items: ArrayList<MyData>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun OnItemClick(data: MyData)
    }

    fun moveItem(oldPos: Int, newPos: Int) {
        val item = items[oldPos]
        items.removeAt(oldPos)
        items.add(newPos, item)
        notifyItemMoved(oldPos, newPos)
    }

    fun removeItem(pos: Int) {
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }

    var itemClickListener: OnItemClickListener? = null //초기값 null값

    inner class ViewHolder(val binding: RowBinding) : RecyclerView.ViewHolder(binding.root) {
  /*    val textView = itemView.findViewById<TextView>(R.id.textView)
        val textView2 = itemView.findViewById<TextView>(R.id.textView2)*/

        init {
            binding.textView.setOnClickListener {
                itemClickListener?.OnItemClick(items[adapterPosition]) //?는 null일 수 도 있다고 알려주는 역할
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowBinding.inflate(LayoutInflater.from(parent.context),  parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var isClicked: Boolean = true
        holder.binding.textView.text = items[position].word
        holder.binding.textView2.text = items[position].meaning
        holder.binding.textView2.visibility = View.GONE

        holder.binding.textView.setOnClickListener {
            if (isClicked) {
                holder.binding.textView2.visibility = View.VISIBLE
                isClicked = false
            } else {
                holder.binding.textView2.visibility = View.GONE
                isClicked = true
            }
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }


}
