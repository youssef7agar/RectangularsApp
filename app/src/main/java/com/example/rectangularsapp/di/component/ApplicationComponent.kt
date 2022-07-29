package com.example.rectangularsapp.di.component

import android.content.Context
import com.example.rectangularsapp.di.module.AppModule
import com.example.rectangularsapp.di.module.DomainModule
import com.example.rectangularsapp.di.module.RemoteModule
import com.example.rectangularsapp.di.module.ViewModelModule
import com.example.rectangularsapp.presentation.view.MainActivity
import com.example.rectangularsapp.util.MyApplication
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        RemoteModule::class,
        DomainModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(app: MyApplication)

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}