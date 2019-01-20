package example.com.mymandir.adaptermandir

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.example.mayank.kwizzapp.helpers.Converters
import example.com.mymandir.Constants
import example.com.mymandir.databinding.MyMandirRowDataBinding
import example.com.mymandir.models.MyMandirModel
import example.com.mymandir.postFragment.PostFragment
import net.rmitsolutions.mfexpert.lms.helpers.switchToFragmentBackStack

class MyMandirViewHolder(val dataBinding: MyMandirRowDataBinding) : RecyclerView.ViewHolder(dataBinding.root) {

    private lateinit var context: Context

    fun bindView(context: Context, postModel: MyMandirModel) {
        this.context = context
        val timeStamp = Converters.fromTimestamp(postModel.createdAt)
        postModel.timeStamp = Constants.getFormatDate(timeStamp!!)!!
        dataBinding.postDataVm = postModel

        dataBinding.root.setOnClickListener{
            val bundle = Bundle()
            bundle.putSerializable("MyMandirModel", postModel)
            val postFrag = PostFragment()
            postFrag.arguments = bundle
            context.switchToFragmentBackStack(postFrag)
        }

        if (postModel.attachments != null) {
            postModel.attachments.forEach { attachment ->

                disablePlayButton(false)
                if (attachment.type == "video" || attachment.type == "audio") {
                    dataBinding.sender = postModel.sender
                } else {
                    dataBinding.attachments = attachment
                    disablePlayButton(true)
                }
            }
        }
    }

    private fun disablePlayButton(disable: Boolean) {
        dataBinding.playButton = disable
    }
}