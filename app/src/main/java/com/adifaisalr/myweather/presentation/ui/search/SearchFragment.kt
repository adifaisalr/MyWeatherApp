package com.adifaisalr.myweather.presentation.ui.search

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.adifaisalr.myweather.R
import com.adifaisalr.myweather.databinding.FragmentSearchBinding
import com.adifaisalr.myweather.domain.model.DataHolder
import com.adifaisalr.myweather.domain.model.GeoLocationItem
import com.adifaisalr.myweather.presentation.ui.adapter.SearchResultAdapter
import com.adifaisalr.myweather.presentation.util.NavigationUtils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel by viewModels<SearchViewModel>()
    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var adapter: SearchResultAdapter
    var searchResults: ArrayList<GeoLocationItem> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearchInputListener()
        initRecyclerView()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initSearchInputListener() {
        binding.input.setOnEditorActionListener { view: View, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch(view)
                true
            } else {
                false
            }
        }
        binding.input.setOnKeyListener { view: View, keyCode: Int, event: KeyEvent ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                doSearch(view)
                true
            } else {
                false
            }
        }
        binding.searchBtn.setOnClickListener { view ->
            doSearch(view)
        }
    }

    private fun clearAdapter() {
        searchResults.clear()
        adapter.notifyDataSetChanged()
    }

    private fun observeViewModel() {
        viewModel.searchResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is DataHolder.Success -> {
                    setLoading(false)
                    if (result.data.isEmpty()) {
                        clearAdapter()
                        setError(getString(R.string.empty_search_result, viewModel.query))
                    } else {
                        result.data.let { searchItems ->
                            searchResults.clear()
                            searchResults.addAll(searchItems)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
                is DataHolder.Loading -> {
                    setLoading(true)
                    setError(null)
                }
                is DataHolder.Failure -> {
                    setLoading(false)
                    clearAdapter()
                    setError(result.errorData.message)
                }
                else -> {
                    setLoading(false)
                    clearAdapter()
                    setError(getString(R.string.general_error))
                }
            }
        }
    }

    private fun initRecyclerView() {
        adapter = SearchResultAdapter(
            users = searchResults,
            actionFavoriteClickListener = { city, pos ->
                lifecycleScope.launchWhenResumed {
                    val newData = city.apply {
                        isFavorite = !isFavorite
                    }
                    val updatedRow = viewModel.updateCity(newData).await()
                    if (updatedRow > 0) adapter.updateData(newData, pos)
                }
            },
            actionSetDefaultClickListener = { city, _ ->
                val action = SearchFragmentDirections.actionNavigationSearchCityToNavigationHome(
                    city.lat.toFloat(),
                    city.lon.toFloat()
                )
                findNavController().safeNavigate(action)
            }
        )
        binding.userList.adapter = adapter
    }

    private fun doSearch(v: View) {
        val query = binding.input.text.toString()
        // Dismiss keyboard
        dismissKeyboard(v.windowToken)
        viewModel.setQuery(query)
        viewModel.searchCity()
    }

    private fun dismissKeyboard(windowToken: IBinder) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun setLoading(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setError(errorMessage: String?) {
        binding.error.visibility = if (errorMessage.isNullOrEmpty()) View.GONE else View.VISIBLE
        binding.error.text = errorMessage
    }
}