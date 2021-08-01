package com.solomaticydl.moneymaker.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.solomaticydl.moneymaker.R
import com.solomaticydl.moneymaker.databinding.FragmentRecordListBinding
import com.solomaticydl.moneymaker.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecordListFragment :
    BaseFragment<FragmentRecordListBinding>(FragmentRecordListBinding::inflate) {

    private val viewModel: RecordListViewModel by viewModel()

    private val recordListAdapter = RecordListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFabButton(view)
        initSwipeRefreshLayout()
        initRecyclerView()
        initObservers()
        viewModel.requestRecordList()
    }

    private fun initFabButton(view: View) {
        binding.fabAdd.setOnClickListener {
            val action = RecordListFragmentDirections.actionRecordListFragmentToAdditionFragment()
            view.findNavController().navigate(action)
        }
    }

    private fun initSwipeRefreshLayout() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.requestRecordList()
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = recordListAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        recordListAdapter.clickListener = {
            // TODO: open detail page
        }
        recordListAdapter.longClickListener = {
            viewModel.deleteRecord(it)
        }
    }

    private fun initObservers() {
        viewModel.loadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            binding.swipeRefresh.isRefreshing = isLoading
        }
        viewModel.recordListLiveData.observe(viewLifecycleOwner) { result ->
            recordListAdapter.submitList(result.getOrDefault(mutableListOf()))
        }
        viewModel.deleteLiveData.observe(viewLifecycleOwner) { result ->
            val deleteSuccess = result.getOrDefault(false)
            Toast.makeText(
                activity,
                if (deleteSuccess) R.string.delete_success else R.string.delete_fail,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}