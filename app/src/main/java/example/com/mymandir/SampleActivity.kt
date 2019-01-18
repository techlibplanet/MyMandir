package example.com.mymandir

import android.Manifest
import android.app.Dialog
import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import net.rmitsolutions.libcam.LibCamera
import net.rmitsolutions.libcam.LibPermissions
import net.rmitsolutions.mfexpert.lms.helpers.logD
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import android.graphics.drawable.Drawable
import net.rmitsolutions.mfexpert.lms.helpers.hideProgress
import net.rmitsolutions.mfexpert.lms.helpers.showProgress
import java.io.BufferedInputStream


class SampleActivity : AppCompatActivity() {

    val permissions = arrayOf<String>(Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION)

    private lateinit var libPermissions: LibPermissions
    private lateinit var libCamera: LibCamera

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        libPermissions = LibPermissions(this, permissions)
        val runnable = Runnable {
            logD("All permission enabled")
            //ProgressBack().execute()

            DownloadFromURL().execute(url);

            //DownloadFileFromURL().execute("https://img4.mymandir.com/0143e8ab07a3e800f5cd6a6bb8a42e87-overlay")
        }
        libPermissions.askPermissions(runnable)

        libCamera = LibCamera(this)

    }

    private var pDialog: ProgressDialog? = null
    val progress_bar_type = 0

    override fun onCreateDialog(id: Int): Dialog? {
        when (id) {
            progress_bar_type -> {
                pDialog = ProgressDialog(this)
                pDialog?.setMessage("Downloading file. Please wait...")
                pDialog?.setIndeterminate(false)
                pDialog?.setMax(100)
                pDialog?.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                pDialog?.setCancelable(true)
                pDialog?.show()
                return pDialog
            }
            else -> return null
        }
    }

//    internal inner class DownloadFileFromURL : AsyncTask<String, String, String>() {
//
//        override fun onPreExecute() {
//            super.onPreExecute()
//            showDialog(progress_bar_type)
//        }
//
//        override fun doInBackground(vararg f_url: String): String? {
//            var count: Int
//            try {
//                val url = URL(f_url[0])
//                val connection = url.openConnection()
//                connection.connect()
//
//                val lenghtOfFile = connection.contentLength
//
//                val input = BufferedInputStream(url.openStream(), 8192)
//
//                val storageDir = Environment.getExternalStorageDirectory().absolutePath
//                val fileName = "/video.jpg"
//                val imageFile = File(storageDir + fileName)
//                val output = FileOutputStream(imageFile)
//
//                val data = ByteArray(1024)
//                var total: Long = 0
//                count = input.read(data)
//                while (count != -1) {
//                    count = input.read(data)
//                    total += count.toLong()
//
//                    publishProgress("" + (total * 100 / lenghtOfFile).toInt())
//
//                    output.write(data, 0, count)
//                }
//                output.flush()
//
//                output.close()
//                input.close()
//            } catch (e: Exception) {
//                Log.e("Error: ", e.message)
//            }
//
//            return null
//
//        }
//
//        override fun onProgressUpdate(vararg progress: String) {
//            pDialog?.setProgress(Integer.parseInt(progress[0]))
//        }
//
//        override fun onPostExecute(file_url: String?) {
//            dismissDialog(progress_bar_type)
//
//            val imagePath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.jpg"
//            //my_image.setImageDrawable(Drawable.createFromPath(imagePath))
//            logD(imagePath)
//        }
//    }

    private val url = "https://3.bp.blogspot.com/-EFwVj5ztKtQ/V8Qs6Viyl6I/AAAAAAAADWs/031SPYFrUnM-wreztTT4fgPe1yQj3LFhgCPcB/s1600/developer.jpg"
    val progressType = 0

    internal inner class DownloadFromURL : AsyncTask<String, String, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            showDialog(progressType)
        }

        override fun doInBackground(vararg fileUrl: String): String? {
            var count: Int
            try {
                val url = URL(fileUrl[0])
                val urlConnection = url.openConnection()
                urlConnection.connect()
                // show progress bar 0-100%
                val fileLength = urlConnection.contentLength
                val inputStream = BufferedInputStream(url.openStream(), 8192)
                val outputStream = FileOutputStream("/sdcard/downloadedfile.jpg")

                val data = ByteArray(1024)
                var total: Long = 0
                count = inputStream.read(data)
                while (count != -1) {
                    count = inputStream.read(data)
                    total += count.toLong()
                    publishProgress("" + (total * 100 / fileLength).toInt())
                    outputStream.write(data, 0, count)
                }
                // flushing output
                outputStream.flush()
                // closing streams
                outputStream.close()
                inputStream.close()

            } catch (e: Exception) {
                Log.e("Error: ", e.message)
            }

            return null
        }

        // progress bar Updating

        override fun onProgressUpdate(vararg progress: String) {
            // progress percentage
            pDialog?.setProgress(Integer.parseInt(progress[0]))
        }

        override fun onPostExecute(file_url: String?) {
            dismissDialog(progressType)
            val imagePath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.jpg"
            logD(imagePath)
            //imageView.setImageDrawable(Drawable.createFromPath(imagePath))
        }
    }
}
