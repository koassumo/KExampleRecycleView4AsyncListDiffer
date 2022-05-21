package com.example.kexamplerecycleview4asynclistdiffer.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kexamplerecycleview4asynclistdiffer.R
import com.example.kexamplerecycleview4asynclistdiffer.model.entity.Note


// ---1--- Дописываем новый класс, который будет организовывать сравнение

//class MyDiffUtilCallback(
//    private val oldList: List<Note>,
//    private val newList: List<Note>,
//) : DiffUtil.Callback() {
//
//    override fun getOldListSize(): Int = oldList.size
//
//    override fun getNewListSize(): Int = newList.size
//
//    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        val oldItem = oldList[oldItemPosition]
//        val newItem = newList[oldItemPosition]
//        return oldItem.mTitle == newItem.mTitle
//    }
//
//    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        val oldItem = oldList[oldItemPosition]
//        val newItem = newList[oldItemPosition]
//        return oldItem.mTitle == newItem.mTitle
//    }
//}


class NotesRvAdapter : RecyclerView.Adapter<NotesRvAdapter.ViewHolder>() {

//    var notes: List<Note> = listOf()
//        set(newValue) {
//            val diffCallBack = MyDiffUtilCallback (field, newValue)
//            val diffResult = DiffUtil.calculateDiff(diffCallBack)
//            field = newValue
//            diffResult.dispatchUpdatesTo(this@NotesRvAdapter)
//        }


//================================================================================================
    // Т.е. по факту убрали свой class MyDiffUtilCallback и поручаем сравнение списка на входе
    // классу AsyncListDiffer
    private val myDiffUtilCallBack = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
            oldItem.mTitle == newItem.mTitle

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, myDiffUtilCallBack)

    var notes: List<Note>
        get() = differ.currentList
        set(value) = differ.submitList(value)
//================================================================================================



    // 0. Определяем CLASS ViewHolder
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note) {
            itemView.findViewById<TextView>(R.id.tv_title).text = note.mTitle
            itemView.findViewById<TextView>(R.id.tv_text).text = note.avatarUrl
            itemView.findViewById<ImageView>(R.id.iv_avatar).load(R.drawable.ic_launcher_foreground)
        }
    }

// Далее переопределяем ТРИ обязательных метода:

    // 1. Создаем каждый конкретный новый экземпляр viewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false))


    // 2. Наполняем (связываем) viewHolder с данными (подставляем в шаблон конкретные значения Note)
    override fun onBindViewHolder(holder: NotesRvAdapter.ViewHolder, position: Int) =
        holder.bind(notes[position])

    // 3. Recycler должен знать сколько у него элементов. Данный метод не вызывается из кода
    override fun getItemCount(): Int = notes.size


    fun updateNote(newNotes: List<Note>) {
        this.notes = newNotes
    }

}