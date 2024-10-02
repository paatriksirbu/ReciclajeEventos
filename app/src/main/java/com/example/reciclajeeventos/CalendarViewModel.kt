package com.example.reciclajeeventos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.Month

class CalendarViewModel : ViewModel() {
    private val _month = MutableLiveData(Month.OCTOBER)
    val month: LiveData<Month> get() = _month

    private val _year = MutableLiveData(2024)
    val year: LiveData<Int> get() = _year

    fun setMonth(newMonth: Month) {
        _month.value = newMonth
    }

    fun setYear(newYear: Int) {
        _year.value = newYear
    }
}
