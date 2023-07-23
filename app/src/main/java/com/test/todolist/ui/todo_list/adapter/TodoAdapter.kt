package com.test.todolist.ui.todo_list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.RecyclerView
import com.test.todolist.data.entity.Todo
import com.test.todolist.databinding.CustomTodoViewBinding
import com.test.todolist.ui.todo_list.TodoListEvents
import com.test.todolist.ui.todo_list.TodoListViewModel

class TodoAdapter(
    private val context: Context,
    val todoList: MutableList<Todo>, val viewModel: TodoListViewModel
): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    class TodoViewHolder(val binding: CustomTodoViewBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    private lateinit var binding: CustomTodoViewBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        binding= CustomTodoViewBinding.inflate(LayoutInflater.from(context))
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todoList[position]
        binding.tvTitle.text = todo.title
        binding.tvDescription.text = todo.description
        binding.isDone.isChecked = todo.isDone
        holder.binding.imgDelete.setOnClickListener { viewModel.onEvent(TodoListEvents.DeleteTodo(todo))}
        holder.binding.isDone.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onEvent(TodoListEvents.OnDoneChange(todo, isChecked))
        }
        holder.binding.imgDelete.setOnClickListener { viewModel.onEvent(TodoListEvents.DeleteTodo(todo)) }
    }

    override fun getItemCount(): Int {
        return todoList.toList().size
    }

}