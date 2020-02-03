package com.example.cryptoapp.repository

import com.example.cryptoapp.balance.BalanceService
import com.example.cryptoapp.model.Balance
import io.reactivex.Single
import javax.inject.Inject

class WithDrawCoinRepository @Inject constructor(private val balanceService: BalanceService){

    fun getWithDrawCoinRepo(api_key : String, amount:String, to_address:String):
            Single<Balance>{

        return balanceService.withDrawCoin(api_key,amount,to_address)

    }

}