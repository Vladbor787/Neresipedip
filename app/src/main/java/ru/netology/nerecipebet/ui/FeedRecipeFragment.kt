package ru.netology.nerecipebet.ui
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_DRAG
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nerecipebet.R
import ru.netology.nerecipebet.adapter.RecipeAdapter
import ru.netology.nerecipebet.databinding.EmptyFieldBinding
import ru.netology.nerecipebet.viewModel.RecipeViewModel

class FeedRecipeFragment : Fragment()  {

    private val viewModel by activityViewModels<RecipeViewModel>()


    private val itemTouchHelper by lazy {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP
                    or ItemTouchHelper.DOWN
                    or ItemTouchHelper.START
                    or ItemTouchHelper.END,
            0
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val adapter = recyclerView.adapter as RecipeAdapter
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition

                adapter.moveItem(from, to)
                adapter.notifyItemMoved(from, to)

                viewModel.updateListOnMove(
                    adapter.getIndexFrom(from),
                    adapter.getIndexTo(to),
                    adapter.getIdFrom(from),
                    adapter.getIdTo(to)
                )
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            }

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                if (actionState == ACTION_STATE_DRAG) {
                    viewHolder?.itemView?.alpha = 0.5f
                }
            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)
                viewHolder.itemView.alpha = 1.0f
            }
        }

        ItemTouchHelper(simpleItemTouchCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.favoriteFragment.observe(this) {
            val direction = FeedRecipeFragmentDirections.favoriteFragment()
            findNavController().navigate(direction)
        }

        viewModel.updateRecipeFragment.observe(this) {
            val updatedRecipe = viewModel.updateRecipe.value
            val directions = FeedRecipeFragmentDirections.updateRecipeFragment(updatedRecipe)
            findNavController().navigate(directions)
        }

        viewModel.createFragment.observe(this) {
            val directions = FeedRecipeFragmentDirections.recipeCreateFragment()
            findNavController().navigate(directions)
        }

        viewModel.singleFragment.observe(this) {
            val viewRecipe = viewModel.singleRecipe.value
            val directions = FeedRecipeFragmentDirections.recipeViewFragment(viewRecipe)
            findNavController().navigate(directions)
        }

        viewModel.filterFragment.observe(this) {
            val directions = FeedRecipeFragmentDirections.recipeFilterFragment()
            findNavController().navigate(directions)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = EmptyFieldBinding.inflate(layoutInflater, container, false).also { binding ->
        itemTouchHelper.attachToRecyclerView(binding.listRecipe)
        val adapter = RecipeAdapter(viewModel)
        binding.listRecipe.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            val feedRecipe = recipes.isEmpty()
            if (recipes.isEmpty()) {
                binding.textBackground1.isVisible = feedRecipe
                binding.iconBackground1.isVisible = feedRecipe
                binding.textHint1.isVisible = feedRecipe
                binding.searchView.visibility = View.GONE
            }else{
                binding.textBackground1.visibility = View.GONE
                binding.iconBackground1.visibility = View.GONE
                binding.textHint1.visibility = View.GONE
                binding.searchView.visibility = View.VISIBLE
            }
            adapter.submitList(recipes)
        }

        if (viewModel.filterIsActive) {

            binding.buttonClearFilter.isVisible = viewModel.filterIsActive
            binding.textBackground1.setText(R.string.text_background2)
            binding.textHint1.setText(R.string.text_hint2)
            binding.buttonClearFilter.setOnClickListener {
                viewModel.clearFilter()
                viewModel.resetFilterCheckboxes()
                viewModel.filterIsActive = false
                binding.buttonClearFilter.visibility = View.GONE
                binding.textBackground1.visibility = View.GONE
                binding.iconBackground1.visibility = View.GONE
                binding.textHint1.visibility = View.GONE
                binding.searchView.visibility = View.VISIBLE
                viewModel.data.observe(viewLifecycleOwner) { recipe ->

                    adapter.submitList(recipe)
                }
            }
        } else {
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {

                    if (newText.isNotBlank()) {
                        viewModel.onSearchClicked(newText)
                        viewModel.data.observe(viewLifecycleOwner) { recipe ->
                            adapter.submitList(recipe)
                        }
                    }
                    if (TextUtils.isEmpty(newText)){
                        viewModel.clearFilter()
                        viewModel.data.observe(viewLifecycleOwner) { recipe ->
                            adapter.submitList(recipe)
                        }
                    }
                    return false
                }
            })
        }

        binding.bottomToolbar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorites -> {
                    viewModel.favoriteFragment.call()
                    true
                }
                R.id.filter -> {
                    viewModel.filterFragment.call()
                    true
                }
                else -> false
            }
        }

        binding.buttonAdd.setOnClickListener {
            viewModel.onCreateClicked()
        }

    }.root
}



