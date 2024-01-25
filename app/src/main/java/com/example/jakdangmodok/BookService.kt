package com.example.jakdangmodok

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {

    // 알라딘 책 검색 api
    @GET("ItemSearch.aspx")
    fun getBookSearch(
        @Query("TTBKey") ttbkey: String,    // 알라딘 api key
        @Query("Query") query: String  // 검색어
    ): Call<String>

    // 알라딘 책 목록 api
    @GET("ItemList.aspx")
    fun getBookList(
        @Query("TTBKey") ttbkey: String,
        @Query("QueryType") querytype: String,
        @Query("Query") query: String
    ): Call<String>

}