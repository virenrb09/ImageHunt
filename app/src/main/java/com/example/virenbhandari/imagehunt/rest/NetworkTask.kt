package com.example.virenbhandari.imagehunt.rest

import android.os.AsyncTask
import com.example.virenbhandari.imagehunt.model.SearchResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

private const val READ_TIMEOUT = 8000
private const val CONNECTION_TIMEOUT = 8000
const val CUSTOM_ERROR_CODE = 1000

class NetworkTask<Any>(
    private val url: String,
    private val listener: IAPIListener<Any>,
    private val httpMethod: String
) : AsyncTask<String, String, String>() {

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
                    listener.onError(responseCode, "")
                }
            } else {
                listener.onError(responseCode, "")
            }
        } catch (ex: Exception) {
            listener.onError(CUSTOM_ERROR_CODE, ex.toString())
        }
        return result
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        listener.onSuccess(response = Gson().fromJson(result, object : TypeToken<SearchResult>() {}.type))
    }
}