package com.example.icecream_kotlin

import android.content.Context
import android.widget.Toast

class OrderItemManager(private val _context: Context) {
    fun addOrderItem(orderItem: OrderItem) {
        try {
            val orderDatabase = OrderDatabase(_context)
            orderDatabase.addOrderItem(orderItem)
        } catch (e: Exception) {
            Toast.makeText(_context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun getOrderItems(): ArrayList<OrderItem> {
        val result: ArrayList<OrderItem>
        val orderDatabase = OrderDatabase(_context)
        result = orderDatabase.getAllItems()
        return result
    }
}