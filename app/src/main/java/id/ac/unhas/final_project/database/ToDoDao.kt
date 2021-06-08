package id.ac.unhas.final_project.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todolist ORDER BY due_date ASC, due_hour ASC")
    fun getToDoList(): LiveData<List<ToDo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(list: ToDo)

    @Delete
    suspend fun deleteList(list: ToDo)

    @Update
    suspend fun updateList(list: ToDo)

    @Query("SELECT * FROM todolist WHERE title LIKE :title")
    fun searchResult(title: String): LiveData<List<ToDo>>

    @Query("SELECT * FROM todolist ORDER BY due_date DESC, due_hour DESC")
    fun sortByDueDateDescending(): LiveData<List<ToDo>>

    @Query("SELECT * FROM todolist ORDER BY created_date ASC")
    fun sortByCreatedDateAscending(): LiveData<List<ToDo>>

    @Query("SELECT * FROM todolist ORDER BY created_date DESC")
    fun sortByCreatedDateDescending(): LiveData<List<ToDo>>
}