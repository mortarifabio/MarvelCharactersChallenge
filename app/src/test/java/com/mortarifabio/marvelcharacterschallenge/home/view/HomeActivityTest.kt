package com.mortarifabio.marvelcharacterschallenge.home.view

import androidx.lifecycle.Lifecycle
import org.junit.Test
import androidx.test.core.app.ActivityScenario.launch

class HomeActivityTest {

    //todo: implementar testes de navegação

    @Test
    fun clickingFavorites_shouldLoadFavoritesFragment() {
        val scenario = launch(HomeActivity::class.java)
        scenario.moveToState(Lifecycle.State.CREATED)
    }
}