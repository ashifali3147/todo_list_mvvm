package com.test.todolist.ui.add_edit_todo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.test.todolist.databinding.FragmentAddEditTodoBinding
import com.test.todolist.util.Constant
import com.test.todolist.util.UiEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddEditTodoFragment : Fragment() {
    private lateinit var binding: FragmentAddEditTodoBinding
    private val viewModel by activityViewModels<AddEditTodoViewModel>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddEditTodoBinding.inflate(LayoutInflater.from(context))

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.uiEvent.collect { events ->
                when(events){
                    is UiEvent.ShowSnackBar -> {showSnackBar(events.message, events.action)}
                    is UiEvent.PopBackStack -> { requireActivity().supportFragmentManager.popBackStack()}
                    is UiEvent.Navigate -> {sendNavigationRoutes(events.route)}
                }
            }
        }

        binding.edtTitle.doOnTextChanged { text, start, before, count ->
            viewModel.onEvent(AddEditTodoEvents.OnTitleChange(text.toString()))
        }
        binding.edtDescription.doOnTextChanged { text, start, before, count ->
            viewModel.onEvent(AddEditTodoEvents.OnDescriptionChange(text.toString()))
        }
        binding.fabDone.setOnClickListener { viewModel.onEvent(AddEditTodoEvents.OnSaveTodoClick) }

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
        snackBar.show()
    }
}