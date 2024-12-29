package com.example.icecream_kotlin

import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Random

class OrderItem : Serializable {
    // Ensure rand is random between instances (Singleton)
    companion object { private val rand = Random() }

    var orderId: Long = rand.nextInt(1000000).toLong()
    var orderDateTime: LocalDateTime = LocalDateTime.now()
    var peanutsChecked: Boolean = false
    var almondsChecked: Boolean = false
    var strawberriesChecked: Boolean = false
    var gummyBearsChecked: Boolean = false
    var mAndMsChecked: Boolean = false
    var browniesChecked: Boolean = false
    var oreosChecked: Boolean = false
    var marshmallowsChecked: Boolean = false
    var hotFudgeOunzes: Int = 1
    var flavor: Flavor = Flavor.Vanilla
    var size: Size = Size.Small

    enum class Flavor {
        Vanilla, Chocolate, Strawberry
    }

    enum class Size {
        Small, Medium, Large
    }

    fun selectAllOptions() {
        peanutsChecked = true
        almondsChecked = true
        strawberriesChecked = true
        gummyBearsChecked = true
        mAndMsChecked = true
        browniesChecked = true
        oreosChecked = true
        marshmallowsChecked = true
        size = Size.Large
        hotFudgeOunzes = 3
    }

    override fun toString(): String {
        var result = "\n"

        result += "Order Id: $orderId\n"
        result += "Date: ${orderDateTime.format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a"))}\n"
        result += "Flavor: ${flavor.toString()}\n"
        result += "Size: ${size.toString()}\n"
        result += "Cost: " + OrderItemUtilities.getFormattedOrderItemSum(this) + "\n"

        return result
    }
}