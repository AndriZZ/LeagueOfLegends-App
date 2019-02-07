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

import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mentormate.andriivanov.retrofit101.JsonPlaceHolderApi
import com.mentormate.andriivanov.retrofit101.R
import com.mentormate.andriivanov.retrofit101.db.LegendsRoomDatabase
import com.mentormate.andriivanov.retrofit101.db.LegendsViewModel
import com.mentormate.andriivanov.retrofit101.data.AllChamps
import com.mentormate.andriivanov.retrofit101.data.ChampionMasteries
import com.mentormate.andriivanov.retrofit101.data.Matches
import com.mentormate.andriivanov.retrofit101.recyclerview.RecyclerViewAdapterMatches

import java.util.ArrayList

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MatchesFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MatchesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MatchesFragment : Fragment() {


    private var jsonPlaceHolderApi: JsonPlaceHolderApi? = null

    private var matchRecyclerView: RecyclerView? = null
    private var pieChart: PieChart? = null
    private var mLegendsViewModel: LegendsViewModel? = null
    lateinit var champPoints: List<PieEntry>
    private var allCC: List<AllChamps>? = null
    private val legendsRoomDatabase: LegendsRoomDatabase? = null

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_matches, container, false)

        pieChart = rootView.findViewById(R.id.pie_chart)
        val gson = GsonBuilder().serializeNulls().create()
        val retrofit = Retrofit.Builder()
                .baseUrl("https://euw1.api.riotgames.com/lol/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)
        val lstMatches = ArrayList<Matches>()
        matchRecyclerView = rootView.findViewById(R.id.recyclerview_id) as RecyclerView
        mLegendsViewModel = ViewModelProviders.of(this).get(LegendsViewModel::class.java)
        mLegendsViewModel!!.allChampMasteries.observe(this, Observer { championMasteries ->
            // Update the cached copy of the words in the adapter.


            champPoints = ArrayList()
            for (championMastery in championMasteries!!) {

                //if(allCC.contains(championMastery.getChampionId())) {
                val a = championMastery.championId
                //  if (a < 90) {
                //     champPoints.add(new PieEntry(championMastery.getChampionPoints(), allCC.get(a).getName()));
                //   }
                //  }
            }
            val dataSet = PieDataSet(champPoints, "ChampDATA")
            dataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
            val data = PieData(dataSet)


            pieChart!!.data = data
            pieChart!!.animateY(1000)
            pieChart!!.invalidate()
        })

        mLegendsViewModel!!.allChamps.observe(this, Observer { allChamps -> allCC = allChamps })


        mLegendsViewModel!!.allMatches.observe(this, Observer { matches ->
            for (i in matches!!.indices) {

                lstMatches.add(matches[i])
                for (champ in allCC!!) {

                    if (champ.id == matches[i].champion) {
                        lstMatches[i].platformId = champ.name
                    }

                }

            }


            val myAdapter = RecyclerViewAdapterMatches(this!!.context!!, lstMatches)

            matchRecyclerView!!.layoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
            myAdapter.notifyDataSetChanged()
            matchRecyclerView!!.adapter = myAdapter
        })


        return rootView
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
         * @return A new instance of fragment MatchesFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): MatchesFragment {
            val fragment = MatchesFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
