package com.example.icecream_kotlin

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.time.LocalDateTime

class OrderDatabase(private val _context: Context) : SQLiteOpenHelper(_context, DATABASE_NAME, null, DATABASE_VERSION) {
    fun addOrderItem(orderItem: OrderItem) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("OrderItemId", orderItem.orderId)
        values.put("OrderDateTime", orderItem.orderDateTime.toString())
        values.put("BrowniesChecked", orderItem.browniesChecked)
        values.put("AlmondsChecked", orderItem.almondsChecked)
        values.put("MandMsChecked", orderItem.mAndMsChecked)
        values.put("MarshmallowsChecked", orderItem.marshmallowsChecked)
        values.put("OreosChecked", orderItem.oreosChecked)
        values.put("PeanutsChecked", orderItem.peanutsChecked)
        values.put("StrawberriesChecked", orderItem.strawberriesChecked)
        values.put("GummyBearsChecked", orderItem.gummyBearsChecked)
        values.put("HotFudgeOunces", orderItem.hotFudgeOunzes)
        values.put("Flavor", orderItem.flavor.ordinal)
        values.put("Size", orderItem.size.ordinal)
        db.insert("order_items", null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getAllItems(): ArrayList<OrderItem> {
            val orderItems = ArrayList<OrderItem>()
            val selectQuery = "SELECT * FROM order_items"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) do {
                val orderItem = OrderItem()
                orderItem.orderId = cursor.getInt(cursor.getColumnIndex("OrderItemId")).toLong()
                orderItem.orderDateTime = LocalDateTime.parse(cursor.getString(cursor.getColumnIndex("OrderDateTime")))
                orderItem.size = OrderItem.Size.values()[cursor.getInt(cursor.getColumnIndex("Size"))]
                orderItem.flavor = OrderItem.Flavor.values()[cursor.getInt(cursor.getColumnIndex("Flavor"))]
                orderItem.oreosChecked = cursor.getInt(cursor.getColumnIndex("OreosChecked")) == 1
                orderItem.marshmallowsChecked = cursor.getInt(cursor.getColumnIndex("MarshmallowsChecked")) == 1
                orderItem.browniesChecked = cursor.getInt(cursor.getColumnIndex("BrowniesChecked")) == 1
                orderItem.mAndMsChecked = cursor.getInt(cursor.getColumnIndex("MandMsChecked")) == 1
                orderItem.gummyBearsChecked = cursor.getInt(cursor.getColumnIndex("GummyBearsChecked")) == 1
                orderItem.strawberriesChecked = cursor.getInt(cursor.getColumnIndex("StrawberriesChecked")) == 1
                orderItem.peanutsChecked = cursor.getInt(cursor.getColumnIndex("PeanutsChecked")) == 1
                orderItem.almondsChecked = cursor.getInt(cursor.getColumnIndex("AlmondsChecked")) == 1
                orderItem.hotFudgeOunzes = cursor.getInt(cursor.getColumnIndex("HotFudgeOunces"))
                orderItems.add(orderItem)
            } while (cursor.moveToNext())

            cursor.close()
            db.close()

            // return item list
            return orderItems
        }

    fun ClearData() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM order_items")
        db.execSQL("VACUUM")
        db.close()
    }

    override fun onCreate(db: SQLiteDatabase) {
        try {
            db.execSQL(CREATE_TABLE_ITEMS)
        } catch (e: Exception) {
            Toast.makeText(_context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    // Constants used for the database class
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "OrderItems.db"
        private const val CREATE_TABLE_ITEMS = ("CREATE TABLE order_items ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "OrderItemId TEXT,"
                + "OrderDateTime TEXT,"
                + "BrowniesChecked TEXT,"
                + "AlmondsChecked TEXT,"
                + "MandMsChecked TEXT,"
                + "MarshmallowsChecked TEXT,"
                + "OreosChecked TEXT,"
                + "PeanutsChecked TEXT,"
                + "StrawberriesChecked TEXT,"
                + "GummyBearsChecked TEXT,"
                + "HotFudgeOunces TEXT,"
                + "Flavor TEXT,"
                + "Size TEXT)")
    }
}