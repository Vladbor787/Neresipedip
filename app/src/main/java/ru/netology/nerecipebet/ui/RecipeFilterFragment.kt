package ru.netology.nerecipebet.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nerecipebet.R
import ru.netology.nerecipebet.databinding.FragmentFilterBinding
import ru.netology.nerecipebet.viewModel.RecipeViewModel

class RecipeFilterFragment : DialogFragment() {
    private val viewModel by activityViewModels<RecipeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentFilterBinding.inflate(layoutInflater, container, false).also { binding ->
        viewModel.clearFilter()
        binding.buttonApply.setOnClickListener {
            onApplyButtonClicked(binding)
        }
        viewModel.filterStateMutableLiveData.observe(viewLifecycleOwner) { filterState ->
            with(binding) {
                checkBoxBeef.isChecked = filterState.europeanIsActive
                checkBoxPork.isChecked = filterState.asianIsActive
                checkBoxLamb.isChecked = filterState.panasianIsActive
                checkBoxChicken.isChecked = filterState.easternIsActive
                checkBoxSeafood.isChecked = filterState.americanIsActive
                checkBoxPasta.isChecked = filterState.russianIsActive
                checkBoxDessert.isChecked = filterState.mediterraneanIsActive
            }
        }

    }.root

    private fun onApplyButtonClicked(binding: FragmentFilterBinding) {
        var checkedCount = 0
        val numberOfFilters = 7

        if (!binding.checkBoxBeef.isChecked) {
            ++checkedCount
            viewModel.showBeefCategory(getString(R.string.european_category))
        }
        if (!binding.checkBoxPork.isChecked) {
            ++checkedCount
            viewModel.showPorkCategory(getString(R.string.asian_category))

        }
        if (!binding.checkBoxLamb.isChecked) {
            ++checkedCount
            viewModel.showLambCategory(getString(R.string.panasian_category))

        }
        if (!binding.checkBoxChicken.isChecked) {
            ++checkedCount
            viewModel.showChickenCategory(getString(R.string.eastern_category))
        }
        if (!binding.checkBoxSeafood.isChecked) {
            ++checkedCount
            viewModel.showSeafoodCategory(getString(R.string.american_category))
        }
        if (!binding.checkBoxPasta.isChecked) {
            ++checkedCount
            viewModel.showPastaCategory(getString(R.string.russian_category))
        }
        if (!binding.checkBoxDessert.isChecked) {
            ++checkedCount
            viewModel.showDessertCategory(getString(R.string.miditerranean_category))
        }

        if (checkedCount == numberOfFilters) {
            Toast.makeText(activity, getString(R.string.filter_caution), Toast.LENGTH_LONG).show()
            return
        }
        if (checkedCount == 0) {
            viewModel.clearFilter()
            saveFilterState(binding)
        } else
            saveFilterState(binding)
        findNavController().popBackStack()
    }

    private fun saveFilterState(binding: FragmentFilterBinding) {
        viewModel.filterStateMutableLiveData.value?.europeanIsActive =
            binding.checkBoxBeef.isChecked
        viewModel.filterStateMutableLiveData.value?.asianIsActive =
            binding.checkBoxPork.isChecked
        viewModel.filterStateMutableLiveData.value?.panasianIsActive =
            binding.checkBoxLamb.isChecked
        viewModel.filterStateMutableLiveData.value?.easternIsActive =
            binding.checkBoxChicken.isChecked
        viewModel.filterStateMutableLiveData.value?.americanIsActive =
            binding.checkBoxSeafood.isChecked
        viewModel.filterStateMutableLiveData.value?.russianIsActive =
            binding.checkBoxPasta.isChecked
        viewModel.filterStateMutableLiveData.value?.mediterraneanIsActive =
            binding.checkBoxDessert.isChecked
    }
}
