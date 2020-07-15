package com.ramonguimaraes.bitcoinbaron

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.TimeUnit


object APIRequester {

    fun hasConnection(ctx: Context): Boolean{

        val cm = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        return info != null && info.isConnected

    }

    fun getLastValue(coin: String): Double? {

        val url = "https://www.mercadobitcoin.net/api/${coin}/ticker/"

        val client = OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()

        val request = Request.Builder()
            .url(url)
            .build()

        val response = client.newCall(request).execute()

        val jsonString = response.body?.string()

        val json = JSONObject(jsonString)

        return readLast(json)
    }

    fun readLast(jsonObject: JSONObject): Double? {
        var price: Double? = null
        var ticker: JSONObject? = null
        try {
            ticker = jsonObject.getJSONObject("ticker")
            price = ticker.getDouble("last")

            Log.e("Preco da moeda: ", "${price}")

        } catch (e: JSONException) {

            Log.e("Erro:", "${e}")

        }

        return price
    }

}