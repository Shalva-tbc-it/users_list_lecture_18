package com.example.userlist.presentation.home

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userlist.R
import com.example.userlist.data.common.Resource
import com.example.userlist.databinding.FragmentHomeBinding
import com.example.userlist.presentation.base.BaseFragment
import com.example.userlist.presentation.home.adapter.UserListRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: UserListRecyclerAdapter
    private val userId: MutableList<Int> = mutableListOf()

    override fun start() {
        setAdapter()
        observe()
    }

    override fun clickListener() {
        getUserId()
        deleteUsers()
        clearUsersId()
    }

    private fun observe() {
        getUser()
        setNavEvent()
    }

    private fun deleteUsers() {
        binding.btnDeleteUsers.setOnClickListener {
            viewModel.userId(userId)
            btnGone()
        }
    }

    private fun clearUsersId() {
        binding.btnClearUsersId.setOnClickListener {
            userId.clear()
            viewModel.userId(userId)
            btnGone()
        }
    }

    private fun btnGone() {
        val animationGone =
            AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)
        binding.btnDeleteUsers.visibility = View.GONE
        binding.btnClearUsersId.visibility = View.GONE
        binding.btnClearUsersId.startAnimation(animationGone)
        binding.btnDeleteUsers.startAnimation(animationGone)
    }

    private fun btnVisible() {
        val animationVisible =
            AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        binding.btnDeleteUsers.visibility = View.VISIBLE
        binding.btnClearUsersId.visibility = View.VISIBLE
        binding.btnClearUsersId.startAnimation(animationVisible)
        binding.btnDeleteUsers.startAnimation(animationVisible)
    }

    private fun getUserId() {
        adapter.setOnItemClickListener(
            listener = {
                if (binding.btnDeleteUsers.isVisible) {
                    if (userId.contains(it)) {
                        userId.remove(it)
                    } else {
                        userId.add(it)
                    }
                } else {
                    viewModel.listener(it)
                }

            }
        )

        adapter.setonItemLongClickListener(
            longClickListener = {
                userId.add(it)
                btnVisible()
            }
        )
    }

    private fun setAdapter() {
        adapter = UserListRecyclerAdapter()
        binding.recyclerUsersList.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerUsersList.adapter = adapter
    }

    private fun getUser() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.UsersList.collect {
                    when (it) {
                        is Resource.Loading -> showProgressBar()

                        is Resource.Error -> {
                            Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_LONG)
                                .show()
                            binding.progressBar.visibility = View.GONE
                        }

                        is Resource.Success -> {
                            adapter.submitList(it.data)
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

    private fun setNavEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigationEvent.collect { navigationEvent ->
                    navEvent(navigationEvent)
                }
            }
        }
    }

    private fun navEvent(navigationEvent: NavigationEvent) {
        when (navigationEvent) {
            is NavigationEvent.NavigateToCurrentUser -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToCurrentUserFragment(navigationEvent.userId)
                )
            }
        }
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

}