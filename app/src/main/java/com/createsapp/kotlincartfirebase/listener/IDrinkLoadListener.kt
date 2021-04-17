package com.createsapp.kotlincartfirebase.listener

import com.createsapp.kotlincartfirebase.model.DrinkModel

interface IDrinkLoadListener {
    fun onDrinkLoadSuccess(drinkModelList:List<DrinkModel>)
    fun onDrinkLoadFailed(message:String?)
}