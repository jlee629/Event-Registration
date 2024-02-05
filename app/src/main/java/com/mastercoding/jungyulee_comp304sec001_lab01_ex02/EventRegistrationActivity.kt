package com.mastercoding.jungyulee_comp304sec001_lab01_ex02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class EventRegistrationActivity : AppCompatActivity() {
    private lateinit var textViewEventRegistrationApp: TextView
    private lateinit var editTextEventName: EditText
    private lateinit var editTextEventDate: EditText
    private lateinit var editTextEventOrganizer: EditText
    private lateinit var spinnerCity: Spinner
    private lateinit var checkBoxCorporate: CheckBox
    private lateinit var checkBoxNotForProfit: CheckBox
    private lateinit var checkBoxEducational: CheckBox
    private lateinit var textViewEventFormat: TextView
    private lateinit var radioGroupEventFormat: RadioGroup
    private lateinit var radioButtonInPerson: RadioButton
    private lateinit var radioButtonOnline: RadioButton
    private lateinit var buttonRegister: Button
    private lateinit var buttonReset: Button
    private lateinit var textViewOutput: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_registration)

        textViewEventRegistrationApp = findViewById(R.id.textViewEventRegistrationApp)
        editTextEventName = findViewById(R.id.editTextEventName)
        editTextEventDate = findViewById(R.id.editTextEventDate)
        editTextEventOrganizer = findViewById(R.id.editTextEventOrganizer)
        spinnerCity = findViewById(R.id.spinnerCity)
        checkBoxCorporate = findViewById(R.id.checkBoxCorporate)
        checkBoxNotForProfit = findViewById(R.id.checkBoxNotForProfit)
        checkBoxEducational = findViewById(R.id.checkBoxEducational)
        textViewEventFormat = findViewById(R.id.textView)
        radioGroupEventFormat = findViewById(R.id.rdbGpEventFormat)
        radioButtonInPerson = findViewById(R.id.radioButtonInPerson)
        radioButtonOnline = findViewById(R.id.radioButtonOnline)
        buttonRegister = findViewById(R.id.buttonRegister)
        buttonReset = findViewById(R.id.buttonReset)
        textViewOutput = findViewById(R.id.textViewOutput)

        buttonRegister.setOnClickListener {

            if (!validateEventDate()) {
                return@setOnClickListener
            }

            if (!validateCheckBoxes()) {
                Toast.makeText(this, "Select at least one type of organization.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!validateRadioButtons()) {
                Toast.makeText(this, "Select an event format.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            displayOutput()
        }

        buttonReset.setOnClickListener {
            resetForm()
        }
    }

    private fun validateEventDate(): Boolean {
        var dateString = editTextEventDate.text.toString()

        // format validation
        if (!dateString.matches(Regex("\\d{2}/\\d{2}/\\d{4}"))) {
            editTextEventDate.error = "Date must be in MM/DD/YYYY format"
            return false
        }

        // check valid calendar date
        var dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
        dateFormat.isLenient = false
        try {
            dateFormat.parse(dateString)
        } catch (e: ParseException) {
            editTextEventDate.error = "Invalid calendar date"
            return false
        }

        return true
    }

    private fun validateCheckBoxes(): Boolean {
        return checkBoxCorporate.isChecked || checkBoxNotForProfit.isChecked || checkBoxEducational.isChecked
    }

    private fun validateRadioButtons(): Boolean {
        return radioGroupEventFormat.checkedRadioButtonId != -1
    }

    private fun displayOutput() {
        var eventName = editTextEventName.text.toString()
        var eventDate = editTextEventDate.text.toString()
        var eventOrganizer = editTextEventOrganizer.text.toString()
        var city = spinnerCity.selectedItem.toString()

        //
        var organizationTypes = mutableListOf<String>()
        if (checkBoxCorporate.isChecked) organizationTypes.add("Corporate")
        if (checkBoxNotForProfit.isChecked) organizationTypes.add("Not For Profit")
        if (checkBoxEducational.isChecked) organizationTypes.add("Educational")
        var organizationType = if (organizationTypes.isNotEmpty()) organizationTypes.joinToString(", ") else "Not specified"

        var eventFormat = when (radioGroupEventFormat.checkedRadioButtonId) {
            R.id.radioButtonInPerson -> "In Person"
            R.id.radioButtonOnline -> "Online"
            else -> "Not specified"
        }

        var outputText = "Event Name: $eventName\n" +
                "Event Date: $eventDate\n" +
                "Event Organizer: $eventOrganizer\n" +
                "City: $city\n" +
                "Organization Type: $organizationType\n" +
                "Event Format: $eventFormat"

        textViewOutput.text = outputText
        Toast.makeText(this, outputText, Toast.LENGTH_SHORT).show()

    }

    private fun resetForm() {
        editTextEventName.text.clear()
        editTextEventDate.text.clear()
        editTextEventOrganizer.text.clear()

        spinnerCity.setSelection(0)

        checkBoxCorporate.isChecked = false
        checkBoxNotForProfit.isChecked = false
        checkBoxEducational.isChecked = false

        radioGroupEventFormat.clearCheck()

        textViewOutput.text = ""
    }


}