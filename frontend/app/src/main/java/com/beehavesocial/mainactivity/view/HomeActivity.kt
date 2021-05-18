package com.beehavesocial.mainactivity.view
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.beehavesocial.mainactivity.R
import com.beehavesocial.mainactivity.SettingsFragment
import com.beehavesocial.mainactivity.SocialMediaFragment
import com.beehavesocial.mainactivity.fragment.ForumFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var fragment: Fragment
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigation = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        fragmentManager = supportFragmentManager

        //Untuk inisialisasi fragment pertama kali
        fragmentManager.beginTransaction().replace(R.id.main_container, SocialMediaFragment()).commit()

        //Memberikan listener saat menu item di bottom navigation diklik
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            var id = item.itemId
            when (id) {
                R.id.page_1 -> fragment = SocialMediaFragment()
                R.id.page_3 -> fragment = ForumFragment()
                R.id.page_4 -> fragment = SettingsFragment()
            }
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.main_container, fragment).commit()
            true
        }
    }

    companion object {
        private val TAG = HomeActivity::class.java.simpleName
    }
}