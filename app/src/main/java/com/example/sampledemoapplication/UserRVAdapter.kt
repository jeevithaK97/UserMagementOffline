package com.example.sampledemoapplication

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sampledemoapplication.data.UserInfo
import java.lang.Byte.decode
import java.net.URLDecoder.decode
import java.util.*
import kotlin.collections.ArrayList

class UserRVAdapter(
    val context: Context,
    val userClickDeleteInterface: UserClickDeleteInterface,
    private val userClickInterface: UserClickInterface
) : RecyclerView.Adapter<UserRVAdapter.ViewHolder>() {

    private val readAllUserData = ArrayList<UserInfo>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTV = itemView.findViewById<TextView>(R.id.idTVUserName)
        val noTV = itemView.findViewById<TextView>(R.id.idTVPhoneNo)
        val mailTV = itemView.findViewById<TextView>(R.id.idTVEmail)
        val deleteIV = itemView.findViewById<ImageView>(R.id.idIVDelete)
        val editIV = itemView.findViewById<ImageView>(R.id.idIVEdit)
        val profileIV = itemView.findViewById<ImageView>(R.id.img_profile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.user_rv_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.nameTV.setText(readAllUserData.get(position).name)
        holder.noTV.setText(readAllUserData.get(position).phoneno)
        holder.mailTV.setText(readAllUserData.get(position).email)
        var img_url: String = readAllUserData.get(position).image
//        holder.profileIV.setImageURI(img_url.toUri())
        if (img_url.equals("")) {
            holder.profileIV.setImageResource(R.drawable.ic_person)
        } else {
            Glide.with(context)
                .load(img_url)
                .into(holder.profileIV);
        }

        holder.deleteIV.setOnClickListener {
            userClickDeleteInterface.onDeleteIconClick(readAllUserData.get(position))
        }

        holder.editIV.setOnClickListener {
            userClickInterface.onNoteClick(readAllUserData.get(position))
        }
        holder.itemView.setOnClickListener {
            userClickInterface.onNoteClick(readAllUserData.get(position))
        }
    }

    override fun getItemCount(): Int {
        return readAllUserData.size
    }

    fun updateList(newList: List<UserInfo>) {
        readAllUserData.clear()
        readAllUserData.addAll(newList)
        notifyDataSetChanged()
    }
}

interface UserClickDeleteInterface {
    fun onDeleteIconClick(userInfo: UserInfo)
}

interface UserClickInterface {
    fun onNoteClick(userInfo: UserInfo)
}
