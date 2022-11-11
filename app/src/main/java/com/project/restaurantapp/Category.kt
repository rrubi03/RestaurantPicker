package com.project.restaurantapp

import android.os.Parcelable
import java.io.Serializable

data class Category(var categoryName: String, var restaurants: List<String>): Serializable
