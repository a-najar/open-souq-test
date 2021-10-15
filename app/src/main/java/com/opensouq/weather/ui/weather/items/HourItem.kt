package com.opensouq.weather.ui.weather.items

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.opensouq.weather.R
import com.opensouq.weather.databinding.HourItemBinding
import java.text.SimpleDateFormat
import java.util.*

class HourItem(override val type: Int = R.id.hour_item) : AbstractBindingItem<HourItemBinding>() {


    private lateinit var image: String
    private lateinit var hour: String
    private lateinit var tempC: String
    private lateinit var tempF: String


    fun withImage(image: String): HourItem {
        this.image = image
        return this
    }

    fun withHour(hour: String): HourItem {
        this.hour = hour
        return this
    }

    fun withTempC(tempC: String): HourItem {
        this.tempC = tempC
        return this
    }

    fun withTempF(tempF: String): HourItem {
        this.tempF = tempF
        return this
    }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        HourItemBinding.inflate(inflater, parent, false)


    override fun bindView(binding: HourItemBinding, payloads: List<Any>) {
        super.bindView(binding, payloads)
        binding.imageWeather.load(image)
        binding.textTempC.text = binding.root.context.getString(R.string.text_temp_c, tempC)
        binding.textTempF.text = binding.root.context.getString(R.string.text_temp_f, tempF)
        binding.textTime.text = hour
    }
}