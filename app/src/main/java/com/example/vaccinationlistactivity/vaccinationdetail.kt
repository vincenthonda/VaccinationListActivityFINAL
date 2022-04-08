package com.example.vaccinationlistactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vaccinationlistactivity.databinding.ActivityVaccinationdetailBinding

class vaccinationdetail : AppCompatActivity() {

    private lateinit var binding: ActivityVaccinationdetailBinding

    companion object{
        val EXTRA_COUNTRY = "Country"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVaccinationdetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val countryInfo = intent.getParcelableExtra<VaccinationInfo>(EXTRA_COUNTRY)
        binding.textViewDetailCountryName.text = countryInfo?.country
        binding.textViewDetailTimeline.text =
            countryInfo?.timeline?.toList()?.joinToString {
                it.first + ":" + it.second + "\n"
            }?.replace(",", "")

        //val vaccination = intent.getParcelableExtra<VaccinationInfo>(EXTRA_COUNTRY)
        //binding.textViewDetailCountryName.text = vaccination?.country
        //val lastDay = vaccination?.timeline?.lastKey()
        //val lastDayNumber = vaccination?.timeline?.get(lastDay)
        //binding.textViewDetailTimeline.text = vaccination?.timeline.toString()
    }
}