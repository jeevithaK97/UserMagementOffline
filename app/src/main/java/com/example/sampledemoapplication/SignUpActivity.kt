package com.example.sampledemoapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.sampledemoapplication.data.User
import com.example.sampledemoapplication.data.UserViewModel
import com.example.sampledemoapplication.databinding.ActivitySignUpBinding
import kotlinx.coroutines.Job

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    lateinit var viewModal: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(UserViewModel::class.java)

        binding.imgBack.setOnClickListener {
            goToSignIn("", "");
        }

        binding.btnRegister.setOnClickListener {
            validateInputs();
        }

    }

    private fun validateInputs() {
        var userName = binding.editName.text.toString();
        var email = binding.editEmail.text.toString();
        var passWord = binding.editPass.text.toString();

        if (userName.equals("") && email.equals("") && passWord.equals("")) {
            binding.editName.error = "Enter Username";
            binding.editEmail.error = "Enter Email";
            binding.editPass.error = "Enter Password";
        } else if (userName.equals("") && email.equals("") && !passWord.equals("")) {
            binding.editName.error = "Enter Username";
            binding.editEmail.error = "Enter Email";
        } else if (!userName.equals("") && email.equals("") && passWord.equals("")) {
            binding.editEmail.error = "Enter Email";
            binding.editPass.error = "Enter Password";
        } else if (userName.equals("") && !email.equals("") && passWord.equals("")) {
            binding.editName.error = "Enter Username";
            binding.editPass.error = "Enter Password";
        } else if (userName.equals("") && !email.equals("") && !passWord.equals("")) {
            binding.editName.error = "Enter Username";
        } else if (!userName.equals("") && email.equals("") && !passWord.equals("")) {
            binding.editEmail.error = "Enter Email";
        } else if (!userName.equals("") && !email.equals("") && passWord.equals("")) {
            binding.editPass.error = "Enter Password";
        } else {
            var i: Int = 0
            val registerUser = User(1, userName, email, passWord)
            viewModal.addUser(registerUser)
            goToSignIn(userName, passWord);
        }
    }

    private fun goToSignIn(userName: String, passWord: String) {
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.out, R.anim.`in`)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.out, R.anim.`in`)
        finish()
    }
}