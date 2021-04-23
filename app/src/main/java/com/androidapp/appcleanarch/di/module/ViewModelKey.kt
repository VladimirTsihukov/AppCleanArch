package com.androidapp.appcleanarch.di.module

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

//для чего аннотация используется
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)

@MapKey //аннотация используется как ключ к нашей Map
annotation class ViewModelKey(val value: KClass<out ViewModel>)
