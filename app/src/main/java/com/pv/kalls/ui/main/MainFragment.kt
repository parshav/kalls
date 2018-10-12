package com.pv.kalls.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pv.kalls.R
import com.pv.networking.Networking

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    val networking = Networking

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

/*        networking.roadster {
            it.fold(
                    {
                        Log.d("pv", "Error in Roadster $it")
                    },
                    {
                        Log.d("pv", "RoadsterModel Returned")
                    }
            )
        }

        networking.historyFor(1) {

            it.fold(
                    {
                        Log.d("pv", "Error in History $it")
                    },
                    {
                        Log.d("pv", "HistoryModel Returned boi")
                    }
            )
        }

        networking.latestLaunch {
            it.fold(
                    {
                        Log.d("pv", "Error in latest launch $it")
                    },
                    {
                        Log.d("pv", "LatestLaunchModel Returned boi")
                    }
            )
        }*/

        networking.dynamicHistory(7) {
            it.fold(
                    {
                        Log.d("pv", "Error in dynamic history $it")
                    },
                    {
                        Log.d("pv", "Dynamic history returned boi")
                    }
            )
        }

        networking.singleHistory {
            it.fold(
                    {
                        Log.d("pv", "Error in single history $it")
                    },
                    {
                        Log.d("pv", "Single history returned boi")
                    }
            )
        }
    }
}
