package com.example.mayank.kwizzapp.dependency.components

import com.example.mayank.kwizzapp.dependency.scopes.ActivityScope
import dagger.Component
import example.com.mymandir.mainfragment.MainFragment

@ActivityScope
@Component(dependencies = arrayOf(ApplicationComponent::class))
interface InjectFragmentComponent {
    fun injectMainFragment(mainFragment: MainFragment)
}