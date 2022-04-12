package com.feiyatsu.reviewroomdb.ui.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feiyatsu.reviewroomdb.data.User
import com.feiyatsu.reviewroomdb.databinding.UserRowBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.UserViewHolder>() {

    private var userList = emptyList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        // Without viewBinding should be like this
        // return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_row,parent,false))
        return UserViewHolder(
            UserRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindItem(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    inner class UserViewHolder(private val itemBinding: UserRowBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private lateinit var user: User

        fun bindItem(user: User) {
            this.user = user

            itemBinding.apply {
                textViewId.text = user.id.toString()
                textViewFirstName.text = user.firstName.toString()
                textViewLastName.text = user.lastName.toString()
                textViewAge.text = user.age.toString()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(users: List<User>) {
        this.userList = users
        // Should change notifyDataSetChanged to DiffUtil
        notifyDataSetChanged()
    }
}