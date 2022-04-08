package com.example.vaccinationlistactivity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class VaccinationInfo(
    val country: String,
    val timeline: SortedMap<String, Long>
    ): Parcelable, Comparable<VaccinationInfo> {
    override fun compareTo(other: VaccinationInfo): Int {
        return this.country.compareTo(other.country)
    }
}