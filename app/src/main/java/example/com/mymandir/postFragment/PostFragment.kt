package example.com.mymandir.postFragment

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import com.example.mayank.kwizzapp.helpers.Converters
import example.com.mymandir.Constants

import example.com.mymandir.R
import example.com.mymandir.databinding.PostDataBinding
import example.com.mymandir.models.ClickHandler
import example.com.mymandir.models.MyMandirModel
import net.rmitsolutions.libcam.LibPermissions
import net.rmitsolutions.mfexpert.lms.helpers.logD
import org.jetbrains.anko.find
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class PostFragment : Fragment(), View.OnClickListener {

    private var listener: OnFragmentInteractionListener? = null
    private var save: Boolean = false
    private var comment: Boolean = false
    private var like: Boolean = false
    private lateinit var mandirModel: MyMandirModel
    private val CLICKABLES = intArrayOf(R.id.ic_comment, R.id.ic_save, R.id.ic_like)
    private lateinit var progressBar: android.app.ProgressDialog
    private lateinit var postVideo: VideoView
    private lateinit var mediaController: MediaController
    private lateinit var libPermission: LibPermissions
    private lateinit var dataBinding: PostDataBinding
    val permissions = arrayOf<String>(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mandirModel = arguments?.getSerializable("MyMandirModel") as MyMandirModel

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false)
        val view = dataBinding.root
        val timeStamp = Converters.fromTimestamp(mandirModel.createdAt)
        mandirModel.timeStamp = Constants.getFormatDate(timeStamp!!)!!
        dataBinding.postDataVm = mandirModel

        postVideo = view.find<VideoView>(R.id.post_video_view)

        libPermission = LibPermissions(this, permissions)

        val runnable = Runnable {
            logD("All permission enabled")
        }
        libPermission.askPermissions(runnable)

        mandirModel.attachments.forEach { attachment ->

            disablePlayButton(false)
            disableVideo(true)
            if (mandirModel.attachments != null) {
                if (attachment.type == "video" || attachment.type == "audio") {
                    dataBinding.sender = mandirModel.sender
                    dataBinding.handler = (object : ClickHandler {
                        override fun onClick(view: View) {
                            DownloadFileFromURL().execute(attachment.url)
                        }
                    })
                } else {
                    dataBinding.attachments = attachment
                    disablePlayButton(true)
                }
            }
        }
        return view
    }

    private fun disablePlayButton(disable: Boolean) {
        dataBinding.playButton = disable
    }

    private fun disableVideo(disable: Boolean) {
        dataBinding.playVideo = disable
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

    fun getFormatDate(date: Date): String? {
        val formatter: java.text.DateFormat = SimpleDateFormat(Constants.DISPLAY_FULL_DATE_FORMAT)
        return formatter.format(date)
    }

    override fun onPause() {
        super.onPause()
    }

    internal inner class DownloadFileFromURL : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            super.onPreExecute()

            progressBar = ProgressDialog(activity)
            progressBar.setCancelable(false);
            progressBar.setMessage("Downloading...")
            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            progressBar.progress = 0
            progressBar.max = 100
            progressBar.show()
        }

        override fun doInBackground(vararg f_url: String): String? {
            var count: Int = 0
            try {
                val url = URL(f_url[0])
                val connection = url.openConnection()
                connection.connect()

                val lenghtOfFile = connection.getContentLength()

                val input = BufferedInputStream(url.openStream(), 8192)

                val storageDir = Environment.getExternalStorageDirectory().getAbsolutePath()
                val fileName = "/downloadedfile.mp4"
                val imageFile = File(storageDir + fileName)
                val output = FileOutputStream(imageFile)

                val data = ByteArray(1024)
                var total: Long = 0
                count = input.read(data)
                while (count != -1) {
                    total += count.toLong()


                    publishProgress("" + (total * 100 / lenghtOfFile).toInt())

                    output.write(data, 0, count)

                    count = input.read(data)
                }
                output.flush()

                output.close()
                input.close()

            } catch (e: Exception) {
                Log.e("Error: ", e.message)
            }

            return null

        }

        override fun onProgressUpdate(vararg progress: String) {
            progressBar.progress = Integer.parseInt(progress[0])
        }

        override fun onPostExecute(file_url: String?) {
            progressBar.dismiss()

            val imagePath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.mp4"
            logD(imagePath)
            disablePlayButton(true)
            disableVideo(false)
            postVideo.setVideoPath(imagePath);
            mediaController = MediaController(activity)
            mediaController.setMediaPlayer(postVideo)
            postVideo.setMediaController(mediaController)
            postVideo.requestFocus()
            postVideo.start();
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
