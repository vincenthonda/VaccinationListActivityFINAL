package com.example.vaccinationlistactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vaccinationlistactivity.databinding.ActivityVaccinationListBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VaccinationListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVaccinationListBinding
    val TAG = "VaccinationListActivity"
    lateinit var adapter: VaccinationAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVaccinationListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var vaccineList = mutableListOf<VaccinationInfo>()
        //vaccineList.add(
        //                Vaccination(
        //                    "Fake 1", sortedMapOf<String, Int>(
        //                        Pair("1/23/22", 100),
        //                        Pair("1/24/22", 105),
        //            Pair("1/25/22", 110)
        //        )
        //    )
        //)
        //vaccineList.add(
        //    Vaccination(
        //        "Fake 2", sortedMapOf<String, Int>(
        //            Pair("1/23/22", 500000),
        //            Pair("1/24/22", 600000),
        //            Pair("1/25/22", 700000),
        //            Pair("1/20/22", 600000),
        //        )
        //    )
        //)

        val vaccineApi = RetrofitHelper.getInstance().create(Covid19Service::class.java)
        val vaccineCall =vaccineApi.getVaccinations(10)


        vaccineCall.enqueue(object: Callback<List<VaccinationInfo>> {
            override fun onResponse(
                call: Call<List<VaccinationInfo>>,
                response: Response<List<VaccinationInfo>>
            ) {
                Log.d(TAG, "onResponse: ${response.body()}")
                vaccineList = (response.body() ?: listOf<VaccinationInfo>()).toMutableList()
                adapter = VaccinationAdapter(vaccineList)
                binding.recyclerViewVaccinationList.adapter = adapter
                binding.recyclerViewVaccinationList.layoutManager = LinearLayoutManager(this@VaccinationListActivity)
            }

            override fun onFailure(call: Call<List<VaccinationInfo>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })


        //val worldWideCasesCall = vaccineApi.getWorldWideCases()
//
        //worldWideCasesCall.enqueue(object :Callback<WorldWideCases>{
        //    override fun onResponse(
        //        call: Call<WorldWideCases>,
        //        response:Response<WorldWideCases>
        //    ) {
        //        Log.d(TAG, "onResponse: ${response.body()}")
        //    }
//
        //    override fun onFailure(call: Call<WorldWideCases>, t: Throwable) {
        //        Log.d(TAG, "onFailure: ${t.message}")
        //    }
        //})


        val inputStream = resources.openRawResource(R.raw.vaccination)
        val jsonString = inputStream.bufferedReader().use {
            it.readText()
        }
        val gson = Gson()
        val type = object : TypeToken<List<VaccinationInfo>>() {}.type
        val vaccinations = gson.fromJson<List<VaccinationInfo>>(jsonString, type)
        Log.d(TAG, "onCreate: \n$vaccinations")

        // Create our adapter and fill the recycler view
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.vaccinesort, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.sortby_name -> {
                adapter.dataSet = adapter.dataSet.sortedBy {
                    it.country.compareTo("test")
                }
                adapter.notifyDataSetChanged()
                true
            }
            R.id.sortby_lastTen -> {
                adapter.dataSet = adapter.dataSet.sortedBy {
                    (it.timeline.get(it.timeline.lastKey()?:0L))?.minus(it.timeline.get(it.timeline.firstKey())?: 0L)
                }
                adapter.notifyDataSetChanged()
                true
            }
            R.id.sortby_total -> {
                adapter.dataSet = adapter.dataSet.sortedByDescending {
                    it.timeline.get(it.timeline.lastKey()?: 0L)
                }
                adapter.notifyDataSetChanged()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}