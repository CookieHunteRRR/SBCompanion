package com.cookiehunterrr.sbcompanion

import android.os.Bundle
import android.view.Menu
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.room.Room
import com.cookiehunterrr.sbcompanion.database.Database
import com.cookiehunterrr.sbcompanion.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var programManager: ProgramManager
    lateinit var db: Database

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(
            applicationContext,
            Database::class.java, "database.db"
        ).allowMainThreadQueries().build()
        programManager = ProgramManager(this, db)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_profile_selection,
                R.id.nav_profile,
                R.id.nav_mining_forge
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //programManager.onActivityCreated()
        val currentProfile = db.profileInfoDao().getCurrentlySelectedProfile()
        if (currentProfile != null) {
            programManager.setCurrentUserData(currentProfile.playerUUID, currentProfile.profileUUID)
            moveToProfile()
        }
    }

    fun moveToProfileSelection() {
        navController.navigate(R.id.action_nav_profile_to_nav_profile_selection)
    }

    fun moveToProfile() {
        navController.navigate(R.id.action_nav_profile_selection_to_nav_profile)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}