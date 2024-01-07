package com.example.userlist.presentation.current_user

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.userlist.data.common.Resource
import com.example.userlist.databinding.FragmentCurrentUserBinding
import com.example.userlist.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrentUserFragment : BaseFragment<FragmentCurrentUserBinding>(FragmentCurrentUserBinding::inflate) {

    private val viewModel: CurrentUserViewModel by viewModels()
    private val args: CurrentUserFragmentArgs by navArgs()
    override fun start() {
        viewModel.getCurrentUser(args.userId)
        observe()
    }

    override fun clickListener() {

    }

    private fun observe() {
        getUser()
    }

    private fun getUser() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getUser.collectLatest {
                    when (it) {
                        is Resource.Loading -> showProgressBar()

                        is Resource.Success -> {
                            val user = it.data
                            binding.apply {
                                Glide.with(requireContext())
                                    .load(user.avatar)
                                    .into(imgAvatar)
                                tvEmail.text = user.email
                                tvFirstName.text = user.firstName
                                tvLastName.text = user.lastName
                            }
                            binding.progressBar.visibility = View.GONE
                        }

                        is Resource.Error -> {
                            Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_LONG).show()
                            binding.progressBar.visibility = View.GONE
                        }

                        else -> {
                            Toast.makeText(requireContext(), "App error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }


    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

}