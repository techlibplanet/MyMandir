package example.com.mymandir.adaptermandir

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.mayank.kwizzapp.helpers.Converters
import example.com.mymandir.Constants
import example.com.mymandir.R
import example.com.mymandir.models.MyMandirModel
import example.com.mymandir.postFragment.PostFragment
import net.rmitsolutions.mfexpert.lms.helpers.switchToFragmentBackStack
import org.jetbrains.anko.find


class MandirPostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var save: Boolean = false
    private var comment: Boolean = false
    private var like: Boolean = false

    fun bindView(context: Context, postModel: MyMandirModel, position: Int) {

        val testViewUserName = itemView.find<TextView>(R.id.userName)
        val testViewTimeStamp = itemView.find<TextView>(R.id.textViewTimeStamp)
        val textViewTitle = itemView.find<TextView>(R.id.textViewTitle)
        val profileImage = itemView.find<ImageView>(R.id.profile_image)
        val postImage = itemView.find<ImageView>(R.id.post_image_view)
        val saveImage = itemView.find<ImageView>(R.id.ic_save)
        val commentImage = itemView.find<ImageView>(R.id.ic_comment)
        val likeImage = itemView.find<ImageView>(R.id.ic_like)
        val playImage = itemView.find<ImageView>(R.id.ic_play)

        itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("MyMandirModel", postModel)
            val postFrag = PostFragment()
            postFrag.arguments = bundle
            context.switchToFragmentBackStack(postFrag)
        }

        saveImage.setOnClickListener {
            if (!save) {
                save = true
                saveImage.setImageResource(R.drawable.ic_save_blue)
            } else {
                save = false
                saveImage.setImageResource(R.drawable.ic_save_grey)
            }
        }

        commentImage.setOnClickListener {
            if (!comment) {
                comment = true
                commentImage.setImageResource(R.drawable.ic_comment_blue)
            } else {
                comment = false
                commentImage.setImageResource(R.drawable.ic_comment_grey)
            }
        }

        likeImage.setOnClickListener {
            if (!like) {
                like = true
                likeImage.setImageResource(R.drawable.ic_thumb_up_blue)
            } else {
                like = false
                likeImage.setImageResource(R.drawable.ic_thumb_up_grey)
            }
        }

        val timeStamp = Converters.fromTimestamp(postModel.createdAt)
        if (postModel.attachments != null) {
            postModel.attachments.forEach { attachment ->
                if (attachment.type == "video" || attachment.type == "audio") {
                    playImage.visibility = View.VISIBLE
                    testViewUserName.text = postModel.sender.name
                    Glide.with(context).load(postModel.sender.imageUrl).into(profileImage)
                    Glide.with(context).load(postModel.sender.imageUrl).into(postImage)
                } else {
                    playImage.visibility = View.GONE
                    testViewUserName.text = attachment.userName
                    Glide.with(context).load(attachment.userImage).into(profileImage)
                    Glide.with(context).load(attachment.mobile_url).into(postImage)
                }
            }
        }

        testViewTimeStamp.text = Constants.getFormatDate(timeStamp!!)
        textViewTitle.text = postModel.title

    }
}