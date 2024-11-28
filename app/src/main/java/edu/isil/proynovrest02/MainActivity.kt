package edu.isil.proynovrest02

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import edu.isil.proynovrest02.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() , SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: DogAdapter
    private val dogImages = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflar el layout utilizando ViewBinding.
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Establecer el contenido de la actividad con la raíz de la vista vinculada.
        setContentView(binding.root)
        // Configurar el SearchView para manejar consultas de búsqueda.
        binding.svDogs.setOnQueryTextListener(this)
        // Inicializar el RecyclerView.
        initRecyclerView()
    }

    // Método para inicializar el RecyclerView.
    private fun initRecyclerView() {
        // Inicializar el adaptador con la lista de imágenes.
        adapter = DogAdapter(dogImages)
        // Configurar el RecyclerView con un LinearLayoutManager.
        binding.rvDogs.layoutManager = LinearLayoutManager(this)
        // Asignar el adaptador al RecyclerView.
        binding.rvDogs.adapter = adapter
    }

    // Método para configurar Retrofit.
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/") // URL base para la API de perros.
            .addConverterFactory(GsonConverterFactory.create()) // Añadir convertidor Gson.
            .build()
    }

    // Manejar el envío de una consulta de búsqueda.
    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            searchByName(query.toLowerCase()) // Buscar imágenes por nombre de raza.
        }
        return true
    }

    // Manejar cambios en el texto de búsqueda.
    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    // Método para buscar imágenes de perros por nombre de raza.
    private fun searchByName(query: String) {
        // Lanzar una coroutine en el contexto de IO.
        CoroutineScope(Dispatchers.IO).launch {
            // Realizar la solicitud a la API.
            val call = getRetrofit().create(APIService::class.java).getDogsByBreeds("$query/images")
            val puppies = call.body()
            // Actualizar la UI en el hilo principal.
            runOnUiThread {
                if (call.isSuccessful) {
                    // Obtener la lista de imágenes de la respuesta.
                    val images = puppies?.images ?: emptyList()
                    // Limpiar la lista actual y añadir las nuevas imágenes.
                    dogImages.clear()
                    dogImages.addAll(images)
                    // Notificar al adaptador que los datos han cambiado.
                    adapter.notifyDataSetChanged()
                } else {
                    // Mostrar mensaje de error si la respuesta no es exitosa.
                    showError()
                }
            }
        }
    }

    // Método para mostrar un mensaje de error.
    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }




}