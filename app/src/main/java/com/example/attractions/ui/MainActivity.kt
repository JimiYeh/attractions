package com.example.attractions.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.attractions.R
import com.example.attractions.databinding.ActivityMainBinding
import com.example.attractions.model.Language
import com.example.attractions.repository.network.interceptor.LanguageInterceptor
import com.example.attractions.ui.home.HomeFragment
import com.example.attractions.ui.home.HomeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private val languageInterceptor: LanguageInterceptor by inject()
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // 如果是首次創建,添加 HomeFragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, HomeFragment())
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_language -> {
                showLanguageDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLanguageDialog() {
        val languages = Language.entries.toTypedArray()
        val currentLanguage = Language.fromCode(languageInterceptor.language) ?: Language.CHINESE_TRADITIONAL
        
        val adapter = object : ArrayAdapter<Language>(
            this,
            R.layout.item_language,
            R.id.text_language,
            languages
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val textView = view.findViewById<TextView>(R.id.text_language)
                val language = getItem(position)
                
                language?.let {
                    textView.text = getString(it.displayNameRes)
                    
                    // 設置選中項目的背景和文字顏色
                    if (it == currentLanguage) {
                        textView.setBackgroundColor(getColor(R.color.primary))
                        textView.setTextColor(getColor(R.color.white))
                    } else {
                        textView.setBackgroundColor(getColor(android.R.color.transparent))
                        textView.setTextColor(getColor(android.R.color.black))
                    }
                }
                
                return view
            }
        }

        MaterialAlertDialogBuilder(this, R.style.CustomAlertDialog)
            .setTitle(R.string.dialog_title_language)
            .setAdapter(adapter) { dialog, which ->
                val selectedLanguage = languages[which]
                if (selectedLanguage != currentLanguage) {
                    languageInterceptor.language = selectedLanguage.code
                    homeViewModel.refresh()
                    dialog.dismiss()
                    recreate()
                }
            }
            .show()
    }
}