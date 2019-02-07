package com.mentormate.andriivanov.retrofit101.recyclerview

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.mentormate.andriivanov.retrofit101.R
import com.mentormate.andriivanov.retrofit101.data.Matches


class RecyclerViewAdapterMatches(private val mContext: Context, private val mData: List<Matches>) : RecyclerView.Adapter<RecyclerViewAdapterMatches.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view: View
        val mInflater = LayoutInflater.from(mContext)
        view = mInflater.inflate(R.layout.cardview_matches, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.championName.text = mData[position].platformId!!.toString()
        holder.matchRole.text = mData[position].role
        holder.matchLane.text = mData[position].lane
        holder.matchSeason.text = "Season: " + mData[position].season + "\n" + mData[position].gameId

    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal val championName: TextView
        internal val matchRole: TextView
        internal val matchLane: TextView
        internal val matchSeason: TextView
        internal var cardView: CardView

        init {

            championName = itemView.findViewById(R.id.champion_name)
            matchRole = itemView.findViewById(R.id.match_role)
            matchLane = itemView.findViewById(R.id.match_lane)
            matchSeason = itemView.findViewById(R.id.match_season)
            cardView = itemView.findViewById<View>(R.id.cardview_id_matches) as CardView


        }
    }


}
