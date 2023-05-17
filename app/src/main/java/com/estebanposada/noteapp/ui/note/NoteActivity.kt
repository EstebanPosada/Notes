package com.estebanposada.noteapp.ui.note

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.estebanposada.noteapp.R
import com.estebanposada.noteapp.databinding.ActivityNoteBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class NoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteBinding

    private val viewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val id = intent.getStringExtra(ID)
        id?.let { viewModel.getNote(it) }

        binding.apply {
            buttonSave.setOnClickListener {
                val newNote = editTextNote.text.toString()
                val date = textViewDate.text.toString()
                val isReminder = checkBoxReminder.isChecked
                if (newNote.isNotEmpty()) {
                    if (id != null) {
                        viewModel.editNote(id, newNote, isReminder)
                    } else viewModel.saveNote(newNote, isReminder, if (isReminder) date else "")
                    finish()
                } else
                    Toast.makeText(this@NoteActivity, getString(R.string.empty), Toast.LENGTH_LONG)
                        .show()
            }
            checkBoxReminder.setOnCheckedChangeListener { _, b ->
                textViewDate.visibility = if (b) View.VISIBLE else View.GONE
            }
            textViewDate.setOnClickListener {
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)
                DatePickerDialog(
                    this@NoteActivity,
                    { _, y, m, d -> binding.textViewDate.text = "$d/$m/$y" }, year, month, day
                ).show()
            }
        }
        viewModel.note.observe(this) {
            binding.apply {
                editTextNote.setText(it.title)
                checkBoxReminder.isChecked = it.isReminder
                textViewDate.text = it.date
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val ID = "id"
    }
}