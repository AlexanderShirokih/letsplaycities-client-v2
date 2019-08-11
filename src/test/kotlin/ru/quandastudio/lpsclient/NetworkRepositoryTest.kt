package ru.quandastudio.lpsclient

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import ru.quandastudio.lpsclient.core.NetworkClient
import ru.quandastudio.lpsclient.model.PlayerData

internal class NetworkRepositoryTest {

    @Test
    fun testPlayGame() {
        val networkClient = NetworkClient("localhost")
        val repository = NetworkRepository(networkClient, Single.just(""))
        val playerData = createPlayerData()
        repository.login(playerData)

        val it = repository.login(playerData)
            .doOnSubscribe { println("Connecting to server...") }
            .doOnSuccess { println("Waiting for opponent...") }
            .observeOn(Schedulers.io())
            .flatMapMaybe { repository.play(false, null) }
            .observeOn(Schedulers.single())
            .blockingGet()
        println("Play as=${playerData.authData.login}, with=${it.first.authData.login}, starter=${it.second}")
    }

    private fun createPlayerData(): PlayerData {
        return PlayerData.Factory().create("UnitTest")
    }

}