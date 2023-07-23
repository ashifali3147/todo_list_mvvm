package com.test.todolist.ui.todo_list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.test.todolist.databinding.FragmentTodoListBinding
import com.test.todolist.ui.todo_list.adapter.TodoAdapter
import com.test.todolist.util.Constant
import com.test.todolist.util.UiEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoListFragment : Fragment() {
    private lateinit var binding: FragmentTodoListBinding
    private val viewModel by activityViewModels<TodoListViewModel>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentTodoListBinding.inflate(LayoutInflater.from(context))
        CoroutineScope(Dispatchers.Main).launch{
            viewModel.uiEvent.collect(){ event ->
                when(event){
                    is UiEvent.ShowSnackBar -> {showSnackBar(event.message, event.action)}
                    is UiEvent.Navigate -> {sendNavigationRoutes(event.route)}
                    else -> Unit
                }
            }
        }

        binding.fabAdd.setOnClickListener { viewModel.onEvent(TodoListEvents.OnAddTodoClick) }

        CoroutineScope(Dispatchers.IO).launch {
            viewModel.todo.collect(){ todoList ->
                CoroutineScope(Dispatchers.Main).launch {
                    binding.rvTodoList.layoutManager = LinearLayoutManager(requireContext())
                    binding.rvTodoList.adapter = TodoAdapter(requireContext(), todoList, viewModel)
                }
            }
        }



        return binding.root
    }

    private fun sendNavigationRoutes(route: String) {
        val navigation = Intent(Constant.NAVIGATION_ACTION)
        navigation.putExtra("routes_path", route)
        requireContext().sendBroadcast(navigation)
    }

    private fun showSnackBar(message: String, action: String?){
        val snackBar = Snackbar
            .make(binding.root, message, Snackbar.LENGTH_SHORT)
            .setAction(action) {
                viewModel.onEvent(TodoListEvents.OnUndoDeleteClick)
            }

        snackBar.show()
    }
}