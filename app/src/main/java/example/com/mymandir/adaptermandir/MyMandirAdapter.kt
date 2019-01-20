package example.com.mymandir.adaptermandir

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import example.com.mymandir.databinding.MyMandirRowDataBinding
import example.com.mymandir.models.MyMandirModel

class MyMandirAdapter: RecyclerView.Adapter<MyMandirViewHolder>() {

    var items: List<MyMandirModel> = emptyList()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMandirViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val dataBinding = MyMandirRowDataBinding.inflate(inflater, parent, false)
        return MyMandirViewHolder(dataBinding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyMandirViewHolder, position: Int) {
        val post = items[position]
        holder.bindView(context, post)
    }
}