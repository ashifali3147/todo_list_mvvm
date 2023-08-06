package com.test.todolist.ui.add_edit_todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.test.todolist.databinding.FragmentAddEditTodoBinding
import com.test.todolist.util.UiEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddEditTodoFragment : Fragment() {
    private lateinit var binding: FragmentAddEditTodoBinding
    private val viewModel by activityViewModels<AddEditTodoViewModel>()
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddEditTodoBinding.inflate(LayoutInflater.from(context))

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.uiEvent.collect { events ->
                when(events){
                    is UiEvent.ShowSnackBar -> {showSnackBar(events.message, events.action)}
                    is UiEvent.PopBackStack -> { navController.popBackStack()}
                    else -> Unit
                }
            }
        }

//        binding.edtTitle.setText(viewModel.title)
//        binding.edtDescription.setText(viewModel.description)

        binding.edtTitle.doOnTextChanged { text, start, before, count ->
            viewModel.onEvent(AddEditTodoEvents.OnTitleChange(text.toString()))
        }
        binding.edtDescription.doOnTextChanged { text, start, before, count ->
            viewModel.onEvent(AddEditTodoEvents.OnDescriptionChange(text.toString()))
        }
        binding.fabDone.setOnClickListener { viewModel.onEvent(AddEditTodoEvents.OnSaveTodoClick) }

        return binding.root
    }

    private fun showSnackBar(message: String, action: String?){
        val snackBar = Snackbar
            .make(binding.root, message, Snackbar.LENGTH_SHORT)
        snackBar.show()
    }
}