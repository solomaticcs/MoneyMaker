package com.solomaticydl.moneymaker.ui.addition

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.findNavController
import com.solomaticydl.moneymaker.R
import com.solomaticydl.moneymaker.databinding.FragmentAdditionBinding
import com.solomaticydl.moneymaker.db.entity.Record
import com.solomaticydl.moneymaker.db.entity.RecordCategory
import com.solomaticydl.moneymaker.db.entity.text
import com.solomaticydl.moneymaker.ui.base.BaseFragment
import com.solomaticydl.moneymaker.ui.view.DatePickerFragment
import com.solomaticydl.moneymaker.utils.TimeUtil
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class AdditionFragment :
    BaseFragment<FragmentAdditionBinding>(FragmentAdditionBinding::inflate) {

    private val viewModel: AdditionViewModel by viewModel()

    private val datePicker: DatePickerFragment by lazy {
        DatePickerFragment().apply {
            dateSetCallback = this@AdditionFragment.dateSetCallback
        }
    }

    private val dateSetCallback: (year: Int, month: Int, dayOfMonth: Int) -> Unit =
        { year, month, dayOfMonth ->
            binding.tvDate.text = TimeUtil.dateToDateString(year, month, dayOfMonth)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initDate()
        initCategorySpinner()
        initSubmit()
        initObservers(view)
    }

    private fun initDate() {
        val currentTime = System.currentTimeMillis()
        binding.tvDate.text = TimeUtil.timestampToDateString(currentTime)
        binding.layoutDate.setOnClickListener {
            activity?.supportFragmentManager?.let {
                datePicker.show(it, TAG)
            }
        }
    }

    private fun initCategorySpinner() {
        binding.tvCategory.setText(RecordCategory.values().first().text())
        binding.tvCategory.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                RecordCategory.values().map { recordCategory ->
                    recordCategory.text()
                }
            )
        )
    }

    private fun initSubmit() {
        binding.btnSubmit.setOnClickListener {
            val moneyStr = binding.etMoney.text.toString()
            if (moneyStr.isEmpty()) {
                Toast.makeText(activity, R.string.addition_message_money_empty, Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            val money = moneyStr.toDouble()
            val description = binding.etDescription.text.toString()
            val dateTimestamp = TimeUtil.dateStringToTimestamp(binding.tvDate.text.toString())
            val recordCategory =
                RecordCategory.valueOf(binding.tvCategory.text.toString().uppercase())
            viewModel.addRecord(Record(money, description, dateTimestamp, recordCategory))
        }
    }

    private fun initObservers(view: View) {
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            // TODO: show loading bar
        }
        viewModel.addDataResultLiveData.observe(viewLifecycleOwner) { result ->
            val success = result.getOrDefault(false)
            if (success) {
                Toast.makeText(activity, R.string.addition_message_add_success, Toast.LENGTH_SHORT)
                    .show()
                view.findNavController().popBackStack()
            } else {
                Toast.makeText(activity, R.string.addition_message_add_fail, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    companion object {
        private const val TAG = "AdditionFragment"
    }
}