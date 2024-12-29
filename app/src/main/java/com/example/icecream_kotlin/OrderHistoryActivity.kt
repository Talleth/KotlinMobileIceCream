package com.example.icecream_kotlin

import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.icecream_kotlin.databinding.ActivityOrderHistoryBinding

class OrderHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderHistoryBinding
    private var orderItems = ArrayList<OrderItem>()
    private var orderItemStrings = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        this.title = getString(R.string.order_history);

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Hydrate orderItems then orderItem strings
        orderItems = intent.getSerializableExtra("OrderItems") as ArrayList<OrderItem>
        for (orderItem in orderItems)
            orderItemStrings.add(orderItem.toString())

        // Create the adapter and update the list control
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, orderItemStrings)
        binding.orderListView.adapter = adapter

        // Tool to reset orders on the database
        binding.clearButton.setOnClickListener {
            try {
                val orderDatabase = OrderDatabase(this)
                orderDatabase.ClearData()
                binding.orderListView.adapter = null
                Toast.makeText(this@OrderHistoryActivity, R.string.items_cleared, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@OrderHistoryActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}