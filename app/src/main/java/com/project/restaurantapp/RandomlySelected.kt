package com.project.restaurantapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.restaurantapp.databinding.RandomlySelectedBinding
import kotlinx.android.synthetic.main.randomly_selected.*

class RandomlySelected : Fragment() {

    private var _binding: RandomlySelectedBinding? = null
    private val binding get() = _binding!!
    var sharedPref : SharedPreferences? = null
    var selected_category: Category ?= null
    var editor : SharedPreferences.Editor? = null
    var random_restaurant: String ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = RandomlySelectedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        sharedPref = activity?.applicationContext?.getSharedPreferences("RESTAURANT_LIST", Context.MODE_PRIVATE)

        editor =sharedPref?.edit()

//        selected_category = arguments?.getSerializable("FAV_CATEGORY") as Category
        random_restaurant = arguments?.getSerializable("picked").toString()


        categoryName.text = "${random_restaurant} was randomly picked."

        selected_category?.let {
            for(i in 0 until it.restaurants.count()) {
                restaurnts.addView(generateUI(it.restaurants.get(i)))
            }
        }
    }

    fun generateUI(text: String): TextView{
            val lparams =
                LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

            lparams.setMargins(0, 8, 12, 8)

            val textView = TextView(context)
            textView.layoutParams = lparams
            textView.text = text
            textView.textSize = 18F
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            return textView
    }

}