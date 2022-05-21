package com.apsan.quizbyap.models

import com.google.gson.annotations.SerializedName

data class categories(
    val id : Int,
    val name : String
)

data class category_response(
    val trivia_categories : List<categories>
)

data class token_response(
    @SerializedName("response_code")
    val responseCode : Int,
    @SerializedName("response_message")
    val responseMessage : String,
    @SerializedName("token")
    val token : String
)

data class question_response (

    @SerializedName("response_code" ) var responseCode : Int?               = null,
    @SerializedName("results"       ) var results      : ArrayList<question> = arrayListOf()

)

data class question (

    @SerializedName("category"          ) var category         : String?           = null,
    @SerializedName("type"              ) var type             : String?           = null,
    @SerializedName("difficulty"        ) var difficulty       : String?           = null,
    @SerializedName("question"          ) var question         : String?           = null,
    @SerializedName("correct_answer"    ) var correctAnswer    : String?           = null,
    @SerializedName("incorrect_answers" ) var incorrectAnswers : ArrayList<String> = arrayListOf()

)
