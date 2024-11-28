package edu.isil.proynovrest02

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.isil.proynovrest02.databinding.ItemDogBinding

class DogViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemDogBinding.bind(view)

    fun bind(image: String) {
        // Utiliza Picasso para cargar la imagen desde la URL en el ImageView.
        Picasso.get().load(image).into(binding.ivDog)
    }

}