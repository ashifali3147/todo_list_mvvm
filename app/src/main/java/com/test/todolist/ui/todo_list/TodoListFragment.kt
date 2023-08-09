package com.test.todolist.ui.todo_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavArgument
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.test.todolist.R
import com.test.todolist.databinding.FragmentTodoListBinding
import com.test.todolist.ui.todo_list.adapter.TodoAdapter
import com.test.todolist.util.UiEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
@AndroidEntryPoint
class TodoListFragment : Fragment() {
    private lateinit var binding: FragmentTodoListBinding
    private val viewModel by activityViewModels<TodoListViewModel>()
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTodoListBinding.inflate(LayoutInflater.from(context))
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.uiEvent.collect() { event ->
                when (event) {
                    is UiEvent.ShowSnackBar -> {
                        showSnackBar(event.message, event.action)
                    }
                    is UiEvent.Navigate -> {
                        navigate(event.route, event.todoId)
                    }
                    else -> Unit
                }
            }
        }

        binding.fabAdd.setOnClickListener { viewModel.onEvent(TodoListEvents.OnAddTodoClick) }

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.todo.collect { todoList ->
                binding.rvTodoList.layoutManager = LinearLayoutManager(requireContext())
                binding.rvTodoList.adapter = TodoAdapter(requireContext(), todoList, viewModel)
            }
        }



        return binding.root
    }

    private fun navigate(route: String, todoId: Int?) {
        val argument = Bundle()
        val id: Int = todoId ?: -1
        argument.putInt("todoId", id)
        navController.navigate(R.id.addEditTodoFragment, argument)
    }

    private fun showSnackBar(message: String, action: String?) {
        val snackBar = Snackbar
            .make(binding.root, message, Snackbar.LENGTH_SHORT)
            .setAction(action) {
                viewModel.onEvent(TodoListEvents.OnUndoDeleteClick)
            }

        snackBar.show()
    }
}