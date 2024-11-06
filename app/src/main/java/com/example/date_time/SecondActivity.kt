package com.example.date_time

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit

class SecondActivity : AppCompatActivity() {

    private lateinit var toolbarSA:Toolbar
    private lateinit var firstNameTA:TextView
    private lateinit var secondNameTA:TextView
    private lateinit var ageTV:TextView
    private lateinit var imageIV: ImageView
    lateinit var person:Person

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        firstNameTA = findViewById(R.id.firstNameTV)
        secondNameTA = findViewById(R.id.secondNameTV)
        ageTV = findViewById(R.id.ageTV)
        imageIV = findViewById(R.id.imageIV)

        toolbarSA = findViewById(R.id.toolbarSA)
        setSupportActionBar(toolbarSA)
        title = ""

        person = intent.getSerializableExtra("person") as Person
        firstNameTA.text = person.name
        secondNameTA.text = person.secondName
        imageIV.setImageURI(Uri.parse(person.image))

        val day = person.day
        val month = person.month
        val year = person.year

        val birthDate = LocalDate.of(year,month,day)
        val currentDate = LocalDate.now()
        val age = Period.between(birthDate, currentDate).years
        var nextBirthday = birthDate.withYear(currentDate.year)
        if (nextBirthday.isBefore(currentDate) || nextBirthday.isEqual(currentDate)){
            nextBirthday = nextBirthday.plusYears(1)
        }
        val daysUnitBirthday = ChronoUnit.DAYS.between(currentDate, nextBirthday)
        ageTV.text = "Возраст $age \nДо дня рождения осталось дней: $daysUnitBirthday"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_sa, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.exitMenuSA -> finishAffinity()
        }
        return super.onOptionsItemSelected(item)
    }
}