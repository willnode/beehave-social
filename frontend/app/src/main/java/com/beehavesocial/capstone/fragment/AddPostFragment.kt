package com.beehavesocial.capstone.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.beehavesocial.capstone.databinding.FragmentAddPostBinding
import com.beehavesocial.capstone.view.article.AddPostViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPostFragment : Fragment() {

    private lateinit var binding: FragmentAddPostBinding
    private val addPostViewModel: AddPostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddPostBinding.inflate(layoutInflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSend.setOnClickListener{
            val title = binding.titleInput.text
            val content = binding.contentInput.text
            val url = binding.urlInput.text

            addPostViewModel.addPost(title.toString(), content.toString(), url.toString())
            addPostViewModel.action.observe(viewLifecycleOwner,{
                when(it){
                    AddPostViewModel.ACTION_INPUT_SUCCESS -> {
                        Snackbar.make(binding.root, "Content Berhasil Di Buat", Snackbar.LENGTH_SHORT)
                            .show()
                        val intent = Intent(getActivity(), SocialMediaFragment::class.java)
                        startActivity(intent)
                    }
                    AddPostViewModel.ACTION_INPUT_ERROR -> inputError()
                    AddPostViewModel.ACTION_INPUT_FAILED -> inputFailed()
                }
            })

        }

    }
    private fun inputFailed() {
        Snackbar.make(binding.root, "Input Error", Snackbar.LENGTH_SHORT).show()
    }

    private fun inputError() {
        Snackbar.make(binding.root, "Input Gagal", Snackbar.LENGTH_SHORT).show()
    }
}