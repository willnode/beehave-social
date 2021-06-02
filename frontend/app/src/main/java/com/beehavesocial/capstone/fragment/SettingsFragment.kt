package com.beehavesocial.capstone.fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.beehavesocial.capstone.databinding.FragmentSettingsBinding
import com.beehavesocial.capstone.view.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.provider.Settings
import android.view.*
import com.beehavesocial.capstone.R


@AndroidEntryPoint
class SettingsFragment : Fragment()  {

    private lateinit var binding: FragmentSettingsBinding

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root






    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProfile()
        viewModel.responseProfile.observe(viewLifecycleOwner, {
//            Log.d("Bearer", it.toString())
            binding.tvName.text=it.name
        })

        binding.btnLanguange.setOnClickListener {
            val tt = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(tt)

        }
    }




}





