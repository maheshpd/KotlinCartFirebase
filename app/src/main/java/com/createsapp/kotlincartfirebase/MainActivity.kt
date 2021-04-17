package com.createsapp.kotlincartfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.createsapp.kotlincartfirebase.adapter.MyDrinkAdapter
import com.createsapp.kotlincartfirebase.listener.IDrinkLoadListener
import com.createsapp.kotlincartfirebase.model.DrinkModel
import com.createsapp.kotlincartfirebase.utils.SpaceItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), IDrinkLoadListener {

    //mahesh223prasad@gmail.com/FirebaseTutorial

    lateinit var drinkLoadListener: IDrinkLoadListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        loadDrinkFromFirebase()
    }

    private fun loadDrinkFromFirebase() {
        val drinkModels : MutableList<DrinkModel> = ArrayList()
        FirebaseDatabase.getInstance()
            .getReference("Drink")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists())
                    {
                        for (drinkSnapshot in snapshot.children)
                        {
                            val drinkModel = drinkSnapshot.getValue(DrinkModel::class.java)
                            drinkModel!!.key = drinkSnapshot.key
                            drinkModels.add(drinkModel)
                        }

                        drinkLoadListener.onDrinkLoadSuccess(drinkModels)

                    } else {
                        drinkLoadListener.onDrinkLoadFailed("Drink items not exists")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    drinkLoadListener.onDrinkLoadFailed(error.message)
                }

            })
    }

    private fun init() {
        drinkLoadListener = this

        val gridLayoutManager = GridLayoutManager(this,2)
        recycler_drink.layoutManager = gridLayoutManager
        recycler_drink.addItemDecoration(SpaceItemDecoration())

    }

    override fun onDrinkLoadSuccess(drinkModelList: List<DrinkModel>) {
        val adapter = MyDrinkAdapter(this,drinkModelList)
        recycler_drink.adapter = adapter
    }

    override fun onDrinkLoadFailed(message: String?) {
       Snackbar.make(mainLayout, message!!, Snackbar.LENGTH_LONG).show()
    }

}