package com.alekseyvalyakin.deezersample.di.activity;


import com.alekseyvalyakin.deezersample.MainActivity;
import com.alekseyvalyakin.deezersample.di.rib.RibDependencyProvider;
import com.alekseyvalyakin.deezersample.ribs.root.RootBuilder;
import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent extends RootBuilder.ParentComponent, RibDependencyProvider {

    void inject(MainActivity mainActivity);
}
