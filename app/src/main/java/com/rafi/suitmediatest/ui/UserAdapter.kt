package com.rafi.suitmediatest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.rafi.suitmediatest.api.DataItem
import com.rafi.suitmediatest.databinding.ItemListUserBinding

class UserAdapter(private val listUser: List<DataItem>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    inner class ViewHolder(var binding: ItemListUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listUser[position]
        with(holder.binding) {
            tvName.text = user.firstName + " " + user.lastName
            tvEmail.text = user.email
            Glide.with(root.context)
                .load(user.avatar)
                .transform(CircleCrop())
                .into(imgAvatar)
            root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DataItem)
    }

}