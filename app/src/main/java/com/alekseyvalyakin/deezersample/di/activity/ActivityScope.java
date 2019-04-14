package com.alekseyvalyakin.deezersample.di.activity;

import javax.inject.Scope;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Dagger annotation
 * Custom scope for Parent fragment
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
@interface ActivityScope {}
