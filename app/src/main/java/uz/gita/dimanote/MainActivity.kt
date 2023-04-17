package uz.gita.dimanote

import android.annotation.SuppressLint
import android.app.*
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import uz.gita.dimanote.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Log.i("TTT: ", "Granted")
            } else {
                Log.i("TTT: ", "Denied")
            }
        }
    private var isNight = false

    @SuppressLint("AppCompatMethod")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when {
            ContextCompat.checkSelfPermission(
                this@MainActivity, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED -> {

            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) -> {

            }

            else -> {
                requestPermissionLauncher.launch(
                    android.Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }

        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                val colorDrawable = ColorDrawable(Color.parseColor("#888888"))
                supportActionBar?.setBackgroundDrawable(colorDrawable)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                val colorDrawable = ColorDrawable(Color.parseColor("#3461FD"))
                supportActionBar?.setBackgroundDrawable(colorDrawable)
            }
        }


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)

        binding.navigationView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.navigationView.itemIconTintList = null

        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.trashScreen -> {
                    navController.navigate(R.id.action_homeFragment_to_trashFragment)
                    Log.d("DDD","Activity -> onCreate navigate to trash")
                }
                R.id.night_mode -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    val colorDrawable = ColorDrawable(Color.parseColor("#888888"))
                    supportActionBar?.setBackgroundDrawable(colorDrawable)
                    Log.d("DDD","Activity -> onCreate")
                    recreate()
                }
                R.id.light_mode -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    val colorDrawable = ColorDrawable(Color.parseColor("#3461FD"))
                    supportActionBar?.setBackgroundDrawable(colorDrawable)
                    recreate()
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        Log.d("DDD","Activity -> onCreate")
    }

    override fun finish() {
        super.finish()
        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }
}