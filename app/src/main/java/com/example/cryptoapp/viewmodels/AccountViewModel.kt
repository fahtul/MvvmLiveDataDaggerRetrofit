package com.example.cryptoapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoapp.model.Balance
import com.example.cryptoapp.repository.WithDrawCoinRepository
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AccountViewModel @Inject constructor(private val withDrawCoinRepository: WithDrawCoinRepository) : ViewModel(){

    private val disposable =  CompositeDisposable()
    private val withDrawCoinRepositoryLiveData = MutableLiveData<Balance>()

    private val isLoading = MutableLiveData<Boolean>()
    private val errorDisplay = MutableLiveData<String>()

    fun getWithCoinRepoData(api_key: String,amount: String,to_address: String) : MutableLiveData<Balance>{
        loadData(api_key,amount,to_address)
        return withDrawCoinRepositoryLiveData
    }

    private fun loadData(api_key:String, amount:String, to_address:String){
        isLoading.value = true
        disposable.add(withDrawCoinRepository.getWithDrawCoinRepo(api_key,amount,to_address).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({data ->
                isLoading.value = false
                getWithCoinRepoData(api_key,amount,to_address).value = data
            },{error ->
                isLoading.value = false
                errorDisplay.value = error.toString()

            }))
    }

    fun isLoading(): LiveData<Boolean>{
        return isLoading
    }

    fun errorDisplay(): LiveData<String>{
        return errorDisplay
    }
}