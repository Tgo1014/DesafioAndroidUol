package tgo1014.desafioandroid.model

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tgo1014.desafioandroid.contracts.MainContract
import tgo1014.desafioandroid.network.RestClient

class MainModel : MainContract.MainModel {

    override fun requestShots(page: Int, listener: MainContract.MainModel.OnShotsRequestCompletionListener) {
        RestClient.getShots(page).enqueue(object : Callback<List<Shot>> {
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