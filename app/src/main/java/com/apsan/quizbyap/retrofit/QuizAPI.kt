package com.apsan.quizbyap.retrofit

import com.apsan.quizbyap.models.category_response
import com.apsan.quizbyap.models.question_response
import com.apsan.quizbyap.models.token_response
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizAPI {

    @GET("api_category.php")
    suspend fun getCategoryResponse(): Response<category_response>

    @GET("api_token.php?command=request")
    suspend fun getSessionToken(): Response <token_response>

    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount : Int = 10,
        @Query("category") category : Int,
        @Query("difficulty") difficulty : String,
        @Query("type") type: String = "multiple",
        @Query("token") token : String
    ): Response<question_response>

}