package tgo1014.desafioandroid.model

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tgo1014.desafioandroid.contracts.MainContract
import tgo1014.desafioandroid.model.objects.Shot
import tgo1014.desafioandroid.network.RestClient

class MainModelImpl : MainContract.MainModel {

    private var shotsCache: MutableList<Shot> = ArrayList()

    override fun saveShotsCache(shotList: List<Shot>) {
        clearShotsCache()
        this.shotsCache.addAll(shotList)
    }

    override fun clearShotsCache() {
        this.shotsCache.clear()
    }

    override fun restoreShotsCache(): List<Shot> = shotsCache

    override fun requestShots(page: Int, order: String, listener: MainContract.MainModel.OnShotsRequestCompletionListener) {
        RestClient.getShots(page, order).enqueue(object : Callback<List<Shot>> {
            override fun onResponse(call: Call<List<Shot>>, response: Response<List<Shot>>) {
                if (response.isSuccessful) {
                    listener.onSucess(response.body()!!)
                    return
                }

                listener.onError("Error") //TODO ajustar mensagem neste caso
            }

            override fun onFailure(call: Call<List<Shot>>, t: Throwable) {
                listener.onError(t.localizedMessage)
            }
        })
    }
}