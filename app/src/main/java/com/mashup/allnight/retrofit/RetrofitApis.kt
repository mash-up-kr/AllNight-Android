package com.mashup.allnight.retrofit

import com.mashup.allnight.retrofit.retrofitmodel.RetrofitCocktailDetailResponse
import com.mashup.allnight.retrofit.retrofitmodel.RetrofitCocktailListResponse
import com.mashup.allnight.retrofit.retrofitmodel.RetrofitCocktailScrapResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface IRetrofitApi {

    @GET("api/v1/search")
    fun getSearchIngredientListResult(
        @Query("ingredient") name: String
    ) : Call<String>

    @GET("api/v1/search/cocktail")
    fun getSearchCocktailListResult(
        @QueryMap queryMap: Map<String, String>
    ) : Call<RetrofitCocktailListResponse>

    @GET("api/v1/search/cocktail/{id}")
    fun getCocktailDetailResult(
        @Path(value = "id", encoded = true) id: String
    ) : Call<RetrofitCocktailDetailResponse>

    @GET("api/v1/user/scrap")
    fun getCocktailScrapResult(
        // no variable
    ) : Call<RetrofitCocktailScrapResponse>

}