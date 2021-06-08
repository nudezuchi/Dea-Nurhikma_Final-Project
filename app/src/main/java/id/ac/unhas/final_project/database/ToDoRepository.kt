package id.ac.unhas.final_project.database


import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ToDoRepository (application: Application){
    private val toDoDao: ToDoDao?
    private var lists: LiveData<List<ToDo>>? = null

    init {
        val db = AppDatabase.getInstance(
            application.applicationContext
        )
        toDoDao = db?.toDoListDao()
        lists = toDoDao?.getToDoList()
    }

    fun getLists(): LiveData<List<ToDo>>?{
        return lists
    }

    fun insertList(list: ToDo) = runBlocking {
        this.launch(Dispatchers.IO) {
            toDoDao?.insertList(list)
        }
    }

    fun deleteList(list: ToDo) = runBlocking{
        this.launch(Dispatchers.IO){
            toDoDao?.deleteList(list)
        }
    }

    fun updateList(list: ToDo) = runBlocking {
        this.launch(Dispatchers.IO) {
            toDoDao?.updateList(list)
        }
    }

    fun searchResult(title: String): LiveData<List<ToDo>>?{
        return toDoDao?.searchResult(title)
    }

    fun sortByDueDateDescending(): LiveData<List<ToDo>>?{
        return toDoDao?.sortByDueDateDescending()
    }

    fun sortByCreatedDateAscending(): LiveData<List<ToDo>>?{
        return toDoDao?.sortByCreatedDateAscending()
    }

    fun sortByCreatedDateDescending(): LiveData<List<ToDo>>?{
        return toDoDao?.sortByCreatedDateDescending()
    }
}