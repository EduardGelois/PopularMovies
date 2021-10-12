package com.gelioscompany.popularmovies.presentation.utils

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Делегат для получения и автоматической очистки переменной ViewBinding
 *
 * @param F - тип фрагмента
 * @param V - тип view binding
 * @param viewBinder - метод инициализации view binding
 *
 * @return делегат ViewBinding
 */
fun <F : Fragment, V : ViewBinding> viewBinding(
    viewBinder: (View) -> V
): FragmentViewBindingProperty<F, V> {
    return FragmentViewBindingProperty(viewBinder)
}

/**
 * Класс делегата property класса с view binding,
 * вызывающий lazy инициализацию переменной и очистку при вызове onDestroy view фрагмента
 *
 * @param viewBinder - метод инициализации view binding
 */
class FragmentViewBindingProperty<in F : Fragment, out V : ViewBinding>(
    private val viewBinder: (View) -> V
) : ReadOnlyProperty<F, V>, LifecycleEventObserver {

    private var viewBinding: V? = null

    @MainThread
    override fun getValue(thisRef: F, property: KProperty<*>): V {
        viewBinding?.let { return it }

        val lifecycle = try {
            thisRef.viewLifecycleOwner.lifecycle
        } catch (ignored: IllegalStateException) {
            error("Фрагмент не прикреплен к activity")
        }

        val view = thisRef.view ?: error("Фрагмент не имеет view или был уничтожен")
        val viewBinding = viewBinder(view)
        if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
            // Во избежание утечек ничего не делаем, так как фрагмент был уничтожен
        } else {
            lifecycle.addObserver(this)
            this.viewBinding = viewBinding
        }
        return viewBinding
    }


    override fun onStateChanged(source: LifecycleOwner, event: Event) {
        if (event == Event.ON_DESTROY) {
            mainHandler.post { viewBinding = null }
        }
    }

    private companion object {

        private val mainHandler = Handler(Looper.getMainLooper())
    }
}