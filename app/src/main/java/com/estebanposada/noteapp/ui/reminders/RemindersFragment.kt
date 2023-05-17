package com.estebanposada.noteapp.ui.reminders

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.estebanposada.noteapp.R
import com.estebanposada.noteapp.databinding.FragmentRemindersBinding
import com.estebanposada.noteapp.ui.NoteAdapter
import com.estebanposada.noteapp.ui.model.Type
import com.estebanposada.noteapp.ui.note.NoteActivity
import com.estebanposada.noteapp.ui.note.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RemindersFragment : Fragment() {

    private var _binding: FragmentRemindersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NoteViewModel by viewModels()
    private val adapter by lazy { NoteAdapter(Type.REMINDER) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRemindersBinding.inflate(inflater, container, false).apply {
            rvReminders.apply {
                adapter = this@RemindersFragment.adapter
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
        }
        return binding.root
    }

    override fun onResume() {
        viewModel.getReminders()
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.reminderData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        adapter.onEditClicked = { uid ->
            startActivity(
                Intent(
                    requireContext(),
                    NoteActivity::class.java
                ).apply {
                    putExtra(NoteActivity.ID, uid)
                })
        }
        adapter.onDelete = { uid ->
            viewModel.delete(uid)
            viewModel.getReminders()
            Toast.makeText(requireContext(), getString(R.string.deleted), Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}