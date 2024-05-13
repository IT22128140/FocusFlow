import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.focusflow.CardInfo
import com.example.focusflow.UpdateCard
import com.example.focusflow.database.FocusFlowDatabase
import com.example.focusflow.database.daos.FocusFlowDao
import com.example.focusflow.database.entities.FocusFlow
import com.example.focusflow.databinding.TaskViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class FocusFlowAdapter(private var data: List<CardInfo>) : RecyclerView.Adapter<FocusFlowAdapter.ViewHolder>(), Filterable {

    var filterListResult: List<CardInfo> = data

    inner class ViewHolder(val binding: TaskViewBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(cardInfo: CardInfo) {

            binding.title.text = cardInfo.title
            binding.desc.text = cardInfo.desc
            binding.priority.text = cardInfo.priority
            binding.dueTime.text = cardInfo.dueTime
            binding.status.isChecked = cardInfo.status

//            binding.status.setOnCheckedChangeListener { _, isChecked ->
//                cardInfo.status = isChecked
//                CoroutineScope(Dispatchers.IO).launch {
//                    database.dao().updateStatus(
//                        FocusFlow(
//                            position+1,
//                            cardInfo.title,
//                            cardInfo.desc,
//                            cardInfo.priority,
//                            cardInfo.dueTime,
//                            cardInfo.status
//                        )
//                    )
//                }
//            }

            binding.root.setOnClickListener {
                val intent = Intent(it.context, UpdateCard::class.java)
                intent.putExtra("id", adapterPosition)
                it.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TaskViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filterListResult[position])
        when (filterListResult[position].priority.lowercase(Locale.getDefault())) {
            "high" -> {
                holder.binding.myLayout.setBackgroundColor(Color.parseColor("#F05454"))
                holder.binding.priority.setTextColor(Color.parseColor("#D2DBC8"))
                holder.binding.title.setTextColor(Color.parseColor("#D2DBC8"))
                holder.binding.desc.setTextColor(Color.parseColor("#D2DBC8"))
                holder.binding.dueTime.setTextColor(Color.parseColor("#ffffff"))
            }

            "medium" -> holder.binding.myLayout.setBackgroundColor(Color.parseColor("#EDC988"))
            else -> holder.binding.myLayout.setBackgroundColor(Color.parseColor("#D2DBC8"))
        }
    }

    override fun getItemCount(): Int {
        return filterListResult.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charSearch = charSequence.toString()
                filterListResult = if (charSearch.isEmpty()) {
                    data
                } else {
                    val resultList = ArrayList<CardInfo>()
                    for (row in data) {
                        if (row.title.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterListResult
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults?) {
                filterListResult = filterResults?.values as List<CardInfo>
                notifyDataSetChanged()
            }
        }
    }
}
