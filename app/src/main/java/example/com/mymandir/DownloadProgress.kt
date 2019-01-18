package example.com.mymandir

import android.app.Dialog
import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import net.rmitsolutions.mfexpert.lms.helpers.logD
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL


class DownloadProgress : AppCompatActivity() {

    lateinit var btnShowProgress: Button
    private var pDialog: ProgressDialog? = null

    lateinit var my_image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_progress)

        btnShowProgress = findViewById(R.id.btnProgressBar) as Button

        my_image = findViewById(R.id.my_image) as ImageView

        btnShowProgress.setOnClickListener {
            DownloadFileFromURL().execute(file_url)
        }
    }

    override fun onCreateDialog(id: Int): Dialog? {
        when (id) {
            progress_bar_type -> {
                pDialog = ProgressDialog(this)
                pDialog!!.setMessage("Downloading file. Please wait...")
                pDialog!!.setIndeterminate(false)
                pDialog!!.setMax(100)
                pDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                pDialog!!.setCancelable(true)
                pDialog!!.show()
                return pDialog
            }
            else -> return null
        }
    }

    internal inner class DownloadFileFromURL : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            showDialog(progress_bar_type)
        }

        override fun doInBackground(vararg f_url: String): String? {
            var count: Int=0
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
            pDialog!!.progress = Integer.parseInt(progress[0])
        }

        override fun onPostExecute(file_url: String?) {
            dismissDialog(progress_bar_type)

            val imagePath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.mp4"
            logD(imagePath)
            //my_image.setImageDrawable(Drawable.createFromPath(imagePath))
        }
    }

    companion object {

        val progress_bar_type = 0

        private val file_url = "https://img4.mymandir.com/0143e8ab07a3e800f5cd6a6bb8a42e87-overlay"
    }
}
