package com.example.sampledemoapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sampledemoapplication.data.UserViewModel
import com.example.sampledemoapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val sharedLoginData = "sharedLoginData"

    lateinit var viewModal: UserViewModel
    private var usernme: String = ""
    private var usermail: String = ""
    var isLogin: Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(UserViewModel::class.java)

        viewModal.readAllData.observe(this, Observer { list ->
            list.let {
                if (!it.isEmpty()) {
                    usernme = it[0].name
                    usermail = it[0].email
                }
            }
        })

        sharedPreferences = getSharedPreferences(sharedLoginData, MODE_PRIVATE)
        isLogin = sharedPreferences.getBoolean("LOGIN", false)

        loginManagement(isLogin);

        binding.btnSignIn.setOnClickListener {
            validateInputs();
        }

        binding.btnSignUp.setOnClickListener {
            goToSignUp();
        }

    }

    private fun validateInputs() {
        var userName = binding.editName.text.toString();
        var passWord = binding.editPass.text.toString();

        if (userName.equals("") && passWord.equals("")) {
            binding.editName.error = "Enter Username";
            binding.editPass.error = "Enter Password";
        } else if (userName.equals("") && !passWord.equals("")) {
            binding.editName.error = "Enter Username";
        } else if (!userName.equals("") && passWord.equals("")) {
            binding.editPass.error = "Enter Password";
        } else {
            if (usernme.equals(userName) && usermail.equals(passWord)) {
                goToSignIn();
            } else {
                Toast.makeText(this, "Enter valid username or password..", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun goToSignIn() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean("LOGIN", true)
        editor.commit()

        intent = Intent(this, LandingActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToSignUp() {
        intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.`in`, R.anim.out)
    }

    private fun loginManagement(isLogin: Boolean) {
        if (isLogin) {
            val i = Intent(this, LandingActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (isLogin) {
            val i = Intent(this, LandingActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}