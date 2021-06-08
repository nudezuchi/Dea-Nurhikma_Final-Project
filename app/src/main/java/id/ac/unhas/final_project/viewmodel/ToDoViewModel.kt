package id.ac.unhas.final_project.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.ac.unhas.final_project.database.ToDo
import id.ac.unhas.final_project.database.ToDoRepository

class ToDoViewModel (application: Application): AndroidViewModel(application){
    private var toDoListRepo =
        ToDoRepository(application)
    private var lists: LiveData<List<ToDo>>? = toDoListRepo.getLists()

    fun getLists(): LiveData<List<ToDo>>?{
        return lists
    }

    fun insertList(list: ToDo){
        toDoListRepo.insertList(list)
    }

    fun deleteList(list: ToDo){
        toDoListRepo.deleteList(list)
    }

    fun updateList(list: ToDo){
        toDoListRepo.updateList(list)
    }

    fun searchResult(title: String): LiveData<List<ToDo>>?{
        return toDoListRepo.searchResult(title)
    }

    fun sortByDueDateDescending(): LiveData<List<ToDo>>?{
        return toDoListRepo.sortByDueDateDescending()
    }

    fun sortByCreatedDateAscending(): LiveData<List<ToDo>>?{
        return toDoListRepo.sortByCreatedDateAscending()
    }

    fun sortByCreatedDateDescending(): LiveData<List<ToDo>>?{
        return toDoListRepo.sortByCreatedDateDescending()
    }
}