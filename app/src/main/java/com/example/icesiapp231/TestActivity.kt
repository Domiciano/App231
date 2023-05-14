package com.example.icesiapp231

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        lifecycleScope.launch(Dispatchers.IO) {
            val query = Firebase.firestore
                .collection("cities")
                .orderBy("population")
                .whereLessThan("population",1000000)
                .limit(5)
                .get()
                .await()
            for (doc in query.documents) {
                val city = doc.toObject(City::class.java)
            }

        }

    }
}

data class City(
    var id: String = "",
    var country: String = "",
    var city: String = "",
    var population: Int = 0,
    var area: Double = 0.0,
    var sectors: ArrayList<String> = arrayListOf()
)