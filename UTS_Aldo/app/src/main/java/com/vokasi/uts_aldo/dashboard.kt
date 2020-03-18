package com.vokasi.uts_aldo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.vokasi.uts_aldo.input
import kotlinx.android.synthetic.main.activity_dashboard.*
import org.json.JSONObject

class dashboard : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val context=this

        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager= LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val users=ArrayList<User>()

        // ini fungsi untuk keluar/ LOGOUT
        logout.setOnClickListener{
            val sharedPreferences=getSharedPreferences("CEKLOGIN", Context.MODE_PRIVATE)
            val editor=sharedPreferences.edit()

            editor.putString("STATUS","0")
            editor.apply()

            startActivity(Intent(this@dashboard, MainActivity::class.java))
            finish()
        }

        // ini fungsi untuk pindah ke bagian input
        input.setOnClickListener {
            val intent= Intent(context,com.vokasi.uts_aldo.input::class.java)
            startActivity(intent)
            finish()
        }

        // ini fungsi untuk nampilin data dalam bentuk recycelr view
        AndroidNetworking.get("http://192.168.43.68/uts/penduduk_json.php")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.e("_kotlinResponse", response.toString())

                    val jsonArray = response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        Log.e("_kotlinTitle", jsonObject.optString("id_penduduk"))

                        // txt1.setText(jsonObject.optString("shubuh"))
                        var isi1=jsonObject.optString("id_penduduk").toString()
                        var isi2=jsonObject.optString("nama_penduduk").toString()
                        var isi3=jsonObject.optString("ttl_penduduk").toString()
                        var isi4=jsonObject.optString("hp_penduduk").toString()
                        var isi5=jsonObject.optString("alamat_penduduk").toString()


                        users.add(User("$isi1", "$isi2", "$isi3", "$isi4", "$isi5"))


                    }

                    val adapter=CustomAdapter(users)
                    recyclerView.adapter=adapter


                }

                override fun onError(anError: ANError) {
                    Log.i("_err", anError.toString())
                }
            })


    }
}