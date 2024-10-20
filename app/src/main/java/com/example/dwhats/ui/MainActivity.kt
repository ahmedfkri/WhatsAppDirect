package com.example.dwhats.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dwhats.R
import com.example.dwhats.databinding.ActivityMainBinding
import com.example.dwhats.local.NumbersDatabase
import com.example.dwhats.ui.adapter.NumbersAdapter
import com.example.dwhats.ui.view_model.MainViewModel
import com.example.dwhats.ui.view_model.MainViewModelFactory
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var numbersAdapter: NumbersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val database = NumbersDatabase.getInstance(this)
        val factory = MainViewModelFactory(database)
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        setupRecyclerView()
        getNumbers()
        setupSwipeToDelete()
        onMessageClicked()
        onNumberClicked()

    }

    private fun onNumberClicked() {
        numbersAdapter.onItemClick = {
            messageNumber(it.num)
        }
    }

    private fun setupSwipeToDelete() {
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                mainViewModel.deleteNumber(numbersAdapter.differ.currentList[position])
                numbersAdapter.notifyItemRemoved(position)
                showSnackBar(position)
            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvRecent)
    }

    private fun showSnackBar(position: Int) {
        Snackbar.make(binding.root, "Deleted", Snackbar.LENGTH_SHORT).apply {
            setAction("Undo") {
                saveNumber(numbersAdapter.differ.currentList[position].num)
            }
            show()
        }
    }

    private fun onMessageClicked() {
        binding.btnMessage.setOnClickListener {
            val number = binding.edtNumber.text.toString().trim()
            if (number.isNotEmpty()) {
                saveNumber(number)
                binding.edtNumber.text?.clear()
                messageNumber(number)
            }
        }
    }

    private fun saveNumber(number: String) {
        mainViewModel.insertNumber(number)
        getNumbers()
    }

    private fun getNumbers() {
        mainViewModel.numbers.observe(this) {
            numbersAdapter.differ.submitList(it)
        }
    }

    private fun setupRecyclerView() {
        numbersAdapter = NumbersAdapter()
        binding.rvRecent.apply {
            adapter = numbersAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun messageNumber(number: String) {
        if (mainViewModel.hasCode(number)) {
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("http://api.whatsapp.com/send?phone=$number&text= ")
                startActivity(this)
            }
        } else {
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("http://api.whatsapp.com/send?phone=+2$number&text= ")
                startActivity(this)
            }
        }
    }


}