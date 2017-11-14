package tgo1014.desafioandroid.model

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tgo1014.desafioandroid.contracts.DetailContract
import tgo1014.desafioandroid.network.RestClient


class DetailModel : DetailContract.DetailModel {
    override fun requestShot(shotId: Int, listener: DetailContract.DetailModel.OnShotRequestCompletionListener) {
        RestClient.getSingleShot(shotId).enqueue(object : Callback<Shot> {
            override fun onResponse(call: Call<Shot>, response: Response<Shot>) {
                if (response.isSuccessful) {
                    listener.onSucess(response.body()!!)
                    return
                }

                listener.onError("Error") //TODO ajustar mensagem neste caso
            }

            override fun onFailure(call: Call<Shot>, t: Throwable) {
                listener.onError(t.localizedMessage)
            }
        })

    }
}