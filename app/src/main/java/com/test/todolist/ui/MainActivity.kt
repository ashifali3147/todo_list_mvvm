package com.test.todolist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.todolist.databinding.ActivityMainBinding
import com.test.todolist.ui.adapter.TodoAdapter
import com.test.todolist.ui.todo_list.TodoListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<TodoListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        viewModel.todo
        binding.rvTodoList.layoutManager = LinearLayoutManager(this)
//        GlobalScope.launch {  }
//        val adapter: TodoAdapter = TodoAdapter(this, viewModel.todo.toList(), viewModel)
//        viewModel.todo
    }
}