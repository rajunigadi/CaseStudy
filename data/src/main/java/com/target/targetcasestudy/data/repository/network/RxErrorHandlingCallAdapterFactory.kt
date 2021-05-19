package com.target.targetcasestudy.data.repository.network

import com.target.targetcasestudy.data.repository.network.exceptions.ServerException
import io.reactivex.*
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException
import java.lang.reflect.Type

class RxErrorHandlingCallAdapterFactory private constructor() : CallAdapter.Factory() {
    private val original: RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *> {
        return RxCallAdapterWrapper(
            retrofit, original.get(returnType, annotations, retrofit) as CallAdapter<out Any, *>
        )
    }

    private class RxCallAdapterWrapper<R> internal constructor(
        private val retrofit: Retrofit,
        private val wrapped: CallAdapter<R, *>
    ) : CallAdapter<R, Any> {

        override fun responseType(): Type {
            return wrapped.responseType()
        }

        override fun adapt(call: Call<R>): Any {
            val adaptedCall = wrapped.adapt(call)

            if (adaptedCall is Completable) {
                return adaptedCall.onErrorResumeNext { throwable ->
                    Completable.error(asRetrofitException(throwable))
                }
            }

            if (adaptedCall is Single<*>) {
                return adaptedCall.onErrorResumeNext { throwable ->
                    Single.error(asRetrofitException(throwable))
                }
            }

            if (adaptedCall is Observable<*>) {
                return adaptedCall.onErrorResumeNext { throwable: Throwable ->
                    Observable.error(asRetrofitException(throwable))
                }
            }

            if (adaptedCall is Maybe<*>) {
                return adaptedCall.onErrorResumeNext { throwable: Throwable ->
                    Maybe.error(asRetrofitException(throwable))
                }
            }

            throw RuntimeException("Type not supported")
        }

        private fun asRetrofitException(throwable: Throwable): ServerException {
            // We had non-200 http error
            if (throwable is HttpException) {
                val response = throwable.response()
                return ServerException.httpError(
                    response.raw().request().url().toString(),
                    response, retrofit
                )
            }

            // A network error happened
            return if (throwable is IOException) {
                ServerException.networkError(throwable)
            } else ServerException.unexpectedError(throwable)

            // We don't know what happened. We need to simply convert to an unknown error
        }
    }

    companion object {
        fun create(): CallAdapter.Factory {
            return RxErrorHandlingCallAdapterFactory()
        }
    }
}