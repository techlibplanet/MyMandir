package example.com.mymandir.postFragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import com.bumptech.glide.Glide
import com.example.mayank.kwizzapp.helpers.Converters
import example.com.mymandir.Constants

import example.com.mymandir.R
import example.com.mymandir.models.MyMandirModel
import net.rmitsolutions.mfexpert.lms.helpers.logD
import org.jetbrains.anko.find

class PostFragment : Fragment(), View.OnClickListener {

    private var listener: OnFragmentInteractionListener? = null
    private var save: Boolean = false
    private var comment: Boolean = false
    private var like: Boolean = false
    private lateinit var mandirModel: MyMandirModel
    private val CLICKABLES = intArrayOf(R.id.ic_comment, R.id.ic_save, R.id.ic_like)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mandirModel = arguments?.getSerializable("MyMandirModel") as MyMandirModel

        logD("Text - ${mandirModel.text}")
        logD("Type - ${mandirModel.type}")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_post, container, false)
        val testViewUserName = view.find<TextView>(R.id.userName)
        val testViewTimeStamp = view.find<TextView>(R.id.textViewTimeStamp)
        val textViewTitle = view.find<TextView>(R.id.textViewTitle)
        val profileImage = view.find<ImageView>(R.id.profile_image)
        val postImage = view.find<ImageView>(R.id.post_image_view)
        val playImage = view.find<ImageView>(R.id.ic_play)
        val postVideo = view.find<VideoView>(R.id.post_video_view)

        val timeStamp = Converters.fromTimestamp(mandirModel.createdAt)
        if (mandirModel.attachments != null) {
            mandirModel.attachments.forEach { attachment ->
                if (attachment.type == "video" || attachment.type == "audio") {
                    postVideo.visibility = View.VISIBLE
                    playImage.visibility = View.VISIBLE
                    testViewUserName.text = mandirModel.sender.name
                    Glide.with(this).load(mandirModel.sender.imageUrl).into(profileImage)
                    Glide.with(this).load(mandirModel.sender.imageUrl).into(postImage)
                    playImage.setOnClickListener {
                        playImage.visibility = View.GONE
                        postVideo.setVideoPath(attachment.url);
                        postVideo.start();
                    }
                } else {
                    postVideo.visibility = View.GONE
                    playImage.visibility = View.GONE
                    testViewUserName.text = attachment.userName
                    Glide.with(this).load(attachment.userImage).into(profileImage)
                    Glide.with(this).load(attachment.mobile_url).into(postImage)
                }
            }
        }

        testViewTimeStamp.text = Constants.getFormatDate(timeStamp!!)
        textViewTitle.text = mandirModel.title
        for (id in CLICKABLES) {
            view.find<ImageView>(id).setOnClickListener(this)
        }
        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ic_save -> {
                if (!save) {
                    save = true
                    v.find<ImageView>(R.id.ic_save).setImageResource(R.drawable.ic_save_blue)
                } else {
                    save = false
                    v.find<ImageView>(R.id.ic_save).setImageResource(R.drawable.ic_save_grey)
                }
            }
            R.id.ic_comment -> {
                if (!comment) {
                    comment = true
                    v.find<ImageView>(R.id.ic_comment).setImageResource(R.drawable.ic_comment_blue)
                } else {
                    comment = false
                    v.find<ImageView>(R.id.ic_comment).setImageResource(R.drawable.ic_comment_grey)
                }
            }
            R.id.ic_like -> {
                if (!like) {
                    like = true
                    v.find<ImageView>(R.id.ic_like).setImageResource(R.drawable.ic_thumb_up_blue)
                } else {
                    like = false
                    v.find<ImageView>(R.id.ic_like).setImageResource(R.drawable.ic_thumb_up_grey)
                }
            }
        }
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

}
