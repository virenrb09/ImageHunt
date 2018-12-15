package com.example.virenbhandari.imagehunt.rest

import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import com.example.virenbhandari.imagehunt.model.SearchResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Type
import java.net.HttpURLConnection
import java.net.URL

private const val READ_TIMEOUT = 8000
private const val CONNECTION_TIMEOUT = 8000
const val CUSTOM_ERROR_CODE = 1000

class NetworkTask(
    private val url: String,
    private val listener: IAPIListener<*>,
    private val httpMethod: String,
    private val type: Type
) : AsyncTask<String, String, String>() {
    private val uiHandler: Handler = Handler(Looper.getMainLooper())

    override fun doInBackground(vararg p0: String?): String {
        var result = ""
        try {
            val url = URL(url)
            val httpURLConnection = url.openConnection() as HttpURLConnection

            httpURLConnection.readTimeout = READ_TIMEOUT
            httpURLConnection.connectTimeout = CONNECTION_TIMEOUT
            httpURLConnection.doOutput = true
            httpURLConnection.requestMethod = httpMethod
            httpURLConnection.connect()

            val responseCode: Int = httpURLConnection.responseCode
            if (responseCode == 200) {
                val inStream: InputStream = httpURLConnection.inputStream
                val isReader = InputStreamReader(inStream)
                val bReader = BufferedReader(isReader)
                var tempStr: String?

                try {
                    while (true) {
                        tempStr = bReader.readLine()
                        if (tempStr == null) {
                            break
                        }
                        result += tempStr
                    }
                } catch (Ex: Exception) {
                    uiHandler.post {
                        listener.onError(responseCode, "")
                    }
                }
            } else {
                uiHandler.post {
                    listener.onError(responseCode, "")
                }
            }
        } catch (ex: Exception) {
            uiHandler.post {
                listener.onError(CUSTOM_ERROR_CODE, ex.toString())
            }
        }
        return result
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        if (TextUtils.isEmpty(result)) return
        listener.onSuccess(response = Gson().fromJson(result, type))
    }
}