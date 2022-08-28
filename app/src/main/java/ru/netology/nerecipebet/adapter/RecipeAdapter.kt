package ru.netology.nerecipebet.adapter



import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.netology.nerecipebet.R
import ru.netology.nerecipebet.databinding.RecipeBinding
import ru.netology.nerecipebet.dto.Recipe


class RecipeAdapter(
    private val interactionListener: RecipeInteractionListener,
) : ListAdapter<Recipe, RecipeAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        currentList[position]
        holder.bind(getItem(position))
    }

//        override fun getItemCount(): Int {
//        return arrayItems.size
//    }
    inner class ViewHolder(
        private val binding: RecipeBinding,
        listener: RecipeInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var recipe: Recipe

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.menuOptions).apply {
                inflate(R.menu.option_menu)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(recipe)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(recipe)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        fun bind(recipe: Recipe) {
            this.recipe = recipe
            with(binding) {
                title.text = recipe.title
                authorName.text = recipe.authorName
                val uriPath = recipe.recipeImage
                if (recipe.recipeImage.isEmpty())
                    recipeImageView.setImageResource(R.drawable.ic_error_placeholder) else
                    Picasso.get().load(uriPath)
                        .error(R.drawable.ic_error_placeholder)
                        .into(recipeImageView)
                categoryRecipe.text = recipe.categoryRecipe
                textRecipe.text = recipe.textRecipe
                buttonFavorite.setImageResource(getFavoriteIconResId(recipe.isFavorite))
                buttonFavorite.setOnClickListener {
                    interactionListener.onFavoriteClicked(recipe.id)
                }
                recipeImageView.setOnClickListener {
                    interactionListener.onSingleRecipeClicked(recipe)
                }
                title.setOnClickListener {
                    interactionListener.onSingleRecipeClicked(recipe)
                }
                textRecipe.setOnClickListener {
                    interactionListener.onSingleRecipeClicked(recipe)
                }
                authorName.setOnClickListener {
                    interactionListener.onSingleRecipeClicked(recipe)
                }
                categoryRecipe.setOnClickListener {
                    interactionListener.onSingleRecipeClicked(recipe)
                }
                menuOptions.setOnClickListener {
                    popupMenu.show()
                }
            }
        }

        @DrawableRes
        private fun getFavoriteIconResId(liked: Boolean) =
            if (liked) R.drawable.icon_is_favorites else R.drawable.icon_is_not_favorites
    }


    private object DiffCallback : DiffUtil.ItemCallback<Recipe>() {

        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
            oldItem == newItem
    }

    fun moveItem(from: Int, to: Int) {
        val list = currentList.toMutableList()
        val fromLocation = list[from]
        list.removeAt(from)
        if (to < from) {
            list.add(to + 1, fromLocation)
        } else {
            list.add(to - 1, fromLocation)
        }

    }

    fun getIndexFrom(from: Int): Long {
        return currentList.toMutableList()[from].indexPosition
    }

    fun getIdFrom(from: Int): Long {
        return currentList.toMutableList()[from].id
    }

    fun getIndexTo(to: Int): Long {
        return currentList.toMutableList()[to].indexPosition
    }

    fun getIdTo(to: Int): Long {
        return currentList.toMutableList()[to].id
    }

}