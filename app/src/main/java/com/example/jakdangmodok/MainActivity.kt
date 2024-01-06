package com.example.jakdangmodok

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.jakdangmodok.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val fragments: List<Fragment> by lazy {
        listOf(HomeFragment(), SubscribeFragment(), AddFragment(), BookFragment(), GroupFragment())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setBottomNavigationView()

        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.menu_home
        }
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, fragments[0]).commit()
                }

                R.id.menu_subscribe -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, fragments[1]).commit()
                }

                R.id.menu_add -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, fragments[2]).commit()
                }

                R.id.menu_book -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, fragments[3]).commit()
                }

                R.id.menu_group -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container,fragments[4]).commit()
                }
            }

            true
        }
    }
}