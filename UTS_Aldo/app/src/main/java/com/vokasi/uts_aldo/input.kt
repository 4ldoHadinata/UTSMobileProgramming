package com.vokasi.uts_aldo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.vokasi.uts_aldo.input
import kotlinx.android.synthetic.main.activity_input.*
import org.json.JSONArray

class input : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)
        val context=this

        home.setOnClickListener {
            val intent= Intent(context,MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        input.setOnClickListener {
            var nama = nama.text.toString()
            var ttl = ttl.text.toString()
            var hp = hp.text.toString()
            var alamat = alamat.text.toString()

            postkerserver(nama, ttl, hp, alamat)

            val intent= Intent(context,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun postkerserver(nama:String,ttl:String, hp:String, alamat:String){
        AndroidNetworking.post("http://192.168.43.68/uts/insert_data.php")
            .addBodyParameter("nama", nama)
            .addBodyParameter("ttl", ttl)
            .addBodyParameter("hp", hp)
            .addBodyParameter("alamat", alamat)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray?) {
                    Log.i("Teguh", "MANTAP")
                }

                override fun onError(anError: ANError?) {
                    Log.i("Teguh", "BOCOR")
                }
            })
    }

}
