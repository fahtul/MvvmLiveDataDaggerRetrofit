package com.example.cryptoapp.model

data class Balance(
    val `data`: Data?,
    val status: String?
)

data class Data(
    val error_message : String?,
    val available_balance: String?,
    val network: String?,
    val pending_received_balance: String?
)