package com.example.vaccinationlistactivity

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class VaccinationAdapter(var dataSet: List<VaccinationInfo>) :
    RecyclerView.Adapter<VaccinationAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val countryName: TextView
        val timeline: TextView

        val layout : ConstraintLayout

        init {
            countryName= view.findViewById(R.id.textView_vaccinationItem_countryName)
            timeline = view.findViewById(R.id.textView_vaccinationItem_timeline)
            layout = view.findViewById(R.id.layout_vaccinationItem)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_vaccination, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val vaccination = dataSet[position]
        viewHolder.countryName.text = vaccination.country
        viewHolder.timeline.text = vaccination.timeline.toString()

        viewHolder.layout.setOnClickListener {
            //Toast.makeText(it.context, "Hi, you clicked on ${vaccination.name}", Toast.LENGTH_SHORT).show()
            val context = viewHolder.layout.context
            val vaccinationDetailIntent = Intent(context, vaccinationdetail::class.java).apply{
                putExtra(vaccinationdetail.EXTRA_COUNTRY, vaccination)
            }
            context.startActivity(vaccinationDetailIntent)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}