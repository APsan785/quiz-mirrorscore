package com.apsan.quizbyap.repo

import android.util.Log
import com.apsan.quizbyap.models.categories
import com.apsan.quizbyap.models.question
import com.apsan.quizbyap.retrofit.QuizAPI
import javax.inject.Inject

class Repository @Inject constructor(
    val quizAPI: QuizAPI
) {

    val TAG = "tag"

    suspend fun getCategories(): List<categories>? {
        val category_response = quizAPI.getCategoryResponse()
        if (category_response.isSuccessful) {
            val categories = category_response.body()?.trivia_categories

            Log.d(TAG, "getCategories: hey")
            return categories
        } else {
            Log.d(TAG, "getCategories: ${category_response.message()}")
        }
        return emptyList()
    }

    suspend fun getToken():String?{
        val token_response = quizAPI.getSessionToken()
        if(token_response.isSuccessful){
            val token = token_response.body()?.token
//            Log.d(TAG, "getToken: token got $token")
            return token
        }
        return null
    }


    suspend fun getQuestion(
        category : Int,
        difficulty : String
    ) : ArrayList<question>?{
        val question_response = quizAPI.getQuestions(
            category = category,
            difficulty = difficulty.lowercase(),
            token = getToken()!!
        )
        if (question_response.isSuccessful){
//            Log.d(TAG, "getQuestion: ${question_response.body()?.responseCode}")
            Log.d(TAG, "getQuestion: question list ${question_response.body()?.results?.size}")
            return question_response.body()?.results
        }else{
            Log.d(TAG, "getQuestion: ${question_response.errorBody().toString()}")
        }
        return null
    }
    //TODO: No Internet case
}