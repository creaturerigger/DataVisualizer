package com.sen4992.capstone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

private lateinit var auth: FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        val fragment: Fragment
        if(currentFragment == null) {
            fragment = if (auth.currentUser == null){
                LoginFragment()
            } else {
                DataFragment()
            }
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    companion object {
        fun newInstance(): MainActivity {
            return MainActivity()
        }
    }


}
