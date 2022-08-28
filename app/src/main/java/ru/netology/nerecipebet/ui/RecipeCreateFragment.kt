package ru.netology.nerecipebet.ui


import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.constant.ImageProvider

import com.google.android.material.snackbar.Snackbar
import ru.netology.nerecipebet.R
import ru.netology.nerecipebet.databinding.FragmentCreateBinding
import ru.netology.nerecipebet.viewModel.RecipeViewModel


class RecipeCreateFragment : Fragment() {
    private val viewModel by activityViewModels<RecipeViewModel>()
    private var categoryRecipeNumber = ""

    private var fragmentBinding: FragmentCreateBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCreateBinding.inflate(layoutInflater, container, false).also { binding ->

        binding.buttonSave.setOnClickListener {
            onSaveButtonClicked(binding)
        }

        binding.categoryRecipeCheckBox.setOnCheckedChangeListener { _, id ->
            categoryRecipeNumber = catRec(id)
        }
        val pickPhotoLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                when (it.resultCode) {
                    ImagePicker.RESULT_ERROR -> {
                        Snackbar.make(
                            binding.root,
                            ImagePicker.getError(it.data),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                 Activity.RESULT_OK -> viewModel.changePhoto(it.data?.data)

                }
            }

        binding.takePhoto.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(2048)
                .provider(ImageProvider.CAMERA)
                .createIntent(pickPhotoLauncher::launch)
        }
        binding.pickPhoto.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(2048)
                .provider(ImageProvider.GALLERY)
                .galleryMimeTypes(
                    arrayOf(
                        "image/png",
                        "image/jpeg",
                    )
                )
                .createIntent(pickPhotoLauncher::launch)
        }
        viewModel.photo.observe(viewLifecycleOwner) {
            if (it.uri == null) {
                binding.recipeView.setText("")
                return@observe
            }
            binding.recipeView.setText(it.uri.toString())
        }

        binding.buttonSave.setOnClickListener {
            onSaveButtonClicked(binding)
        }
    }.root

    private fun onSaveButtonClicked(binding: FragmentCreateBinding) {

        val title = binding.title.text.toString()
        val authorName = binding.authorName.text.toString()
        val textRecipe = binding.textRecipe.text.toString()
        val image = binding.recipeView.text.toString()


        if (!emptyCheckUpdateWarning(
                title = title, authorName = authorName, imageView = image,
                textRecipe = textRecipe)) return

        viewModel.onSaveClicked(title = title, authorName = authorName, recipeImage =  image, categoryRecipe = categoryRecipeNumber, textRecipe = textRecipe)

        findNavController().popBackStack()
    }

    private fun emptyCheckUpdateWarning(
        title: String,
        authorName: String,
        imageView: String,
        textRecipe: String,

        ): Boolean {
        return if (title.isBlank() || authorName.isBlank() || imageView.isBlank()|| textRecipe.isBlank() ) {
            Toast.makeText(activity, getString(R.string.caution_filling), Toast.LENGTH_LONG).show()
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
//    private val startForProfileImageResult =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
//            val resultCode = result.resultCode
//            val data = result.data
//
//            if (resultCode == Activity.RESULT_OK) {
//                //Image Uri will not be null for RESULT_OK
//                val fileUri = data?.data!!
//
//                mProfileUri = fileUri
//                imgProfile.setImageURI(fileUri)
//            } else if (resultCode == ImagePicker.RESULT_ERROR) {
//                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
//            }
//        }
override fun onDestroyView() {
    fragmentBinding = null
    super.onDestroyView()
}
}