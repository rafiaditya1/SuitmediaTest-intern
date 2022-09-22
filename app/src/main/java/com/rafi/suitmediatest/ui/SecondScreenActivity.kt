package com.rafi.suitmediatest.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rafi.suitmediatest.databinding.ActivitySecondScreenBinding
import com.rafi.suitmediatest.utils.UserPreference

class SecondScreenActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivitySecondScreenBinding
    private lateinit var  userPreference: UserPreference
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userPreference = UserPreference(this)


        val name = userPreference.preference.getString("name", "")
        binding.tvName.text = name

        val selectedName = userPreference.preference.getString("selected_name", "")
        binding.tvSelectedUser.text = selectedName

        binding.btnChoose.setOnClickListener {
            val intent = Intent(this, ThirdScreenActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        const val NAME = "name"
        const val SELECTED_NAME = "selected_name"
    }
}