package com.test.todolist.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.test.todolist.R
import com.test.todolist.databinding.ActivityMainBinding
import com.test.todolist.ui.add_edit_todo.AddEditTodoFragment
import com.test.todolist.ui.todo_list.TodoListFragment
import com.test.todolist.util.Constant
import com.test.todolist.util.Routes
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        setFragment(Routes.TODO_LIST)

        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                if (intent.action != null && intent.action == Constant.NAVIGATION_ACTION) {
                    // Handle the received data here
                    val data = intent.getStringExtra("routes_path")
                    setFragment(data)
                }
            }
        }
        val filter = IntentFilter(Constant.NAVIGATION_ACTION)
        registerReceiver(broadcastReceiver, filter)
    }

    private fun setFragment(routes: String?) {
        if (routes == Routes.ADD_EDIT_TODO){
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view, AddEditTodoFragment::class.java, null)
                .commit()
        }
        else if (routes == Routes.TODO_LIST){
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view, TodoListFragment::class.java, null)
                .commit()
        }
    }
}