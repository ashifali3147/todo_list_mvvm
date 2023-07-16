package com.test.todolist.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.todolist.data.entity.Todo
import com.test.todolist.data.repository.TodoRepositoryImpl
import com.test.todolist.databinding.CustomTodoViewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

class TodoAdapter(private val context: Context): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    class TodoViewHolder(val binding: CustomTodoViewBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    private lateinit var binding: CustomTodoViewBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        binding= CustomTodoViewBinding.inflate(LayoutInflater.from(context))
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
//        holder.binding.imgDelete.setOnClickListener {repositoryImpl.deleteTodo(todoList[position])}
    }

    override fun getItemCount(): Int {
        TODO()
//        return todoList.toList().size
    }

}