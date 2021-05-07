package com.androidapp.utils.ui

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference
import kotlin.reflect.KProperty

class ViewByIdDelegate<out T : View>(private val rootGetter: () -> View?, private val viewId: Int) {

    //ссылка на root
    private var rootRef: WeakReference<View>? = null

    //ссылка на View
    private var viewRef: T? = null

    //методы вызывваются при каждом обращении к переменной
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        //получаем закэшируемую view
        var view = viewRef
        //получаем корневой объект
        val cachedRoot = rootRef?.get()
        //Получаем текущий корневой объект
        val currentRoot = rootGetter()

        if (currentRoot != cachedRoot || view == null) {
            if (currentRoot == null) {
                throw IllegalStateException("Cannot get View, there is root yet")
            }
            //созжаем view
            view = currentRoot.findViewById(viewId)
            //сохраняем ссылку на View чтобы не создавать ее каждый раз заново
            viewRef = view
            //сохраняем ссылку на root чтобы не искать его каждый раз заново
            rootRef = WeakReference(currentRoot)
        }

        checkNotNull(view) { "View with id $viewId not found in root" }
        return view
    }
}

fun <T : View> Activity.viewByID(@IdRes viewId: Int): ViewByIdDelegate<T> {
    return ViewByIdDelegate({ window.decorView.findViewById(android.R.id.content) }, viewId)
}

fun <T : View> Fragment.viewById(@IdRes viewId: Int): ViewByIdDelegate<T> {
    return ViewByIdDelegate({ view }, viewId)
}
