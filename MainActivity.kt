package com.example.manekelsa
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var listView: ListView
    lateinit var addBtn: Button
    lateinit var searchBar: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        addBtn = findViewById(R.id.addWorkerBtn)
        searchBar = findViewById(R.id.searchBar)

        addBtn.setOnClickListener {
            startActivity(Intent(this, AddWorkerActivity::class.java))
        }

        listView.setOnItemClickListener { _, _, position, _ ->

            val worker = DataHolder.workerList[position]

            val options = arrayOf(
                "Call Worker",
                "Rate Worker",
                "Toggle Availability",
                "Delete Worker"
            )

            AlertDialog.Builder(this)
                .setTitle(worker.name)
                .setItems(options) { _, which ->

                    when(which) {

                        0 -> {

                            val intent = Intent(Intent.ACTION_DIAL)
                            intent.data = Uri.parse("tel:${worker.phone}")
                            startActivity(intent)
                        }

                        1 -> {

                            val ratingBar = RatingBar(this)
                            ratingBar.numStars = 5
                            ratingBar.stepSize = 1f

                            AlertDialog.Builder(this)
                                .setTitle("Rate Worker")
                                .setView(ratingBar)

                                .setPositiveButton("Submit") { _, _ ->

                                    worker.rating = ratingBar.rating
                                    showWorkers(DataHolder.workerList)
                                }

                                .show()
                        }

                        2 -> {

                            worker.available = !worker.available

                            Toast.makeText(
                                this,
                                "Availability Updated",
                                Toast.LENGTH_SHORT
                            ).show()

                            showWorkers(DataHolder.workerList)
                        }

                        3 -> {

                            DataHolder.workerList.removeAt(position)

                            Toast.makeText(
                                this,
                                "Worker Deleted",
                                Toast.LENGTH_SHORT
                            ).show()

                            showWorkers(DataHolder.workerList)
                        }
                    }
                }
                .show()
        }

        searchBar.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterWorkers(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onResume() {
        super.onResume()

        val availableWorkers = DataHolder.workerList.filter {
            true
        }

        showWorkers(availableWorkers)
    }

    private fun filterWorkers(query: String) {

        val filteredList = DataHolder.workerList.filter {

            it.name.contains(query, true) ||
                    it.skill.contains(query, true)
        }

        showWorkers(filteredList)
    }

    private fun showWorkers(workers: List<Worker>) {

        val displayList = ArrayList<String>()

        for(worker in workers) {

            val status = if(worker.available)
                "🟢 Available"
            else
                "🔴 Not Available"

            val text =
                "⭐ Rating: ${worker.rating}\n" +
                        "👤 ${worker.name}\n" +
                        "🛠 Skill: ${worker.skill}\n" +
                        "💰 Wage: ₹${worker.wage}\n" +
                        status

            displayList.add(text)
        }

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            displayList
        )

        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->

            val worker = workers[position]

            val options = arrayOf(
                "Call Worker",
                "Rate Worker",
                "Toggle Availability",
                "Delete Worker"
            )

            AlertDialog.Builder(this)
                .setTitle(worker.name)
                .setItems(options) { _, which ->

                    when(which) {

                        0 -> {

                            val intent = Intent(Intent.ACTION_DIAL)
                            intent.data = Uri.parse("tel:${worker.phone}")
                            startActivity(intent)
                        }

                        1 -> {

                            val ratingBar = RatingBar(this)

                            ratingBar.numStars = 5
                            ratingBar.stepSize = 1f

                            AlertDialog.Builder(this)
                                .setTitle("Rate Worker")
                                .setView(ratingBar)

                                .setPositiveButton("Submit") { _, _ ->

                                    worker.rating = ratingBar.rating

                                    showWorkers(DataHolder.workerList)
                                }

                                .show()
                        }

                        2 -> {

                            worker.available = !worker.available

                            showWorkers(DataHolder.workerList)
                        }

                        3 -> {

                            DataHolder.workerList.remove(worker)

                            Toast.makeText(
                                this,
                                "Worker Deleted",
                                Toast.LENGTH_SHORT
                            ).show()

                            showWorkers(DataHolder.workerList)
                        }
                    }
                }
                .show()
        }
    }
}