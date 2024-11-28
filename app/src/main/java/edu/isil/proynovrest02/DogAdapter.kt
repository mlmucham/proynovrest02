package edu.isil.proynovrest02

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DogAdapter(private val images: List<String>) : RecyclerView.Adapter<DogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        // Infla el diseño del elemento de la lista desde el archivo XML item_dog.
        return DogViewHolder(layoutInflater.inflate(R.layout.item_dog, parent, false))
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        // Obtiene la URL de la imagen para la posición actual.
        val item = images[position]
        // Llama al método bind del ViewHolder para establecer la imagen.
        holder.bind(item)
    }

}