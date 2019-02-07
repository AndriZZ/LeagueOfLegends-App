package com.mentormate.andriivanov.retrofit101.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mentormate.andriivanov.retrofit101.JsonPlaceHolderApi
import com.mentormate.andriivanov.retrofit101.R
import com.mentormate.andriivanov.retrofit101.db.LegendsViewModel
import com.mentormate.andriivanov.retrofit101.data.League
import com.mentormate.andriivanov.retrofit101.data.Profile
import com.mentormate.andriivanov.retrofit101.recyclerview.RecyclerViewAdapterLeagues
import com.squareup.picasso.Picasso

import java.util.ArrayList

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ProfileFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var profileImage: ImageView? = null
    private var jsonPlaceHolderApi: JsonPlaceHolderApi? = null
    private var rankRecyclerView: RecyclerView? = null
    private var lstLeagues: MutableList<League>? = null
    private var profileName: TextView? = null
    private var mLegendsViewModel: LegendsViewModel? = null
    private val profile2 = Profile()
    internal lateinit var myAdapter: RecyclerViewAdapterLeagues
    private var mListener: OnFragmentInteractionListener? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        val rootView = inflater.inflate(R.layout.fragment_tabbed, container, false)


        profileName = rootView.findViewById(R.id.profile_name)
        profileImage = rootView.findViewById(R.id.profileImage)

        rankRecyclerView = rootView.findViewById<View>(R.id.recyclerview_id) as RecyclerView

        val gson = GsonBuilder().serializeNulls().create()
        val retrofit = Retrofit.Builder()
                .baseUrl("https://euw1.api.riotgames.com/lol/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)
        val intent = activity!!.intent

       // val iconId = intent.getIntExtra("summonerIconId", 1)
     //   profile.profileImage = String.format(
             //   "https://raw.communitydragon.org/pbe/plugins/rcp-be-lol-game-data/global/default/v1/profile-icons/%2d.jpg", iconId)

    //    Picasso.get().load(profile.profileImage).into(profileImage)
        profile2.name = intent.getStringExtra("summonerName")


       // profileName!!.text = profile.name

        mLegendsViewModel = ViewModelProviders.of(this).get(LegendsViewModel::class.java)

        lstLeagues = ArrayList()
        mLegendsViewModel!!.allLeagues.observe(this, Observer { leagues ->
            lstLeagues!!.clear()

            Log.e("###", leagues!!.toString())
            myAdapter = RecyclerViewAdapterLeagues(this!!.context!!, lstLeagues as ArrayList<League>)
            // Update the cached copy of the words in the adapter.
            for (league in leagues) {
                if (league.summonerName.equals(profile2.name!!, ignoreCase = true)) {
                    lstLeagues!!.add(league)


                }
            }

            if (!lstLeagues!!.isEmpty()) {


                rankRecyclerView!!.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.HORIZONTAL, false)
                myAdapter.notifyDataSetChanged()
                rankRecyclerView!!.adapter = myAdapter
            } else {

                // myAdapter.notifyDataSetChanged();

            }
        })


        mLegendsViewModel!!.allProfiles.observe(this, Observer { profiles ->

            Log.e("###", profiles!!.toString())
            // Update the cached copy of the words in the adapter.
            for (profile in profiles) {
                if (profile.name.equals(profile2.name!!, ignoreCase = true)) {
                    profileName!!.text = profile.name
                    var imgString = String.format(
                            "https://raw.communitydragon.org/pbe/plugins/rcp-be-lol-game-data/global/default/v1/profile-icons/%2d.jpg", profile.profileIconId)
                    Picasso.get().load(imgString).into(profileImage)


                }
            }

        })
        return rootView
    }

    override fun onResume() {

        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): ProfileFragment {
            val fragment = ProfileFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
