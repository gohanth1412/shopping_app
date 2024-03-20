package com.example.shoppingapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingapp.model.AdversModel
import com.example.shoppingapp.model.CartModel
import com.example.shoppingapp.model.CategoryModel
import com.example.shoppingapp.model.FavouriteModel
import com.example.shoppingapp.model.MenuModel
import com.example.shoppingapp.model.ProductModel
import com.example.shoppingapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    var firebaseDb: FirebaseDatabase,
    var firebaseAuth: FirebaseAuth
) : ViewModel() {
    private var allProduct = MutableLiveData<List<ProductModel>>()
    var offerProduct = MutableLiveData<List<ProductModel>>()
    var sellingProduct = MutableLiveData<List<ProductModel>>()
    var woolenProduct = MutableLiveData<List<ProductModel>>()
    var itemProductChoose = ProductModel()
    private var currentUserId = firebaseAuth.currentUser!!.uid
    var allData = mutableListOf<ProductModel>()
    var favouriteProducts = MutableLiveData<List<ProductModel>>()
    var listFilterCategory = listOf<ProductModel>()
    var categoryName: String = ""
    var dataCart = MutableLiveData<List<CartModel>>()
    var currentUser = MutableLiveData<UserModel>()
    var allPrice = MutableLiveData<String>()

    init {
        loadAllProduct()
        loadFavouriteProduct()
        loadDataCart()
        loadCurrentUser()
    }

    private fun loadCurrentUser() {
        firebaseDb.getReference("users").child(currentUserId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(UserModel::class.java)
                    user?.let {
                        currentUser.value = it
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("232323", "$error")
                }

            })
    }

    private fun loadDataCart() {
        firebaseDb.getReference("carts").child(currentUserId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listCart = mutableListOf<CartModel>()

                    for (cart in snapshot.children) {
                        val myData = cart.getValue(CartModel::class.java)
                        myData?.let {
                            listCart.add(it)
                        }
                    }
                    dataCart.value = listCart

                    var total = 0f
                    for (i in listCart) {
                        total += i.totalPrice!!.toFloat()
                    }

                    allPrice.value = String.format("%.2f", total)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("232323", "$error")
                }

            })
    }

    private fun loadFavouriteProduct() {
        firebaseDb.getReference("favourites").child(currentUserId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listData = mutableListOf<FavouriteModel>()
                    val listFavourite = mutableListOf<ProductModel>()
                    for (product in snapshot.children) {
                        val myData = product.getValue(FavouriteModel::class.java)
                        myData?.let { listData.add(it) }
                    }

                    for (data in listData) {
                        for (product in allData) {
                            if (data.idProduct == product.idProduct) {
                                listFavourite.add(product)
                            }
                        }
                    }
                    favouriteProducts.value = listFavourite
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("232323", "$error")
                }

            })
    }

    val dataAdvers = listOf(
        AdversModel(R.drawable.advers, "Marriage Products", "Get Up To 40% OFF"),
        AdversModel(R.drawable.advers, "Marriage Products", "Get Up To 40% OFF"),
        AdversModel(R.drawable.advers, "Marriage Products", "Get Up To 40% OFF"),
    )

    val dataCategory = listOf(
        CategoryModel(R.drawable.chhab, "Chhab"),
        CategoryModel(R.drawable.hangings, "Hangings"),
        CategoryModel(R.drawable.kanha, "Kanha"),
        CategoryModel(R.drawable.marriage, "Marriage"),
        CategoryModel(R.drawable.toran, "Toran"),
        CategoryModel(R.drawable.woolen, "Woolen"),
    )

    val dataMenu = listOf(
        MenuModel(R.drawable.order_icon, "Orders"),
        MenuModel(R.drawable.detail_icon, "My Details"),
        MenuModel(R.drawable.address_icon, "Delivery Address"),
        MenuModel(R.drawable.help_icon, "Help"),
        MenuModel(R.drawable.about_icon, "About"),
    )

    private fun loadAllProduct() {
        firebaseDb.getReference("products")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (product in snapshot.children) {
                        val myData = product.getValue(ProductModel::class.java)
                        myData?.let { allData.add(it) }
                    }
                    allProduct.value = allData

                    offerProduct.value = allData.filter { it.offer == true }
                    sellingProduct.value = allData.filter { it.selling == true }
                    woolenProduct.value = allData.filter { it.category == "Woolen" }

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("232323", "$error")
                }

            })
    }

    fun addFavouriteProduct(product: ProductModel) {
        val idFavourite = "${product.idProduct}$currentUserId"
        firebaseDb.getReference("favourites").child(currentUserId).child(idFavourite)
            .setValue(FavouriteModel(idFavourite, currentUserId, product.idProduct))
    }

    fun deleteFavouriteProduct(product: ProductModel) {
        val idFavourite = "${product.idProduct}$currentUserId"
        firebaseDb.getReference("favourites").child(currentUserId).child(idFavourite).removeValue()
    }

    fun checkProductFavourite(): Boolean {
        val list = favouriteProducts.value
        list?.let {
            if (it.contains(itemProductChoose)) {
                return true
            }
        }
        return false
    }

    fun filterProductByCategory(category: String): List<ProductModel> {
        return allData.filter { it.category == category }
    }

    fun addCart(product: ProductModel, countProduct: Int, totalPrice: String) {
        val idCart = "${product.idProduct}$currentUserId"
        firebaseDb.getReference("carts").child(currentUserId).child(idCart)
            .setValue(
                CartModel(
                    idCart,
                    currentUserId,
                    product.idProduct,
                    countProduct,
                    product.productPrice,
                    totalPrice,
                    product.productName,
                    product.thumbnail
                )
            )
    }

    fun deleteCart(idProduct: String) {
        val idCart = "$idProduct$currentUserId"
        firebaseDb.getReference("carts").child(currentUserId).child(idCart).removeValue()
    }

    fun upDateCart(idCart: String, count: Int, totalPrice: String) {
        val updates = HashMap<String, Any>()
        updates["countProduct"] = count
        updates["totalPrice"] = totalPrice
        firebaseDb.getReference("carts").child(currentUserId).child(idCart).updateChildren(updates)
    }

    override fun onCleared() {
        super.onCleared()
    }
}