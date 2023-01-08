package com.lafa.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.lafa.R
import com.lafa.adapter.FavoriteAdapter
import com.lafa.adapter.ProductAdapter
import com.lafa.databinding.FragmentFavoriteBinding
import com.lafa.interfaces.FavoriteProduct
import com.lafa.interfaces.ProductDetail
import com.lafa.model.ProductModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment(), ProductDetail {

    private lateinit var binding: FragmentFavoriteBinding
    @Inject
    lateinit var viewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initAdapter()
        retrieveViewModel()
        clickedOfProduct()
    }


    private fun initAdapter() {
        favoriteAdapter = FavoriteAdapter(this@FavoriteFragment)
    }

    private fun retrieveViewModel() {
            viewModel.getAllProductToFavorite().observe(viewLifecycleOwner) {
                favoriteAdapter.differ.submitList(it.toList())
                binding.rVFavorite.apply {
                    this.adapter = favoriteAdapter
                    this.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

                    this.addItemDecoration(
                        DividerItemDecoration(
                            requireContext(),
                            DividerItemDecoration.VERTICAL
                        )
                    )
                }
            }
    }

    private fun clickedOfProduct() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val product = favoriteAdapter.differ.currentList[position]
                viewModel.deleteProductFromFavorite(product)
                Snackbar.make(requireView(), "Successfully deleted product", Snackbar.LENGTH_SHORT)
                    .apply {
                        setAction("Undo Product") {
                            viewModel.insertProductToFavorite(product)
                        }.show()
                    }
            }

        }).attachToRecyclerView(binding.rVFavorite)
    }

    override fun clickProductOfDetail(productModel: ProductModel) {
        val bundle = Bundle().apply {
            this.putSerializable("product", productModel)
        }
        findNavController().navigate(R.id.action_favoriteFragment_to_productDetailFragment, bundle)
    }
}