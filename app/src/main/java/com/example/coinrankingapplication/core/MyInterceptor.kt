package com.example.coinrankingapplication.core

import okhttp3.Interceptor
import okhttp3.Response

class MyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("x-rapidapi-key", "92bd399073msh8bff8036850a054p167294jsnb3f0d0f365cf")
            .addHeader("x-rapidapi-host", "coinranking1.p.rapidapi.com")
            .build()
        return chain.proceed(request)
    }
}

/*
Proxy sunucunun çalışıyor olduğundan emin olmak için proxy ayarlarınızı kontrol edin veya ağ yöneticinize danışın. Proxy sunucu kullanmamanız gerektiğini düşünüyorsanız: Chrome menüsü > Ayarlar > Gelişmiş ayarları göster… > Proxy ayarlarını değiştir… > LAN Ayarları öğesine gidin ve "LAN için proxy sunucusu kullan" seçeneğinin işaretini kaldırın.
 */