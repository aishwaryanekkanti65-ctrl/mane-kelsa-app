package com.example.manekelsa

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddWorkerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_worker)

        val name = findViewById<EditText>(R.id.name)
        val skill = findViewById<EditText>(R.id.skill)
        val wage = findViewById<EditText>(R.id.wage)
        val phone = findViewById<EditText>(R.id.phone)
        val availabilitySwitch =
            findViewById<Switch>(R.id.availabilitySwitch)

        val saveBtn = findViewById<Button>(R.id.saveBtn)

        saveBtn.setOnClickListener {

            val worker = Worker(
                name.text.toString(),
                skill.text.toString(),
                wage.text.toString(),
                phone.text.toString(),
                0f,
                availabilitySwitch.isChecked
            )

            DataHolder.workerList.add(worker)

            Toast.makeText(this, "Worker Added", Toast.LENGTH_SHORT).show()

            finish()
        }
    }
}