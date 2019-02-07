package com.mentormate.andriivanov.retrofit101.recyclerview

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.mentormate.andriivanov.retrofit101.ChampDialog
import com.mentormate.andriivanov.retrofit101.R
import com.mentormate.andriivanov.retrofit101.data.AllChamps
import com.squareup.picasso.Picasso


/**
 * Created by Aws on 28/01/2018.
 */

class RecyclerViewAdapterChamps(private val mContext: Context, private val mData: List<AllChamps>) : RecyclerView.Adapter<RecyclerViewAdapterChamps.MyViewHolder>() {
    private var context: Context? = null
    private var bundle: Bundle? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view: View
        val mInflater = LayoutInflater.from(mContext)
        view = mInflater.inflate(R.layout.cardveiw_item_champs, parent, false)
        context = view.context
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.titleChamp.text = mData[position].name
        Picasso.get().load(String.format("https://raw.communitydragon.org/pbe/plugins/rcp-be-lol-game-data/global/default/v1/champion-splashes/%s/%s000.jpg",
                mData[position].id, mData[position].id)).into(holder.imgChamp)

        holder.cardView.setOnClickListener {
            bundle = Bundle()
            bundle!!.putString("name", mData[position].name)
            bundle!!.putString("description", mData[position].desc)
            bundle!!.putString("image", String.format("https://raw.communitydragon.org/pbe/plugins/rcp-be-lol-game-data/global/default/v1/champion-splashes/%s/%s000.jpg",
                    mData[position].id, mData[position].id))
            openDialog()
        }

    }

    fun openDialog() {
        val champDialog = ChampDialog()

        champDialog.arguments = bundle
        val manager = (context as FragmentActivity).supportFragmentManager
        champDialog.show(manager, "example dialog")
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal val titleChamp: TextView
        internal val imgChamp: ImageView
        internal var cardView: CardView

        init {

            titleChamp = itemView.findViewById(R.id.champ_title_id)
            imgChamp = itemView.findViewById(R.id.champ_img)

            cardView = itemView.findViewById<View>(R.id.cardview_id_champ) as CardView


        }
    }


}
