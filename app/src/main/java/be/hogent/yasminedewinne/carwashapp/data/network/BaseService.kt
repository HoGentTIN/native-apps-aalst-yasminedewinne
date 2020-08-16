package be.hogent.yasminedewinne.carwashapp.data.network

import android.os.Build
import be.hogent.yasminedewinne.carwashapp.App
import be.hogent.yasminedewinne.carwashapp.BuildConfig
import be.hogent.yasminedewinne.carwashapp.util.converters.DateAdapter
import be.hogent.yasminedewinne.carwashapp.util.converters.TimeAdapter
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object BaseService {

    private val userHelper = App.getUserHelper()

    private fun isProbablyAnEmulator() = Build.FINGERPRINT.startsWith("generic") ||
            Build.FINGERPRINT.startsWith("unknown") ||
            Build.MODEL.contains("google_sdk") ||
            Build.MODEL.contains("Emulator") ||
            Build.MODEL.contains("Android SDK built for x86") ||
            Build.BOARD == "QC_Reference_Phone" || // bluestacks
            Build.MANUFACTURER.contains("Genymotion") ||
            Build.HOST.startsWith("Build") || // MSI App Player
            (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")) ||
            "google_sdk" == Build.PRODUCT

    private val BASE_URL: String
        get() {
            // De 10.0.2.2 of "localhost" url werkt enkel op emulator
                return "https://carwashapi20200630213929.azurewebsites.net/api/"
        }

    private val logInterceptor: HttpLoggingInterceptor
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            interceptor.redactHeader("Authorization")

            return interceptor
        }

    private val moshi = Moshi.Builder()
        .add(DateAdapter())
        .add(TimeAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()

    val RETROFIT: Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .client(createHttpClient())
        .build()

    /** Maak een OkHttpClient op basis van debugging status */
    private fun createHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.callTimeout(5000, TimeUnit.MILLISECONDS)

        // Source: https://www.vogella.com/tutorials/Retrofit/article.html
        okHttpClient.addInterceptor { chain ->
            val originalRequest: Request = chain.request()
            val builder = originalRequest
                .newBuilder()
                .header("content-type", "application/json")

            if (userHelper.isSignedIn()) {
                val authToken = userHelper.authToken
                builder.addHeader("Authorization", "Bearer $authToken")
            }

            val newRequest: Request = builder.build()
            chain.proceed(newRequest)
        }

        if (BuildConfig.DEBUG) {
            // Log all communication while debugging
            okHttpClient.addInterceptor(logInterceptor)

            // Accept all certs for hostnames while debugging
            if (isProbablyAnEmulator()) {
                try {
                    // Create a trust manager that does not validate certificate chains
                    val trustAllCerts: Array<TrustManager> = arrayOf(object : X509TrustManager {
                        override fun checkClientTrusted(
                            chain: Array<out X509Certificate>?,
                            authType: String?
                        ) = Unit

                        override fun checkServerTrusted(
                            chain: Array<out X509Certificate>?,
                            authType: String?
                        ) = Unit

                        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                    })

                    // Install the all-trusting trust manager
                    val sslContext = SSLContext.getInstance("SSL")
                    sslContext.init(null, trustAllCerts, SecureRandom())

                    // Create an ssl socket factory with our all-trusting manager
                    val sslSocketFactory = sslContext.socketFactory
                    if (trustAllCerts.isNotEmpty() && trustAllCerts.first() is X509TrustManager) {
                        okHttpClient.sslSocketFactory(
                            sslSocketFactory,
                            trustAllCerts.first() as X509TrustManager
                        )
                        okHttpClient.hostnameVerifier(HostnameVerifier { _, _ -> true })
                    }
                } catch (e: Exception) {}
            }
        }

        return okHttpClient.build()
    }
}
