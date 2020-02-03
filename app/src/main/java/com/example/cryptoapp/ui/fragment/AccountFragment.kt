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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.cryptoapp.BaseApplication
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.FragmentAccountBinding
import com.example.cryptoapp.viewmodels.AccountViewModel
import javax.inject.Inject

class AccountFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var accountViewModel: AccountViewModel

    private lateinit var fragmentAccountBinding: FragmentAccountBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity!!.application as BaseApplication).getSharedComponent().inject(this)

        fragmentAccountBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_account, container, false)

        return fragmentAccountBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiKey = sharedPreferences.getString("API_KEY","000000")?:""

        accountViewModel = ViewModelProviders.of(
            this,
            viewModelFactory
        ).get(AccountViewModel::class.java)

        accountViewModel.errorDisplay().observe(this, Observer {
            error ->
            fragmentAccountBinding.errorTxt.text = error
        })

        accountViewModel.isLoading().observe(this, Observer {
            isLoading ->
            if (isLoading){
                fragmentAccountBinding.progresBar.visibility = View.VISIBLE
            }else{
                fragmentAccountBinding.progresBar.visibility = View.INVISIBLE
            }
        })


        accountViewModel.getWithCoinRepoData(apiKey,"100","39MadnihMjyvmQqXFL6x1K7jh38HxjkTRe")
            .observe(this, Observer {
                data ->
                Log.e("Account", data.status)
            })
    }


}
