package com.mentormate.andriivanov.retrofit101

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.squareup.picasso.Picasso

class ChampDialog : AppCompatDialogFragment() {

    private var champImage: ImageView? = null
    private var champName: TextView? = null
    private var champDescription: TextView? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)

        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.champ_dialog, null)

        builder.setView(view)

        val bundle = this.arguments
        champImage = view.findViewById(R.id.champ_thumbnail)
        champName = view.findViewById(R.id.champ_name)
        champDescription = view.findViewById(R.id.champ_description)
        val img = bundle!!.getString("image")
        champName!!.text = bundle.getString("name")
        Picasso.get().load(img).into(champImage)
        champDescription!!.text = bundle.getString("description")
        return builder.create()
    }


}
