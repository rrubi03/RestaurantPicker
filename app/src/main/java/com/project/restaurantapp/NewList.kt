package com.project.restaurantapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.restaurantapp.databinding.FragmentNewListBinding
import kotlinx.android.synthetic.main.fragment_new_list.*


class NewList : Fragment() {

    private var _binding: FragmentNewListBinding? = null
    private val binding get() = _binding!!
    private var categoryList: MutableList<Category> = mutableListOf()
    var sharedPref : SharedPreferences? = null
    var editor : SharedPreferences.Editor? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNewListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        sharedPref = activity?.applicationContext?.getSharedPreferences("RESTAURANT_LIST", Context.MODE_PRIVATE)
        editor =sharedPref?.edit()

        categoryList = getList()

        add.setOnClickListener {
            container.addView(addANewRestaurant())
        }

        saveList.setOnClickListener {

            val restList: MutableList<String> = mutableListOf()

            restList.add(textin.text.toString())

            for (i in 0 until container.childCount) {
                val view: View = container.getChildAt(i)
                restList.add((view as EditText).text.toString())
            }

            categoryList.add(Category(categoryTxt.text.toString(), restList))

            setLists(categoryList)

            Navigation.findNavController(it).navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addANewRestaurant(): EditText {
        val lparams =
            LayoutParams(textin.width,  textin.height)

        lparams.setMargins(0, 8, 12, 8)

        val editText = EditText(context)
        editText.layoutParams = lparams
        editText.hint = "Add new restaurant"
        return editText
    }


    fun setLists(list:MutableList<Category>){
        val gson = Gson()
        val json = gson.toJson(list)//converting list to Json
        editor?.putString("LIST",json)
        editor?.commit()
    }

    fun getList(): MutableList<Category>{
        val gson = Gson()
        val json = sharedPref?.getString("LIST",null) ?: return mutableListOf()

        val type = object : TypeToken<MutableList<Category>>(){}.type//converting the json to list
        return gson.fromJson(json,type)//returning the list
    }
    //getting the list from shared preference

}
