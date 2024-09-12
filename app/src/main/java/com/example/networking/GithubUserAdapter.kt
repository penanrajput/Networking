package com.example.networking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.networking.databinding.ItemRowBinding

class GithubUserAdapter(private val githubUsers: ArrayList<GithubUser>) :
    RecyclerView.Adapter<GithubUserAdapter.GithubViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubViewHolder {
        // Using ViewBinding to inflate the layout
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GithubViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GithubViewHolder, position: Int) {
        // Bind the data to the view
        holder.bind(githubUsers[position])
    }

    override fun getItemCount() = githubUsers.size

    class GithubViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(githubUser: GithubUser) {
            binding.tViewLogin.text = githubUser.login
            binding.tViewScore.text = githubUser.score.toString()
            binding.tViewUrl.text = githubUser.html_url

        }
    }
}