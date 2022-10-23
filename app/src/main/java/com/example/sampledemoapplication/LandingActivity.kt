package com.example.sampledemoapplication

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.sampledemoapplication.data.UserInfo
import com.example.sampledemoapplication.data.UserInfoViewModel
import com.example.sampledemoapplication.databinding.ActivityLandingBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class LandingActivity : AppCompatActivity(), UserClickInterface, UserClickDeleteInterface {

    private lateinit var binding: ActivityLandingBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val sharedLoginData = "sharedLoginData"

    lateinit var viewModel: UserInfoViewModel
//    lateinit var userRV: RecyclerView

    var SELECT_PICTURE = 200
    lateinit var img_profile: ImageView
    var profile_Img: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val userRVAdapter = UserRVAdapter(this, this, this)
        binding.recyclerView.adapter = userRVAdapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(UserInfoViewModel::class.java)

        viewModel.readAllUserData.observe(this, Observer { list ->
            list?.let {
                userRVAdapter.updateList(it)
            }
        })

        sharedPreferences = getSharedPreferences(sharedLoginData, MODE_PRIVATE)

        binding.toolbar.setTitle(R.string.txt_addUser)
        binding.toolbar.setTitleTextColor(Color.WHITE)

        binding.imgLogout.setOnClickListener {
            callAlertBox();
        }

        binding.cardView.setOnClickListener {
            callBottomSheetDialog("Add", 0, "", "", "", "");
        }

    }

    private fun callAlertBox() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.alertmsg)

        builder.setPositiveButton("Yes") { dialogInterface, which ->
            makeLogout();
        }
        builder.setNegativeButton("No") { dialogInterface, which ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun makeLogout() {
          val editor: SharedPreferences.Editor = sharedPreferences.edit()
          editor.putBoolean("LOGIN", false)
          editor.commit()

          intent = Intent(this, MainActivity::class.java)
          startActivity(intent)
          finish()
    }

    private fun callBottomSheetDialog(
        update: String,
        user_id: Int,
        userInfo: String,
        phoneno: String,
        email: String,
        image: String
    ) {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
        val Name = view.findViewById<EditText>(R.id.idTVName)
        val PhoneNo = view.findViewById<EditText>(R.id.idTVPhoneNo)
        val Email = view.findViewById<EditText>(R.id.idTVEmail)
        val txt_Title = view.findViewById<TextView>(R.id.txt_Title)
        val btnAdd = view.findViewById<Button>(R.id.idBtnAdd)
        val btnClose = view.findViewById<ImageButton>(R.id.imgButtonclose)
        val idBtnAdd = view.findViewById<Button>(R.id.idBtnAdd)
        img_profile = view.findViewById<ImageView>(R.id.img_profile)

        if (update.equals("Edit")) {
            Name.setText(userInfo)
            PhoneNo.setText(phoneno)
            Email.setText(email)
            txt_Title.setText(R.string.txt_editInfo)
            idBtnAdd.setText(R.string.txt_edit)
            if (image.equals("")) {
                img_profile.setImageResource(R.drawable.ic_person)
            } else {
                Glide.with(this)
                    .load(image)
                    .into(img_profile);
            }

        }

        img_profile.setOnClickListener {
            chooseImageFromGalary();
        }

        btnAdd.setOnClickListener {
            if (Name.text.toString().equals("") || PhoneNo.text.toString()
                    .equals("") || Email.text.toString().equals("")
            ) {
                Toast.makeText(this, "Kindly enter all the details..", Toast.LENGTH_SHORT).show()
            } else {
                if (update.equals("Add")) {
                    var i: Int = 0
                    val registerUser = UserInfo(
                        i++,
                        Name.text.toString(),
                        PhoneNo.text.toString(),
                        Email.text.toString(),
                        profile_Img
                    )
                    viewModel.addUserInfo(registerUser)
                    dialog.dismiss()
                } else if (update.equals("Edit")) {
                    val updatedNote = UserInfo(
                        user_id, Name.text.toString(),
                        PhoneNo.text.toString(),
                        Email.text.toString(),
                        profile_Img
                    )
                    viewModel.updateUserInfo(updatedNote)
                    dialog.dismiss()
                }
            }
        }

        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun chooseImageFromGalary() {

        Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(this, "Select Picture"), SELECT_PICTURE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {
                val selectedImageUri: Uri? = data?.data
                if (null != selectedImageUri) {
                    profile_Img = selectedImageUri.toString()
//                    img_profile.setImageURI(selectedImageUri)
                    Glide.with(this)
                        .load(profile_Img)
                        .into(img_profile);
                } else {
                    img_profile.setImageResource(R.drawable.ic_person)
                }

            }
        }
    }

    override fun onDeleteIconClick(userInfo: UserInfo) {
        viewModel.deleteUseInfo(userInfo)
    }

    override fun onNoteClick(userInfo: UserInfo) {
        /*  // opening a new intent and passing a data to it.
          val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
          intent.putExtra("noteType", "Edit")
          intent.putExtra("noteTitle", note.noteTitle)
          intent.putExtra("noteDescription", note.noteDescription)
          intent.putExtra("noteId", note.id)
          startActivity(intent)
          this.finish()*/
        callBottomSheetDialog(
            "Edit",
            userInfo.id,
            userInfo.name,
            userInfo.phoneno,
            userInfo.email,
            userInfo.image
        );
    }

}