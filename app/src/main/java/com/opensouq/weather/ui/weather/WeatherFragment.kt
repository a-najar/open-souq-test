package com.opensouq.weather.ui.weather

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.mikepenz.fastadapter.GenericFastAdapter
import com.mikepenz.fastadapter.adapters.GenericItemAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil
import com.opensouq.weather.R
import com.opensouq.weather.databinding.FragmentWeatherBinding
import com.opensouq.weather.domain.entities.CurrentWeather.Data.Weather
import com.opensouq.weather.domain.entities.CurrentWeather.Data.Weather.Hourly
import com.opensouq.weather.domain.usecases.CantFindAnyWeather
import com.opensouq.weather.ui.base.BindingFragment
import com.opensouq.weather.ui.weather.items.HourItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class WeatherFragment :
    BindingFragment<FragmentWeatherBinding, WeatherViewModel>(
        FragmentWeatherBinding::inflate,
        WeatherViewModel::class.java
    ) {

    private val itemsAdapter = GenericItemAdapter()
    private val adapter = GenericFastAdapter.with(itemsAdapter)


    override fun viewCreated(binding: FragmentWeatherBinding, savedInstanceState: Bundle?) {
        lifecycleScope.launchWhenStarted { getCountry(getCountryById(binding.chipGroupCountry.checkedChipId)) }
        onGroupChanged(binding)
        onSearchClicked(binding)
    }

    private fun onSearchClicked(binding: FragmentWeatherBinding) {
        binding.inputSearch.setEndIconOnClickListener {
            binding.editTextSearchQuery.setText("")
            binding.chipGroupCountry.isVisible = true
            getCountry(getCountryById(binding.chipGroupCountry.checkedChipId))
        }
        binding.editTextSearchQuery.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.editTextSearchQuery.text.toString()
                binding.chipGroupCountry.isVisible = false
                getCountry(query)
            }
            true
        }
    }

    private fun onGroupChanged(binding: FragmentWeatherBinding) {
        binding.chipGroupCountry.setOnCheckedChangeListener { group, checkedId ->
            getCountry(getCountryById(checkedId))
        }
    }

    private fun getCountryById(checkedId: Int) = when (checkedId) {
        R.id.chipAqaba -> "aqaba"
        R.id.chipIrbid -> "irbid"
        else -> "amman"
    }

    private fun getCountry(country: String) {
        viewModel.getWeatherFlow(country)
            .catch { showError(it) }
            .onEach { showData(it) }
            .onStart { binding.progressBar.show() }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showData(weather: Weather) {
        with(binding) {
            progressBar.hide()
            textViewAvgTempC.text = getString(R.string.text_avg_temperature_in_c, weather.avgtempC)
            textViewAvgTempF.text = getString(R.string.text_avg_temperature_in_f, weather.avgtempF)
            textVieDate.text = weather.date
            listTimeAverage.adapter = adapter
            setHours(weather.hourly)
        }
    }

    private fun setHours(hourly: List<Hourly>) {
        val items = hourly.map { hour ->
            HourItem()
                .also { it.identifier = it.type.toLong() }
                .withHour(hour.time)
                .withImage(hour.weatherIconUrl.firstOrNull()?.value.orEmpty())
                .withTempC(hour.tempC)
                .withTempF(hour.tempF)
        }
        FastAdapterDiffUtil[itemsAdapter] = FastAdapterDiffUtil.calculateDiff(itemsAdapter, items)
    }

    private fun showError(throwable: Throwable) {
        binding.progressBar.hide()
        val message = when (throwable) {
            is CantFindAnyWeather -> getString(R.string.error_message_general)
            else -> getString(R.string.error_message_wrong)
        }
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE).show()
    }

}


fun ProgressBar.hide() {
    isVisible = false
}

fun ProgressBar.show() {
    isVisible = true
}