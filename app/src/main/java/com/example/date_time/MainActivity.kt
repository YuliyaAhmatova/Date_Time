package com.example.date_time

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class MainActivity : AppCompatActivity() {

    private val GALARY_REQUEST = 1
    private var photoUri: Uri? = null
    lateinit var person:Person

    private lateinit var inputFirstNameET:EditText
    private lateinit var inputSecondNameET:EditText
    private lateinit var inputDayET:EditText
    private lateinit var inputMonthET:EditText
    private lateinit var inputYearET:EditText
    private lateinit var inputImageIV:ImageView
    private lateinit var saveBTN:Button

    @SuppressLint("UnsafeIntentLaunch")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()

        inputImageIV.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALARY_REQUEST)
        }

        saveBTN.setOnClickListener {
            createPerson()
            intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("person", person)
            startActivity(intent)
        }
    }

    private fun init() {
        inputFirstNameET = findViewById(R.id.inputFirstNameET)
        inputSecondNameET = findViewById(R.id.inputSecondNameET)
        inputDayET = findViewById(R.id.inputDayET)
        inputMonthET = findViewById(R.id.inputMonthET)
        inputYearET = findViewById(R.id.inputYearET)
        inputImageIV = findViewById(R.id.inputImageIV)
        saveBTN = findViewById(R.id.saveBTN)
    }

    private fun createPerson() {
        val name = inputFirstNameET.text.toString()
        val secondName = inputSecondNameET.text.toString()
        val day = inputDayET.text.toString().toInt()
        val month = inputMonthET.text.toString().toInt()
        val year = inputYearET.text.toString().toInt()
        val image = photoUri.toString()
        person = Person(name, secondName, day, month, year, image)
        clearEditFields()
        photoUri = null
    }

    private fun clearEditFields() {
        inputFirstNameET.text.clear()
        inputSecondNameET.text.clear()
        inputDayET.text.clear()
        inputMonthET.text.clear()
        inputYearET.text.clear()
        inputImageIV.setImageResource(R.drawable.ic_photo)
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        inputImageIV = findViewById(R.id.inputImageIV)
        when (requestCode) {
            GALARY_REQUEST -> if (resultCode === RESULT_OK) {
                photoUri = data?.data
                inputImageIV.setImageURI(photoUri)
            }
        }
    }
}