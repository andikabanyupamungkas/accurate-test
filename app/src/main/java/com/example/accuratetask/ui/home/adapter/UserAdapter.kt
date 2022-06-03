package com.example.accuratetask.ui.home.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.accuratetask.databinding.ItemUserBinding
import com.example.accuratetask.model.entity.User

class UserAdapter : ListAdapter<User, UserAdapter.ViewHolder>(UserComparator()) {

    public override fun getItem(position: Int): User {
        return super.getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val mBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        fun bind(user: User) {
            mBinding.tvName.text = user.name
            mBinding.tvAddress.text = user.address
            mBinding.tvEmail.text = user.email
            mBinding.tvNumber.text = user.phoneNumber
            mBinding.tvCity.text = user.city
        }
    }

    class UserComparator : DiffUtil.ItemCallback<User>() {

        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }

}