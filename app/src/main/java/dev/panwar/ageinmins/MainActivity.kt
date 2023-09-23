package dev.panwar.ageinmins

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvDateSelected : TextView?=null
    private var tvAgeInMinutes : TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnDatePicker : Button =findViewById(R.id.BtnDatePicker)//variable btnDatePicker of type Button

        tvDateSelected=findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes=findViewById(R.id.tvAgeInMinutes)

        btnDatePicker.setOnClickListener {
//            Toast.makeText(this, "btnDatePicker Clicked",Toast.LENGTH_LONG).show()
            clickDatePicker()

        }
    }
    private fun clickDatePicker(){
        val myCalendar=Calendar.getInstance()
        val year=myCalendar.get(Calendar.YEAR)
        val month=myCalendar.get(Calendar.MONTH)
        val day=myCalendar.get(Calendar.DAY_OF_MONTH)
        //we are making DatePickerDialog as variable as we want to changes it's some properties like max date
        val dpd=DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{_,Selectedyear,Selectedmonth,SelectedDayOfMonth ->
                //we can also replace just above line by just {view,year,month,DayOfMonth ->

                Toast.makeText(this, "Selected year was $Selectedyear, Selected month was ${Selectedmonth+1}, Selected day Of Month was $SelectedDayOfMonth",Toast.LENGTH_LONG).show()

                val SelectedDate="$SelectedDayOfMonth/${Selectedmonth+1}/$Selectedyear"
                tvDateSelected?.text=SelectedDate
                val sdf=SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH) //date formatter
                val theDate=sdf.parse(SelectedDate) //take selected date and convert to sdf format
                theDate?.let {//if the Date is Not empty then //just an optimization so that there are no crashes

                    val selectedDateInMinutes=theDate.time/60000 //.time because it gives time in milliseconds from 1 Jan,1970 00:00 so converting it into minutes
                    val currentDate=sdf.parse(sdf.format(System.currentTimeMillis())) //gives time in milliseconds from 1 Jan,1970 00:00

                    currentDate?.let {//if the Date is Not empty then //just an optimization so that there are no crashes
                        val currentDateInMinutes=currentDate.time/60000 //to convert in minutes
                        val diffrenceInMinutes=currentDateInMinutes-selectedDateInMinutes

                        tvAgeInMinutes?.text=diffrenceInMinutes.toString()
                    }

                }

            },
            year,month,day

        )
        dpd.datePicker.maxDate=System.currentTimeMillis()-8400000 //we want the selected date to be max Yesterday....8400000 milli seconds in a day
        dpd.show()

    }
}

