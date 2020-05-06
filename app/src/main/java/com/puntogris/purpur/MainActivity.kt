package com.puntogris.purpur


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.FragmentTransaction


class MainActivity : AppCompatActivity(),
    GameFragment.OnFragmentInteractionListener,
    PostGameFragment.OnFragmentInteractionListener
{

    override fun onFragmentInteraction(uri: Uri) {
    }

    private lateinit var gameFragment: GameFragment
    private lateinit var postGameFragment: PostGameFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        startService(Intent(this, MusicService::class.java))

        gameFragment = GameFragment.newInstance()
        postGameFragment = PostGameFragment.newInstance("A")

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_holder, GameFragment.newInstance())
            .addToBackStack(postGameFragment.toString())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

    }

    override fun onPause() {
        super.onPause()
        stopService(Intent(this, MusicService::class.java))
    }
}


