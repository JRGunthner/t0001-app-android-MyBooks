package com.jgtche.mybooks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jgtche.mybooks.R
import com.jgtche.mybooks.databinding.FragmentFavoriteBinding
import com.jgtche.mybooks.helper.BookConstants
import com.jgtche.mybooks.ui.adapter.BookAdapter
import com.jgtche.mybooks.ui.listener.BookListener
import com.jgtche.mybooks.viewmodel.FavoriteViewModel

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteViewModel by viewModels()
    private val adapter: BookAdapter = BookAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        binding.recyclerviewBooksFavorite.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewBooksFavorite.adapter = adapter

        attachListener()
        setObservers()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavoriteBooks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun attachListener() {
        adapter.attachListener(object: BookListener {
            override fun onClick(id: Int) {
                val bundle = Bundle()
                bundle.putInt(BookConstants.KEY.BOOK_ID, id)
                findNavController().navigate(R.id.navigation_details, bundle)
            }

            override fun onFavoriteClick(id: Int) {
                viewModel.favorite(id)
                viewModel.getFavoriteBooks()
            }
        })
    }

    private fun setObservers() {
        viewModel.books.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.recyclerviewBooksFavorite.visibility = View.GONE
                binding.textViewNoBooks.visibility = View.VISIBLE
                binding.imageViewNoBooks.visibility = View.VISIBLE
            } else {
                binding.recyclerviewBooksFavorite.visibility = View.VISIBLE
                binding.textViewNoBooks.visibility = View.GONE
                binding.imageViewNoBooks.visibility = View.GONE
                adapter.updateBooks(it)
            }
        }
    }
}
