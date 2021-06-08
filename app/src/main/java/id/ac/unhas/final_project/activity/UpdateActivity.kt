package id.ac.unhas.final_project.activity

import  .app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import id.ac.unhas.final_project.Converter
import id.ac.unhas.final_project.R
import id.ac.unhas.final_project.database.ToDo
import id.ac.unhas.final_project.viewmodel.ToDoViewModel
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.util.*


class UpdateActivity : AppCompatActivity() {
    private lateinit var editTextTitle: EditText
    private lateinit var editTextDate: EditText
    private lateinit var editTextTime: EditText
    private lateinit var editTextNote: EditText
    private lateinit var btnUpdate: Button
    private lateinit var btnCancel: Button
    private lateinit var chkBoxIsFinished: CheckBox

    private lateinit var toDoViewModel: ToDoViewModel
    private lateinit var toDo: ToDo
    private var calendar = Calendar.getInstance()

    companion object{
        const val EXTRA_TITLE_UPDATE = "TITLE"
        const val EXTRA_DATE_UPDATE = "date-month-year"
        const val EXTRA_TIME_UPDATE = "hour:minutes"
        const val EXTRA_NOTE_UPDATE = "NOTE"
        const val EXTRA_IS_FINISHED_UPDATE = "false"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        if(supportActionBar != null){
            supportActionBar?.title = getString(R.string.pebarui_tugas)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Back Button

        editTextTitle = findViewById(R.id.title_content)
        editTextDate = findViewById(R.id.due_date_content)
        editTextTime = findViewById(R.id.due_time_content)
        editTextNote = findViewById(R.id.desc_content)
        btnUpdate = findViewById(R.id.btn_update)
        btnCancel = findViewById(R.id.btn_cancel)
        chkBoxIsFinished = findViewById(R.id.checkbox_is_finished)
        toDoViewModel = ViewModelProvider(this).get(ToDoViewModel::class.java)

        getExtra()
        editTextDate.setOnClickListener{
            setDueDate()
        }
        editTextTime.setOnClickListener {
            setDueTime()
        }
        btnUpdate.setOnClickListener{
            updateList(toDo)
        }
        btnCancel.setOnClickListener{
            finish()
        }
    }

    // Back Button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    private fun getExtra(){
        toDo = intent.getParcelableExtra("EXTRA_LIST")!!
        editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE_UPDATE))
        editTextDate.setText(intent.getStringExtra(EXTRA_DATE_UPDATE))
        editTextTime.setText(intent.getStringExtra(EXTRA_TIME_UPDATE))
        editTextNote.setText(intent.getStringExtra(EXTRA_NOTE_UPDATE))
        chkBoxIsFinished.isChecked = intent.getBooleanExtra(EXTRA_IS_FINISHED_UPDATE, false)
    }

    private fun setDueDate(){
        val date = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        // Date picker dialog
        val dateListener = DatePickerDialog.OnDateSetListener{ view, year, month, date ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DATE, date)
            editTextDate.setText(SimpleDateFormat("EEE, MMM dd, yyyy").format(calendar.time))
        }

        DatePickerDialog(this, dateListener, year, month, date).show()
    }

    private fun setDueTime(){
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            editTextTime.setText(SimpleDateFormat("HH:mm").format(calendar.time))
        }

        TimePickerDialog(this, timeSetListener, hour, minute, true).show()
    }

    private fun updateList(toDo: ToDo){
        val current = ZonedDateTime.now()
        val updatedDate = Converter.dateToInt(current)

        val strDueDate = editTextDate.text.toString().trim()
        val dueDate = Converter.stringDateToInt(strDueDate)

        val strDueHour = editTextTime.text.toString().trim()
        val dueHour= Converter.stringTimeToInt(strDueHour)

        toDo.updatedDate = updatedDate
        toDo.title = editTextTitle.text.toString().trim()
        toDo.dueDate = dueDate
        toDo.dueHour = dueHour
        toDo.strDueDate = strDueDate
        toDo.strDueHour = strDueHour
        toDo.note = editTextNote.text.toString().trim()
        toDo.isFinished = chkBoxIsFinished.isChecked

        toDoViewModel.updateList(toDo)
        if(chkBoxIsFinished.isChecked){
            toDoViewModel.deleteList(toDo)
        }
        finish()
    }
}

