package com.adifaisalr.myweather.presentation.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.adifaisalr.myweather.R
import com.adifaisalr.myweather.databinding.FragmentFavoriteBinding
import com.adifaisalr.myweather.domain.model.DataHolder
import com.adifaisalr.myweather.domain.model.GeoLocationItem
import com.adifaisalr.myweather.presentation.ui.adapter.SearchResultAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private val viewModel by viewModels<FavoriteViewModel>()
    private var _binding: FragmentFavoriteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var adapter: SearchResultAdapter
    var cities: ArrayList<GeoLocationItem> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeViewModel()
        viewModel.loadFavoriteCities()
    }

    private fun initRecyclerView() {
        adapter = SearchResultAdapter(
            users = cities,
            actionFavoriteClickListener = { city, pos ->
                lifecycleScope.launchWhenResumed {
                    val newData = city.apply {
                        isFavorite = !isFavorite
                    }
                    val updatedRow = viewModel.updateCity(newData).await()
                    if (updatedRow > 0) adapter.updateData(newData, pos)
                }
            },
            actionSetDefaultClickListener = { city, pos -> }
        )
        binding.userList.adapter = adapter
    }

    private fun clearAdapter() {
        cities.clear()
        adapter.notifyDataSetChanged()
    }

    private fun observeViewModel() {
        viewModel.cities.observe(viewLifecycleOwner) { result ->
            when (result) {
                is DataHolder.Success -> {
                    setLoading(false)
                    if (result.data.isEmpty()) {
                        clearAdapter()
                        setError(getString(R.string.empty_favorite))
                    } else {
                        result.data.let { searchItems ->
                            cities.clear()
                            cities.addAll(searchItems)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
                is DataHolder.Loading -> {
                    setLoading(true)
                    setError(null)
                }
                else -> {
                    setLoading(false)
                    clearAdapter()
                    setError(getString(R.string.general_error))
                }
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setError(errorMessage: String?) {
        binding.error.visibility = if (errorMessage.isNullOrEmpty()) View.GONE else View.VISIBLE
        binding.error.text = errorMessage
    }
}