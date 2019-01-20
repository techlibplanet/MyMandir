package example.com.mymandir

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import example.com.mymandir.mainfragment.MainFragment
import example.com.mymandir.postFragment.PostFragment
import net.rmitsolutions.mfexpert.lms.helpers.switchToFragment


class MainActivity : AppCompatActivity(), MainFragment.OnFragmentInteractionListener,
        PostFragment.OnFragmentInteractionListener {

    private var back_pressed = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainFrag = MainFragment()
        switchToFragment(mainFrag)
    }


    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        when (count) {
            0 -> {
                when {
                    back_pressed + 2000 > System.currentTimeMillis() -> super.onBackPressed()
                    else -> {
                        Toast.makeText(baseContext, "Tap again to exit", Toast.LENGTH_SHORT).show()
                        back_pressed = System.currentTimeMillis()
                    }
                }
            }
            1 -> supportFragmentManager.popBackStack()
        }
    }

    override fun onFragmentInteraction(uri: Uri) {

    }

}
