package example.com.mymandir

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.mayank.kwizzapp.dependency.components.DaggerInjectActivityComponent
import example.com.mymandir.MyMandirApplication.Companion.applicationComponent
import example.com.mymandir.helper.processRequest
import example.com.mymandir.network.IMyMandir
import io.reactivex.disposables.CompositeDisposable
import net.rmitsolutions.mfexpert.lms.helpers.logD
import org.jetbrains.anko.toast
import javax.inject.Inject
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var myMandirService: IMyMandir
    private lateinit var compositeDisposable : CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val depComponent = DaggerInjectActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
        depComponent.injectMainActivity(this)

        compositeDisposable = CompositeDisposable()

        findViewById<Button>(R.id.testApi).setOnClickListener{
            compositeDisposable.add(myMandirService.getData()
                    .processRequest(
                            { response ->
                                for (data in response){
//                                    logD("Text - ${data.text}")
//                                    logD("Title - ${data.title}")
//                                    logD("Xid  - ${data.xid}")

                                    logD("Image Url - ${data.sender.imageUrl}")
                                    logD("Language - ${data.sender.language}")

//                                    for (tags in data.tags){
//                                        logD("Description - ${tags.description}")
//                                        logD("ImageUrl - ${tags.imageUrl}")
//                                        logD("Text - ${tags.text}")
//                                    }
//
//                                    for(attachment in data.attachments){
//                                        logD("DownSampleUrl - ${attachment.downsampled_url}")
//                                        logD("MobileUrl - ${attachment.mobile_url}")
//                                        logD("MediumUrl - ${attachment.medium_url}")
//                                    }
                                }
                            },
                            {err ->
                                toast("Error - $err")
                                logD("Error - $err")
                            }
                    ))
        }
    }
}
