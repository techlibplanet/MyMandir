package example.com.mymandir.mainfragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mayank.kwizzapp.dependency.components.DaggerInjectActivityComponent
import com.example.mayank.kwizzapp.dependency.components.DaggerInjectFragmentComponent
import example.com.mymandir.MyMandirApplication

import example.com.mymandir.R
import example.com.mymandir.adaptermandir.MandirPostAdapter
import example.com.mymandir.helper.processRequest
import example.com.mymandir.models.MyMandirModel
import example.com.mymandir.network.IMyMandir
import io.reactivex.disposables.CompositeDisposable
import net.rmitsolutions.mfexpert.lms.helpers.hideProgress
import net.rmitsolutions.mfexpert.lms.helpers.logD
import net.rmitsolutions.mfexpert.lms.helpers.showProgress
import net.rmitsolutions.mfexpert.lms.helpers.toast
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import javax.inject.Inject


class MainFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    @Inject
    lateinit var myMandirService: IMyMandir
    private lateinit var compositeDisposable : CompositeDisposable

    private lateinit var mandirRecyclerView: RecyclerView
    val adapterMandir: MandirPostAdapter by lazy { MandirPostAdapter() }
    lateinit var modelListMandir: MutableList<MyMandirModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val depComponent = DaggerInjectFragmentComponent.builder()
                .applicationComponent(MyMandirApplication.applicationComponent)
                .build()
        depComponent.injectMainFragment(this)

        compositeDisposable = CompositeDisposable()

        mandirRecyclerView = view.find(R.id.post_recycler_view)
        mandirRecyclerView.layoutManager = LinearLayoutManager(activity)
        mandirRecyclerView.setHasFixedSize(true)
        mandirRecyclerView.addItemDecoration(DividerItemDecoration(activity, 0))
        mandirRecyclerView.adapter = adapterMandir
        modelListMandir = mutableListOf<MyMandirModel>()

        setMyMandirItem()
        return view
    }


    private fun setMyMandirItem() {
        showProgress()
        compositeDisposable.add(myMandirService.getData()
                .processRequest(
                        { response ->
                            hideProgress()
                            setMandirRecyclerViewAdapter(response)
                        },
                        {err ->
                            hideProgress()
                            toast("Error - $err")
                            logD("Error - $err")
                        }
                ))
    }




    private fun setMandirRecyclerViewAdapter(modelList: MutableList<MyMandirModel>) {
        adapterMandir.items = modelList
        adapterMandir.notifyDataSetChanged()
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
