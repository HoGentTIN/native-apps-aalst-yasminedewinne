package be.hogent.yasminedewinne.carwashapp.util.converters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.time.LocalDate
import java.time.LocalTime

@BindingAdapter("app:date")
fun formatDate(view: TextView, date: LocalDate?) {
    if (date == null)
        return

    view.text = DateFormatter.toString(date, "dd/MM/yyyy")
}

@BindingAdapter("app:beginTime")
fun formatTime(view: TextView, time: LocalTime?) {
    if (time == null)
        return

    view.text = TimeFormatter.toString(time, "HH:mm")
}
