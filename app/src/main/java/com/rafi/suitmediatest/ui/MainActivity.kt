package com.rafi.suitmediatest.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import com.rafi.suitmediatest.databinding.ActivityMainBinding
import com.rafi.suitmediatest.utils.UserPreference

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreference = UserPreference(this)

        val name = binding.edtName.text

        binding.btnCheck.setOnClickListener {
            if (isPalindrome(name)) {
                binding.edtPalindrome.setText("Palindrome")
            } else {
                binding.edtPalindrome.setText("Not Palindrome")
            }
        }

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, SecondScreenActivity::class.java)
            userPreference.setName(name.toString())
            startActivity(intent)
        }
    }

    private fun isPalindrome(name: Editable): Boolean {
        val stringBuilder = StringBuilder(name.toString())
        val reserveName = stringBuilder.reverse().toString()

        return name.toString().replace("\\s".toRegex(), "").equals(reserveName.replace("\\s".toRegex(), ""), ignoreCase = true)

    }
}