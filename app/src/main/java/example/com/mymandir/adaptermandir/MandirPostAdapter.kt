package example.com.mymandir.adaptermandir

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import example.com.mymandir.R
import example.com.mymandir.models.MyMandirModel

class MandirPostAdapter: RecyclerView.Adapter<MandirPostViewHolder>() {

    var items: List<MyMandirModel> = emptyList()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MandirPostViewHolder {
        context = parent.context
        val v = LayoutInflater.from(context).inflate(R.layout.post_row, parent, false)
        return MandirPostViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MandirPostViewHolder, position: Int) {
        val post = items[position]
        holder.bindView(context, post, position)
    }
}