package com.example.icesiapp231.add

import android.app.ActivityManager.TaskDescription
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.icesiapp231.R
import com.example.icesiapp231.databinding.PostBinding
import com.example.icesiapp231.model.Post

class PostAdapter(): Adapter<PostVH>() {

    private val posts = arrayListOf<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostVH {
        return PostVH(
            LayoutInflater.from(parent.context).inflate(R.layout.post, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostVH, position: Int) {
        Glide.with(holder.postImage.context).load(posts[position].photoURL).into(holder.postImage)
        holder.postText.text = posts[position].text
    }

    fun addPost(post: Post) {
        if(!posts.contains(post)) {
            posts.add(post)
            notifyItemInserted(posts.lastIndex)
        }
    }

}

class PostVH(root: View) : ViewHolder(root){
    private val binding:PostBinding = PostBinding.bind(root)
    val postImage = binding.postImage
    val postText = binding.postText
}