package com.project.restaurantapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.restaurantapp.databinding.FragmentFirstBinding
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : Fragment() , RecyclerViewHomeClickListener{

    private var categoryList: List<Category> = mutableListOf()
    private var _binding: FragmentFirstBinding? = null
    var sharedPref : SharedPreferences? = null
    private val binding get() = _binding!!
    lateinit var restAdapter: RestAdapter
    private var selectedCategory: String = ""
    var editor : SharedPreferences.Editor? = null
// BUTTONS AND LIST FUNCTIONS AND TEXT BLURB
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = activity?.applicationContext?.getSharedPreferences("RESTAURANT_LIST", Context.MODE_PRIVATE)
        editor =sharedPref?.edit()

        categoryList = getList()

        if(categoryList.isNotEmpty()) {

            noCatAvailable.visibility = View.GONE
            recycler_view.visibility = View.VISIBLE

            restAdapter = RestAdapter(categoryList, this)

            _binding.apply {
                recycler_view.adapter = restAdapter
            }

        }else{
            noCatAvailable.visibility = View.VISIBLE
            recycler_view.visibility = View.GONE
        }

        binding.fab.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.clear.setOnClickListener {
            editor?.remove("LIST")?.commit()

            noCatAvailable.visibility = View.VISIBLE
            recycler_view.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getList(): List<Category>{
        val gson = Gson()
        val json = sharedPref?.getString("LIST",null) ?: return listOf()

        val type = object : TypeToken<MutableList<Category>>(){}.type//converting the json to list
        return gson.fromJson(json,type)//returning the list
    }

    override fun onResume() {
        super.onResume()
        selectedCategory = ""
    }
    override fun clickOnItem(data: Category, card: View) {
        selectedCategory = data.categoryName

        var random_restaurant = categoryList.find { it.categoryName == selectedCategory }?.restaurants?.random()

//        var fav_category = selectedCategory.random()

        Toast.makeText(card.context, random_restaurant, Toast.LENGTH_SHORT).show()

        val bundle = Bundle()
        bundle.putString("picked", random_restaurant)

 //       bundle.putSerializable ("FAV_CATEGORY", categoryList.find { it.categoryName ==  fav_category})

        Navigation.findNavController(card).navigate(R.id.action_FirstFragment_to_randomlySelected, bundle)
    }
}

