package com.example.ifunsoedmobile1_ime

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ifunsoedmobile1_ime.R
import com.example.ifunsoedmobile1_ime.data.model.BookDoc
import com.example.ifunsoedmobile1_ime.ui.adapter.BookAdapter
import com.example.ifunsoedmobile1_ime.databinding.ActivityMainBinding
import com.example.ifunsoedmobile1_ime.databinding.ActivityDaftarBukuBinding
import com.example.ifunsoedmobile1_ime.ui.adapter.OnBookClickListener
import com.example.ifunsoedmobile1_ime.ui.fragment.BookDetailFragment
import com.example.ifunsoedmobile1_ime.viewmodel.MainViewModel


class DaftarBukuActivity : AppCompatActivity(), OnBookClickListener {
    private lateinit var binding: ActivityDaftarBukuBinding

    private val viewModel: MainViewModel by viewModels()

    private val adapter = BookAdapter(emptyList(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarBukuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.books.observe(this) {
            adapter.setData(it)
        }
        viewModel.fetchBooks("kotlin programing")

    }

    override fun onBookClick(book: BookDoc) {
        book.let { b->
            BookDetailFragment(
                title = b.title ?: "No Title",
                author = b.authorName?.joinToString(", ") ?: "Unknown Author",
                year = b.firstPublishYear?.toString() ?: "-",
                coverId = b.coverId ?: 0
            ).show(supportFragmentManager, "book_detail")
        }
    }
}
