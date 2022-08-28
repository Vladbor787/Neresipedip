package ru.netology.nerecipebet.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nerecipebet.R
import ru.netology.nerecipebet.databinding.FragmentUpdateBinding
import ru.netology.nerecipebet.viewModel.RecipeViewModel

class RecipeUpdateFragment : Fragment() {

    private val viewModel by activityViewModels<RecipeViewModel>()
    private val args by navArgs<RecipeUpdateFragmentArgs>()
    private var categoryRecipeNumber = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentUpdateBinding.inflate(layoutInflater, container, false).also { binding ->
        incomingArg(binding)

        binding.categoryRecipeCheckBox.setOnCheckedChangeListener { _, id ->
            categoryRecipeNumber = catRec(id)
        }

        binding.buttonSave.setOnClickListener {
            onSaveButtonClicked(binding)
        }
    }.root

    private fun onSaveButtonClicked(binding: FragmentUpdateBinding) {

        val id = args.idRecipe!!.id
        val title = binding.title.text.toString()
        val authorName = binding.authorName.text.toString()
        val textRecipe = binding.textRecipe.text.toString()
        val image = binding.recipeImageView.text.toString()


        if (!emptyCheckUpdateWarning(title = title,
                authorName = authorName,
                textRecipe = textRecipe,
                imageView = image,
                categoryRecipe = categoryRecipeNumber)) return

        viewModel.updateContent(
            id = id,
            title = title,
            authorName = authorName,
            recipeImage = image,
            textRecipe = textRecipe,
            categoryRecipe = categoryRecipeNumber
        )
        findNavController().popBackStack()
    }

    private fun incomingArg(binding: FragmentUpdateBinding){
        binding.title.setText(args.idRecipe?.title)
        binding.authorName.setText(args.idRecipe?.authorName)
        binding.textRecipe.setText(args.idRecipe?.textRecipe)
        binding.recipeImageView.setText(args.idRecipe?.recipeImage)

    }

    private fun emptyCheckUpdateWarning(
        title: String,
        authorName: String,
        imageView: String,
        textRecipe: String,
        categoryRecipe: String
    ): Boolean {
        return if (title.isBlank() || authorName.isBlank() || imageView.isBlank()|| textRecipe.isBlank() || categoryRecipe.isBlank()) {
            Toast.makeText(activity, "All fields must be filled in", Toast.LENGTH_LONG).show()
            false
        } else true
    }
    private fun catRec(ide: Int): String {
        return when (ide) {
            R.id.checkBoxBeef -> getString(R.string.european_category)
            R.id.checkBoxPork -> getString(R.string.asian_category)
            R.id.checkBoxLamb -> getString(R.string.panasian_category)
            R.id.checkBoxChicken -> getString(R.string.eastern_category)
            R.id.checkBoxSeafood -> getString(R.string.american_category)
            R.id.checkBoxPasta -> getString(R.string.european_category)
            R.id.checkBoxDessert -> getString(R.string.miditerranean_category)
            else -> {
                ""
            }
        }
    }
}