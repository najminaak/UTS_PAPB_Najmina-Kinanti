package com.example.pertemuan6

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker.OnDateChangedListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pertemuan6.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding){
//            Get Array
            val monthList = resources.getStringArray(R.array.month)

//            Initiate
            var selectedTime ="${timePicker.hour}:${timePicker.minute}"
            val _tempCalendar : Calendar = Calendar.getInstance()
            _tempCalendar.timeInMillis = System.currentTimeMillis()
            var selectedDate = "${_tempCalendar.get(Calendar.DAY_OF_MONTH)} ${monthList[_tempCalendar.get(Calendar.MONTH)]} ${_tempCalendar.get(Calendar.YEAR)}"

//            Kehadiran Dropdown=======================================
            val kehadiranList = listOf("Hadir Tepat Waktu", "Terlambat", "Izin")
            val adapterKehadiran = ArrayAdapter<String>(
                this@MainActivity,
                android.R.layout.simple_spinner_item,
                kehadiranList
            )
            kehadiranSpinner.adapter = adapterKehadiran

//            Selected Kehadiran
            kehadiranSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if (kehadiranList[position] == "Terlambat" || kehadiranList[position] == "Izin") {
                            keteranganEdittext.visibility = View.VISIBLE
                        } else {
                            keteranganEdittext.visibility = View.GONE
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }

            submitButton.setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "Presensi berhasil $selectedDate jam $selectedTime",
                    Toast.LENGTH_SHORT
                ).show()
            }

            timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
                selectedTime = "${String.format("%02d", hourOfDay)}:${String.format("%02d", minute)}"
            }

            datepicker.init(
                _tempCalendar.get(Calendar.YEAR),
                _tempCalendar.get(Calendar.MONTH),
                _tempCalendar.get(Calendar.DAY_OF_MONTH)
            ) { _, year, monthOfYear, dayOfMonth ->
                selectedDate = "$dayOfMonth ${monthList[monthOfYear]} $year"
            }
        }
    }
}
