package com.example.cryptoapp.ui.fragment


import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.cryptoapp.BaseApplication
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.FragmentHomeBinding
import com.example.cryptoapp.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var fragmentHomeBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        (activity!!.application as BaseApplication).getSharedComponent().inject(this)

        fragmentHomeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)


        return fragmentHomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiKey = sharedPreferences.getString("API_KEY","000000")?:""

        homeViewModel = ViewModelProviders.of(this,viewModelFactory).get(
            HomeViewModel::class.java)

        homeViewModel.isLoading().observe(this, Observer {isLoading ->
            if (isLoading){
                fragmentHomeBinding.progressBar.visibility = View.VISIBLE
            }else{
                fragmentHomeBinding.progressBar.visibility = View.GONE
            }

        })

        homeViewModel.errorDisplay().observe(this, Observer { error->
            Log.e("Home", error.toString())
        })

        homeViewModel.getBalanceMutableLiveData(apiKey).observe(
            this, Observer {data ->
                fragmentHomeBinding.balance = data
            }
        )

    }


}
