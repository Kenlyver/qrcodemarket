package com.example.qrcodemarket

import android.app.Application
import com.example.qrcodemarket.data.network.MyApi
import com.example.qrcodemarket.data.network.response.NetworkConnectionInterceptor
import com.example.qrcodemarket.data.respositories.UserRepository
import com.example.qrcodemarket.ui.auth.AuthViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton


class QRMarketApplication: Application(), KodeinAware {

    override val kodein = Kodein.lazy{
        import(androidXModule(this@QRMarketApplication))
        bind() from this.singleton { NetworkConnectionInterceptor(this.instance()) }
        bind() from this.singleton { MyApi(this.instance()) }
        bind() from this.singleton { UserRepository(this.instance()) }
        bind() from this.singleton { AuthViewModelFactory(this.instance()) }
    }

}