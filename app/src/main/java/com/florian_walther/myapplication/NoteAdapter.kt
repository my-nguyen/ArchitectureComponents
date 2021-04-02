package com.florian_walther.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.florian_walther.myapplication.databinding.ItemNoteBinding

// class NoteAdapter(): RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
class NoteAdapter(): ListAdapter<Note, NoteAdapter.ViewHolder>(DiffCallback()) {
    inner class ViewHolder(private val binding: ItemNoteBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.apply {
                tvTitle.text = note.title
                tvDescription.text = note.description
                tvPriority.text = note.priority.toString()

                root.setOnClickListener {
                    val position = adapterPosition
                    // val note = notes[position]
                    val note = getItem(position)
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(note)
                    }
                }
            }
        }
    }

    class DiffCallback: DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Note, newItem: Note) =
            oldItem == newItem
    }

    // no need to maintain List<Note>
    // lateinit var notes: List<Note>
    lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNoteBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // val note = notes[position]
        val note = getItem(position)
        holder.bind(note)
    }

    // override fun getItemCount() = notes.size

    public override fun getItem(position: Int): Note {
        return super.getItem(position)
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}