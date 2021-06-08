package id.ac.unhas.final_project.adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.unhas.final_project.R
import id.ac.unhas.final_project.database.ToDo
import kotlinx.android.synthetic.main.item_list.view.*


class ToDoAdapter(private val context: Context?, private val listener: (ToDo, Int) -> Unit):
    RecyclerView.Adapter<ToDoViewHolder>(){
    private var toDoLists = listOf<ToDo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        return ToDoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list,
                parent,
                false
            )
        )
    }
    override fun getItemCount(): Int = toDoLists.count()

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        if(context != null){
            holder.bindItem(context, toDoLists[position], listener)
        }
    }

    fun setLists(lists: List<ToDo>){
        this.toDoLists = lists
        notifyDataSetChanged()
    }
}
class ToDoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    fun bindItem(context: Context, toDo: ToDo, listener: (ToDo, Int) -> Unit){
        itemView.item_title_list.text = toDo.title
        itemView.item_due_time.text = "${toDo.strDueDate}, ${toDo.strDueHour}"
        itemView.item_note.text = toDo.note

        itemView.setOnClickListener{
            listener(toDo, layoutPosition)
        }
    }
}