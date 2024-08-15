package com.example.test

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private val viewModel: CoinViewModel by viewModels {
        CoinViewModelFactory((application as App).repo)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        val textView = findViewById<TextView>(R.id.textView)

        //val viewModel = ViewModelProvider(this).get(CoinViewModel::class.java)
        button.setOnClickListener {
            viewModel.getRate()
            viewModel.coinState.observe(this) { coinState ->
                when (coinState){
                    is CoinViewModel.CoinState.NoData -> Unit
                    is CoinViewModel.CoinState.UpdateData -> {
                        textView.text = coinState.rate
                    }
                    is CoinViewModel.CoinState.Error -> Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                    CoinViewModel.CoinState.Processing -> Unit
                }
            }
        }
    }

    class CoinViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
           if (modelClass.isAssignableFrom(CoinViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CoinViewModel(repository) as T
           }
            throw IllegalArgumentException("Unknown ViewModel class")
       }
    }
}