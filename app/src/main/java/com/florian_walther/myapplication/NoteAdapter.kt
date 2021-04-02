package com.florian_walther.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.florian_walther.myapplication.databinding.ItemNoteBinding

class NoteAdapter(): RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemNoteBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.apply {
                tvTitle.text = note.title
                tvDescription.text = note.description
                tvPriority.text = note.priority.toString()

                root.setOnClickListener {
                    val position = adapterPosition
                    val note = notes[position]
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(note)
                    }
                }
            }
        }
    }

    lateinit var notes: List<Note>
    lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNoteBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }

    override fun getItemCount() = notes.size

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}