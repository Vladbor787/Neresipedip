package ru.netology.nerecipebet.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import ru.netology.nerecipebet.R
import ru.netology.nerecipebet.databinding.FragmentViewRecipeBinding




class RecipeViewFragment : Fragment() {
    private val args by navArgs<RecipeViewFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentViewRecipeBinding.inflate(layoutInflater, container, false).also { binding ->

        incomingArg(binding)

        binding.bottomToolbar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.feed -> findNavController().popBackStack()
            }
            true
        }
    }.root

    private fun incomingArg (binding: FragmentViewRecipeBinding) {
        binding.title.text = args.viewRecipe?.title.toString()
        binding.authorName.text = args.viewRecipe?.authorName.toString()
        binding.categoryRecipe.text = args.viewRecipe?.categoryRecipe.toString()
        binding.textRecipe.text = args.viewRecipe?.textRecipe.toString()
        val uri = args.viewRecipe?.recipeImage.toString()
        Picasso.get().load(uri)
            .error(R.drawable.ic_error_placeholder)
            .into(binding.recipeImageView)
    }
}
