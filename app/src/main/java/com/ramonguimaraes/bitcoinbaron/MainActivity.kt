package com.ramonguimaraes.bitcoinbaron

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener{

    var anterior: Button? = null
    var selected: String = "btc"
    var actualPrice: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CalcTask().execute()

        btnSetStyle(btc)
        anterior = btc

        btc.setOnClickListener(this)
        bch.setOnClickListener(this)
        ltc.setOnClickListener(this)
        eth.setOnClickListener(this)
        xrp.setOnClickListener(this)

        menu.setOnClickListener {
            val popupMenu = PopupMenu(this, it)
            popupMenu.setOnMenuItemClickListener {
                menuItem -> when(menuItem.itemId) {
                    R.id.about_menu_item -> {
                        Toast.makeText(this, "About", Toast.LENGTH_SHORT).show()
                        true
                    }

                    R.id.github_menu_item -> {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ramonguimaraes"))
                        startActivity(intent)
                        true
                    }

                    R.id.github_menu_refresh -> {
                        CalcTask(selected).execute()
                        true
                    }

                    else -> false
                }
            }

            popupMenu.inflate(R.menu.option_menu)
            popupMenu.show()
        }

        button_calc.setOnClickListener {

        }
    }

    inner class CalcTask(var coinName: String = "btc") : AsyncTask<Void, Void, Double?>() {

        override fun onPreExecute() {
            super.onPreExecute()
            progressIndicator(true)

        }

        override fun doInBackground(vararg p0: Void?): Double? {
            return APIRequester.getLastValue(coinName)

        }

        override fun onPostExecute(result: Double?) {
            super.onPostExecute(result)
            if(result != null){
                actual_price.text = "R$".plus(String.format("%.2f", result))
                progressIndicator(false)
                actualPrice = result
            }else{
                //showError()
            }
        }
    }

    fun calc(investment: Double) {
        val coin = Coin()
        if(actualPrice != null ) {
            val res = coin.calcular(investment, actualPrice!!)
            //showResult(res, investment)
        }
    }

    override fun onClick(p0: View) {

        when (p0) {
            btc -> {
                CalcTask("btc").execute()
                selected = "btc"
                btnSetStyle(btc)
                anterior = btc
            }

            ltc -> {
                CalcTask("ltc").execute()
                selected = "ltc"
                btnSetStyle(ltc)
                anterior = ltc
            }

            xrp -> {
                CalcTask("xrp").execute()
                selected = "xrp"
                btnSetStyle(xrp)
                anterior = xrp
            }

            eth -> {
                CalcTask("eth").execute()
                selected = "eth"
                btnSetStyle(eth)
                anterior = eth
            }

            bch -> {
                CalcTask("bch").execute()
                selected = "bch"
                btnSetStyle(bch)
                anterior = bch
            }
        }

    }

    fun progressIndicator(visible: Boolean) {
        if(visible) {
            progress.visibility = View.VISIBLE
            actual_price.visibility = View.GONE
            help_description.visibility = View.GONE
            total_title.visibility = View.GONE
        }else{
            progress.visibility = View.GONE
            actual_price.visibility = View.VISIBLE
            help_description.visibility = View.VISIBLE
            total_title.visibility = View.VISIBLE
        }
    }

    fun btnSetStyle(btn: Button) {
        anterior?.setTextColor(Color.GRAY)
        btn.setTextColor(Color.DKGRAY)
    }
}