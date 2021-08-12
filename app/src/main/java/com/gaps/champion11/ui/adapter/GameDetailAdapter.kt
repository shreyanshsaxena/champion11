import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.gaps.champion11.R
import com.gaps.champion11.model.NumberDetail
import com.gaps.champion11.model.UserStatsResponse
import java.text.MessageFormat

class GameDetailAdapter(private val numberList: List<NumberDetail>,val userStatsResponse: UserStatsResponse, val context: Context?) :
    RecyclerView.Adapter<GameDetailAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflate the custom view from xml layout file
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.game_bet_detail_item, parent, false)

        // return the view holder
        return ViewHolder(view)

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.numberTxt.setTextColor(Color.WHITE)
        holder.betTxt.text= MessageFormat.format("₹ {0}",numberList[position].betAmount)
        if (numberList[position].number==userStatsResponse.correctOption) {
            if (context != null) {
                holder.betTxt.setTextColor(context.resources.getColor(R.color.colorPrimary))

                holder.numberTxt.background =
                    (ResourcesCompat.getDrawable(context.resources, R.drawable.ic_circle_green, null))
            }
        } else {
            if (context != null) {
                holder.betTxt.setTextColor(context.resources.getColor(R.color.colorPrimary))
                holder.numberTxt.background =
                    (ResourcesCompat.getDrawable(context.resources, R.drawable.ic_circle_red, null))
            }
        }
        if(numberList[position].isOptionBetUser && numberList[position].number==userStatsResponse.correctOption){
            holder.betTxt.visibility= View.VISIBLE
            if (context != null)
            holder.betTxt.setTextColor(context.resources.getColor(R.color.colorGreen))
        }

        holder.numberTxt.text = numberList[position].number
    }


    override fun getItemCount(): Int {
        // number of items in the data set held by the adapter
        return numberList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numberTxt: TextView = itemView.findViewById(R.id.numberTxt)
        val betTxt: TextView = itemView.findViewById(R.id.betTxt)

    }


    // this two methods useful for avoiding duplicate item
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }
}
