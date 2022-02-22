package com.example.cupcakeorder.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private const val PRICE_PER_CUPCAKE = 2.00
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

class OrderViewModel: ViewModel() {

    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> get() = _quantity

    private val _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> get() = _flavor

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> get() = _date

    private val _price = MutableLiveData<Double>()
    //Transformar el precio a formato decimal con Transformations.map
    val price: LiveData<String> = Transformations.map(_price) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    val dateOptions = getPickupOptions()

    fun setQuantity( numberCupcakes: Int) {
        _quantity.value = numberCupcakes
        updatePrice()
    }

    fun setFlavor( desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }

    fun setDate( pickupDate: String) {
        _date.value = pickupDate
        updatePrice()
    }

    //Se configuro o no el sabor del pedido?
    fun hasNoFlavorSet(): Boolean {
        return _flavor.value.isNullOrEmpty()
    }

    //Fechas de retiro
    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance() //Contiene la fecha y hora actual
        //Repitiendo por que necesito 4 fechas
        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }

    //ResetOrder
    fun resetOrder() {
        _quantity.value = 0
        _flavor.value = ""
        _date.value = dateOptions[0]
        _price.value = 0.0
    }

    init {
        resetOrder()
    }

    private fun updatePrice() {
        var calculatedPrice = (quantity.value ?: 0) * PRICE_PER_CUPCAKE
        //IF the user selected the first option (today) for pickup, add the surcharge
        if ( dateOptions[0] == _date.value) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        _price.value = calculatedPrice
    }
}