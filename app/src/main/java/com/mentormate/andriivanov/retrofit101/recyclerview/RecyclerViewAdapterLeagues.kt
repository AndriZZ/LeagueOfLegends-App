package com.mentormate.andriivanov.retrofit101.recyclerview

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.mentormate.andriivanov.retrofit101.R
import com.mentormate.andriivanov.retrofit101.data.League


/**
 * Created by Aws on 28/01/2018.
 */

class RecyclerViewAdapterLeagues(private val mContext: Context, private val mData: List<League>) : RecyclerView.Adapter<RecyclerViewAdapterLeagues.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view: View
        val mInflater = LayoutInflater.from(mContext)
        view = mInflater.inflate(R.layout.cardview_rank, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.leagueName.text = mData[position].leagueName
        holder.leagueRank.text = mData[position].rank + " " + mData[position].tier
        holder.queueType.text = mData[position].queueType
        holder.winsLosses.text = "W: " + mData[position].wins + " L: " + mData[position].losses

    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal val leagueName: TextView
        internal val queueType: TextView
        internal val leagueRank: TextView
        internal val winsLosses: TextView
        internal var cardView: CardView

        init {

            winsLosses = itemView.findViewById(R.id.wins_losses)
            leagueName = itemView.findViewById(R.id.league_name)
            queueType = itemView.findViewById(R.id.league_queue)
            leagueRank = itemView.findViewById(R.id.league_rank)
            cardView = itemView.findViewById<View>(R.id.cardview_id_rank) as CardView


        }
    }


}
