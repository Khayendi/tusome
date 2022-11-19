package com.code.tusome.di

import com.code.tusome.ui.viewmodels.MainViewModel
import dagger.Component
import dagger.Module

@Component(modules = [RoomBDComponent::class])
interface RoomBDComponent {
    fun injectMainViewModel(mainViewModel: MainViewModel)
}