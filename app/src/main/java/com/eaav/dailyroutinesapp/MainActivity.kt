package com.eaav.dailyroutinesapp


import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.eaav.dailyroutinesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ArrayAdapter<Routine>
    private val routines = mutableListOf<Routine>()

    private val ROUTINES_KEY = "routines_key"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Restore saved data if available
        if (savedInstanceState != null) {
            val savedRoutines = savedInstanceState.getParcelableArrayList<Routine>(ROUTINES_KEY)
            if (savedRoutines != null) {
                routines.addAll(savedRoutines)
            }
        }

        // Set up Toolbar
        setSupportActionBar(binding.toolbar)

        // Initialize ListView and Adapter
        adapter = object : ArrayAdapter<Routine>(
            this,
            android.R.layout.simple_list_item_1,
            routines
        ) {
            override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
                val view = super.getView(position, convertView, parent)
                val routine = getItem(position)
                (view as android.widget.TextView).text = routine?.name ?: ""
                return view
            }
        }
        binding.routinesList.adapter = adapter
        binding.routinesList.choiceMode = ListView.CHOICE_MODE_SINGLE

        // Add Button Click Listener
        binding.addButton.setOnClickListener {
            val routineName = binding.editTextRoutine.text.toString()
            if (routineName.isNotEmpty()) {
                val routine = Routine(name = routineName)
                routines.add(routine)
                adapter.notifyDataSetChanged()
                binding.editTextRoutine.text.clear()
            }
        }

        // Delete Selected Button Click Listener
        binding.deleteSelectedButton.setOnClickListener {
            val position = binding.routinesList.checkedItemPosition
            if (position != ListView.INVALID_POSITION) {
                routines.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        }

        // Delete All Button Click Listener
        binding.deleteAllButton.setOnClickListener {
            routines.clear()
            adapter.notifyDataSetChanged()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(ROUTINES_KEY, ArrayList(routines))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_spanish -> setLocale("es")
            R.id.action_english -> setLocale("en")
            R.id.action_light -> setThemeMode(AppCompatDelegate.MODE_NIGHT_NO)
            R.id.action_dark -> setThemeMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun setLocale(languageCode: String) {
        val locale = java.util.Locale(languageCode)
        java.util.Locale.setDefault(locale)
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        recreate()
    }

    private fun setThemeMode(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
        recreate()
    }
}