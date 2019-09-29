package com.leonov_dev.currencyconverter.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class RatesGetModel(
    val rates: Rates?,
    val base: String?,
    val date: Date?
)

data class Rates(
    @SerializedName("CAD") val cad: Double,
    @SerializedName("HKD") val hkd: Double,
    @SerializedName("ISK") val isk: Double,
    @SerializedName("PHP") val php: Double,
    @SerializedName("DKK") val dkk: Double,
    @SerializedName("HUF") val huf: Double,
    @SerializedName("CZK") val czk: Double,
    @SerializedName("GBP") val gbp: Double,
    @SerializedName("RON") val ron: Double,
    @SerializedName("SEK") val sek: Double,
    @SerializedName("IDR") val idr: Double,
    @SerializedName("INR") val inr: Double,
    @SerializedName("BRL") val brl: Double,
    @SerializedName("RUB") val rub: Double,
    @SerializedName("HRK") val hrk: Double,
    @SerializedName("JPY") val jpy: Double,
    @SerializedName("THB") val thb: Double,
    @SerializedName("CHF") val chf: Double,
    @SerializedName("EUR") val eur: Double,
    @SerializedName("MYR") val myr: Double,
    @SerializedName("BGN") val bgn: Double,
    @SerializedName("TRY") val turkish: Double,
    @SerializedName("CNY") val cny: Double,
    @SerializedName("NOK") val nok: Double,
    @SerializedName("NZD") val nzd: Double,
    @SerializedName("ZAR") val zar: Double,
    @SerializedName("USD") val usd: Double,
    @SerializedName("MXN") val mxn: Double,
    @SerializedName("SGD") val sgd: Double,
    @SerializedName("AUD") val aud: Double,
    @SerializedName("ILS") val ils: Double,
    @SerializedName("KRW") val krw: Double
)
