package com.estebanposada.noteapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.estebanposada.noteapp.databinding.NoteItemBinding
import com.estebanposada.noteapp.ui.model.Note
import com.estebanposada.noteapp.ui.model.Type

class NoteAdapter(val type: Type) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(DIFF_CALLBACK) {

    var onEditClicked: ((String) -> Unit)? = null
    var onDelete: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder =
        NoteViewHolder(NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class NoteViewHolder(private val binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            itemView.setOnLongClickListener {
                onDelete?.invoke(note.uid)
                true
            }
            binding.apply {
                textViewNote.text = note.title
                checkBoxReminder.isChecked = note.isReminder
                buttonEdit.setOnClickListener { onEditClicked?.invoke(note.uid) }
                textViewDate.text = note.date
                if (type == Type.NOTE) {
                    textViewDate.visibility = View.GONE
                }
            }
        }
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
                oldItem.uid == newItem.uid

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
                oldItem.uid == newItem.uid && oldItem.title == newItem.title
        }
    }
}