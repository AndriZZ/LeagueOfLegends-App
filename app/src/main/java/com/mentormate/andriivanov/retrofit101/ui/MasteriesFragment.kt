package com.mentormate.andriivanov.retrofit101.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.github.mikephil.charting.charts.PieChart
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mentormate.andriivanov.retrofit101.JsonPlaceHolderApi
import com.mentormate.andriivanov.retrofit101.R
import com.mentormate.andriivanov.retrofit101.db.LegendsViewModel
import com.mentormate.andriivanov.retrofit101.data.AllChamps
import com.mentormate.andriivanov.retrofit101.data.BestChampion
import com.mentormate.andriivanov.retrofit101.data.ChampionInfo
import com.mentormate.andriivanov.retrofit101.data.ChampionMasteries
import com.mentormate.andriivanov.retrofit101.data.Profile
import com.mentormate.andriivanov.retrofit101.recyclerview.RecyclerViewAdapterChamps
import com.squareup.picasso.Picasso

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MasteriesFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MasteriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MasteriesFragment : Fragment() {


    private var jsonPlaceHolderApi: JsonPlaceHolderApi? = null
    private var mLegendsViewModel: LegendsViewModel? = null
    private var bestChampStats: TextView? = null
    private var bestChampImage: ImageView? = null
    private var pieChart: PieChart? = null
    private var champRecyclerView: RecyclerView? = null
    private var bestChampion: BestChampion? = null
    private var profile: Profile? = null

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null


    private var mListener: OnFragmentInteractionListener? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_masteries, container, false)
        bestChampStats = rootView.findViewById(R.id.best_champ_stats)
        champRecyclerView = rootView.findViewById(R.id.recyclerview_id)
        bestChampImage = rootView.findViewById(R.id.best_champ_image)
      //  pieChart = rootView.findViewById<View>(R.id.pie_chart) as PieChart

        val gson = GsonBuilder().serializeNulls().create()
        val retrofit = Retrofit.Builder()
                .baseUrl("https://euw1.api.riotgames.com/lol/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)

        mLegendsViewModel = ViewModelProviders.of(this).get(LegendsViewModel::class.java)
        bestChampion = BestChampion.instance
        mLegendsViewModel!!.allChampMasteries.observe(this, Observer {
            // Update the cached copy of the words in the adapter.

            profile = Profile()
            profile!!.profileImage = String.format("https://raw.communitydragon.org/pbe/plugins/rcp-be-lol-game-data/global/default/v1/champion-splashes/%2d/%2d000.jpg",
                    bestChampion!!.getBestChampId(), bestChampion!!.getBestChampId())
            Picasso.get().load(profile!!.profileImage).into(bestChampImage)
        })
        mLegendsViewModel!!.allChamps.observe(this, Observer { allChamps ->
            val myAdapter = RecyclerViewAdapterChamps(this!!.context!!, allChamps!!)
            champRecyclerView!!.layoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
            champRecyclerView!!.adapter = myAdapter
        })
        mLegendsViewModel!!.allChampInfo.observe(this, Observer { championInfos ->
            // Update the cached copy of the words in the adapter.
            var content = ""
            for (championInfo in championInfos!!) {
                if (bestChampion!!.getBestChampId() == championInfo.id) {
                    content = content + "ID: " + championInfo.id + "\n"
                    content = content + "Name: " + championInfo.name + "\n"
                    content = content + "Alias: " + championInfo.alias + "\n"
                    content = content + "Title: " + championInfo.title + "\n"
                    content = content + "Short Bio: " + championInfo.shortBio + "\n\n"
                }
            }


            bestChampStats!!.text = content
        })

        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
        Toast.makeText(activity, "IN MASTERIES", Toast.LENGTH_SHORT).show()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
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
         * @return A new instance of fragment MasteriesFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): MasteriesFragment {

            val fragment = MasteriesFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
