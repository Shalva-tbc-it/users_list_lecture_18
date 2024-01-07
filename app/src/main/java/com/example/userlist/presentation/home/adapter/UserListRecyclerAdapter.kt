package com.example.userlist.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.userlist.databinding.RecyclerUserBinding
import com.example.userlist.presentation.model.Users

class UserListRecyclerAdapter() : ListAdapter<Users, UserListRecyclerAdapter.UserListViewHolder>(UserListDiffCallback()) {

    private var onItemClickListener: ((Int) -> Unit)? = null
    private var onItemLongClickListener: ((Int) -> Unit)? = null

    inner class UserListViewHolder(private val binding: RecyclerUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind() = with(binding) {
            val users = currentList[absoluteAdapterPosition]
            Glide.with(root)
                .load(users.avatar)
                .centerCrop()
                .into(imgAvatar)

            tvEmail.text = users.email
            tvFirstName.text = users.firstName
            tvLastName.text = users.lastName

            root.setOnLongClickListener {
                onItemLongClickListener?.let {
                    it.invoke(users.id)

                    return@setOnLongClickListener true
                }
                return@setOnLongClickListener false
            }

            root.setOnClickListener {
                onItemClickListener?.let {
                    it.invoke(users.id)
                }
            }


        }
    }
    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }
    fun setonItemLongClickListener(longClickListener: (Int) -> Unit) {
        onItemLongClickListener = longClickListener
    }


    class UserListDiffCallback : DiffUtil.ItemCallback<Users>() {
        override fun areItemsTheSame(oldItem: Users, newItem: Users): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Users, newItem: Users): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        return UserListViewHolder(
            RecyclerUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind()
    }

}