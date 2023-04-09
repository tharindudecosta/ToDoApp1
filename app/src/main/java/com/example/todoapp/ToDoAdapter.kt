package com.example.todoapp

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R.layout.item_todo

class ToDoAdapter(private val toDos: MutableList<ToDo>) :RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {

    class ToDoViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        return ToDoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false
            )
        )
    }

    fun addTodo(todo: ToDo) {
        toDos.add(todo)
        notifyItemInserted(toDos.size - 1)
    }

    fun deleteDoneTodos() {
        toDos.removeAll { toDo ->
            toDo.isChecked
        }
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean) {
        if(isChecked) {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }


    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val currTodo = toDos[position]
        holder.itemView.apply {
            var textTitle:TextView = findViewById(R.id.tvTodoItem)
            textTitle.text = currTodo.title

            var checkBox:CheckBox = findViewById(R.id.cbDone)
            checkBox.isChecked = currTodo.isChecked

            toggleStrikeThrough(textTitle, checkBox.isChecked)
            checkBox.setOnCheckedChangeListener{_,isChecked->
                toggleStrikeThrough(textTitle,isChecked)
                currTodo.isChecked=!currTodo.isChecked
            }
        }
    }

    override fun getItemCount(): Int {
        return toDos.size
    }
}