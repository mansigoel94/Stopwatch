package com.example.mansigoel.stopwatch

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mansigoel.stopwatch.LapAdapter.MyViewHolder
import kotlinx.android.synthetic.main.item_lap.*
import kotlinx.android.synthetic.main.item_lap.view.*

class LapAdapter() : RecyclerView.Adapter<MyViewHolder>() {
    var lapList = ArrayList<Lap>();

    fun updateList(lapList: ArrayList<Lap>?) {
        this.lapList.clear();
        if (lapList != null) {
            //add list in reverse order as followed by ios
            this.lapList.addAll(lapList)
            this.lapList.reverse();
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val rootView = LayoutInflater.from(parent.context).
                inflate(R.layout.item_lap, parent, false);
        return MyViewHolder(rootView);
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (lapList != null) {
            holder.bindItems(lapList!![position]);
        }
    }

    override fun getItemCount(): Int {
        if (lapList != null) {
            return lapList!!.size
            Log.d("Mansi", "Size of list is " + lapList!!.size);
        };
        return 0;
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(lap: Lap) {
            itemView.tv_lap_number.text = lap.number.toString();
            itemView.tv_lap_time.text = lap.time;
        }
    }
}