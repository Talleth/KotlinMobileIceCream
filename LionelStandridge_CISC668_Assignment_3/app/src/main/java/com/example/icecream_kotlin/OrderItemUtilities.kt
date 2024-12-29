package com.example.icecream_kotlin

import java.text.NumberFormat

object OrderItemUtilities {
    fun getOrderItemSum(orderItem: OrderItem): Double {
        var currentUnformattedTotal = 0.0

        if (orderItem.almondsChecked) { currentUnformattedTotal += Prices.ALMONDS }
        if (orderItem.browniesChecked) { currentUnformattedTotal += Prices.BROWNIE }
        if (orderItem.gummyBearsChecked) { currentUnformattedTotal += Prices.GUMMY_BEARS }
        if (orderItem.mAndMsChecked) { currentUnformattedTotal += Prices.M_AND_M }
        if (orderItem.marshmallowsChecked) { currentUnformattedTotal += +Prices.MARSHMALLOWS }
        if (orderItem.oreosChecked) { currentUnformattedTotal += +Prices.OREOS }
        if (orderItem.peanutsChecked) { currentUnformattedTotal += Prices.PEANUTS }
        if (orderItem.strawberriesChecked) { currentUnformattedTotal += Prices.STRAWBERRIES }

        when (orderItem.hotFudgeOunzes) {
            1 -> { currentUnformattedTotal += Prices.HOT_FUDGE_ONE_OUNCE }
            2 -> { currentUnformattedTotal += Prices.HOT_FUDGE_TWO_OUNCES }
            3 -> { currentUnformattedTotal += Prices.HOT_FUDGE_THREE_OUNCES }
        }
        when (orderItem.size) {
            OrderItem.Size.Small -> { currentUnformattedTotal += Prices.SIZE_SMALL }
            OrderItem.Size.Medium -> { currentUnformattedTotal += Prices.SIZE_MEDIUM }
            OrderItem.Size.Large -> { currentUnformattedTotal += Prices.SIZE_LARGE }
        }

        return currentUnformattedTotal
    }

    fun getFormattedOrderItemSum(orderItem: OrderItem): String {
        val fmt = NumberFormat.getCurrencyInstance()
        return fmt.format(getOrderItemSum(orderItem))
    }
}