/*===================================================================
Author: Lionel Standridge
Due Date: 11/26/2023
CISC – 0668 Mobile Application Development
Description - Assignment 3
Instructor - Dr. Frank Mitropoulos
File: Assignment 3: LionelStandridge_CISC668_Assignment_3.zip
====================================================================

-- This assignment, is a 1:1 example to the java program in assignment 2.
However, this is completely written in Kotlin (I tried to recycle some
non-language assets such as strings, layout, images etc.)

-- I continued the support for the SQLite persistence (in hopes for extra
 credit) but converted that to Kotlin as well

-- I am very impressed with how much less code is required with Kotlin.  I
was able to trim down a few classes.

** Following are comments  from the Java app that still apply **

-- I added "Clear History" button to the order history page.  I know in
real life that would probably not make sense, however, I figured it was
a good way to clear the table for retesting etc.

-- I just used all string values for the table store.  This was for time sake.

-- When creating a new OrderItem, I just randomized an order number.  This was
just for demo purposes and would need to be fully unique in real life.

Looking forward to your feedback!
*/

package com.example.icecream_kotlin

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.icecream_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentOrderItem = OrderItem()
    private var orderItemManager = OrderItemManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // HotFudge SeekBar listener interface handlers
        binding.hotFudgeSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (progress > 0)
                    binding.hotFudgeAmountTextView.text = getString(R.string.ounces, progress)
                else
                    binding.hotFudgeAmountTextView.text = getString(R.string.none)

                currentOrderItem.hotFudgeOunzes = progress
                updatePrice()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Stubbed out for interface
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Stubbed out for interface
            }
        })

        // Flavor Spinner listener interface handlers
        binding.flavorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                    currentOrderItem.flavor = OrderItem.Flavor.values()[position]
                    updatePrice()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Stubbed out for interface
            }
        }

        // Size Spinner listener interface handlers
        binding.sizeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                    currentOrderItem.size = OrderItem.Size.values()[position]
                    updatePrice()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Stubbed out for interface
            }
        }

        binding.theWorksButton.setOnClickListener {
            binding.peanutsCheckBox.isChecked = true
            binding.almondsCheckBox.isChecked = true
            binding.strawberriesCheckBox.isChecked = true
            binding.gummyBearsCheckBox.isChecked = true
            binding.mAndMCheckBox.isChecked = true
            binding.browniesCheckBox.isChecked = true
            binding.oreosCheckBox.isChecked = true
            binding.marshmallowsCheckBox.isChecked = true
            binding.hotFudgeSeekBar.progress = 3
            binding.sizeSpinner.setSelection(OrderItem.Size.Large.ordinal)
            currentOrderItem.selectAllOptions()
            updatePrice()
        }

        binding.resetButton.setOnClickListener {
            resetAllValues()
        }

        binding.orderButton.setOnClickListener {
            try {
                orderItemManager.addOrderItem(currentOrderItem)
                resetAllValues()
                Toast.makeText(this@MainActivity, R.string.order_completed, Toast.LENGTH_SHORT).show()
            } catch (e: java.lang.Exception) {
                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater;
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_orderHistory -> {
                val intent = Intent(this@MainActivity, OrderHistoryActivity::class.java)
                intent.putExtra("OrderItems", orderItemManager.getOrderItems())
                startActivity(intent)
                return true
            }
            R.id.menu_about -> {
                val intent = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun resetAllValues() {
        binding.peanutsCheckBox.isChecked = false
        binding.almondsCheckBox.isChecked = false
        binding.strawberriesCheckBox.isChecked = false
        binding.gummyBearsCheckBox.isChecked = false
        binding.mAndMCheckBox.isChecked = false
        binding.browniesCheckBox.isChecked = false
        binding.oreosCheckBox.isChecked = false
        binding.marshmallowsCheckBox.isChecked = false
        binding.hotFudgeSeekBar.progress = 1
        binding.flavorSpinner.setSelection(OrderItem.Flavor.Vanilla.ordinal)
        binding.sizeSpinner.setSelection(OrderItem.Size.Small.ordinal)

        currentOrderItem = OrderItem()
        updatePrice()
    }

    private fun updatePrice() {
        try {
            binding.amountTextView.text = OrderItemUtilities.getFormattedOrderItemSum(currentOrderItem)
        } catch (e: Exception) {
            Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun handleChecked(view: View) {
        val checkBoxId = view.id

        when (checkBoxId) {
            R.id.almonds_checkBox -> { currentOrderItem.peanutsChecked = binding.almondsCheckBox.isChecked }
            R.id.brownies_checkBox -> { currentOrderItem.browniesChecked = binding.browniesCheckBox.isChecked }
            R.id.gummy_bears_checkBox -> { currentOrderItem.gummyBearsChecked = binding.gummyBearsCheckBox.isChecked }
            R.id.m_and_m_checkBox -> { currentOrderItem.mAndMsChecked = binding.mAndMCheckBox.isChecked }
            R.id.oreos_checkBox -> { currentOrderItem.oreosChecked = binding.oreosCheckBox.isChecked }
            R.id.marshmallows_checkBox -> { currentOrderItem.marshmallowsChecked = binding.marshmallowsCheckBox.isChecked }
            R.id.peanuts_checkBox -> { currentOrderItem.peanutsChecked = binding.peanutsCheckBox.isChecked }
            R.id.strawberries_checkBox -> { currentOrderItem.strawberriesChecked = binding.strawberriesCheckBox.isChecked }
        }

        updatePrice()
    }
}