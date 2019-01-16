package com.example.mayank.kwizzapp.dependency.components

import com.example.mayank.kwizzapp.dependency.scopes.ActivityScope
import dagger.Component
import example.com.mymandir.MainActivity

@ActivityScope
@Component(dependencies = arrayOf(ApplicationComponent::class))
interface InjectActivityComponent {
    fun injectMainActivity(walletActivity: MainActivity)
}
