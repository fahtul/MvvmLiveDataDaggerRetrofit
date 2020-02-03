package com.example.cryptoapp.repository

import com.example.cryptoapp.balance.BalanceService
import com.example.cryptoapp.model.Balance
import io.reactivex.Single
import javax.inject.Inject

class BalanceRepo @Inject constructor(private val balanceService: BalanceService){

    fun getBalanceRepo(apiKey:String): Single<Balance>{
        return balanceService.getBalance(apiKey)
    }

}