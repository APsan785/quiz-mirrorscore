package com.apsan.quizbyap.viewmodel

import android.widget.Button
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.pedant.SweetAlert.SweetAlertDialog
import com.apsan.quizbyap.models.categories
import com.apsan.quizbyap.models.question
import com.apsan.quizbyap.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class QuizViewModel
    @Inject constructor(
        val repository: Repository
    ): ViewModel  () {

    val CategoryStringList = mutableListOf<String>()
    val CategoryList = mutableListOf<categories>()
    val questionList = mutableListOf<question>()
    val score = MutableLiveData(0)
    val questionNumber = MutableLiveData(1)

    suspend fun getCategoriesList(): List<categories> {
        CategoryList.addAll(repository.getCategories()!!)
        return CategoryList
    }

    suspend fun getQuestions (
        category : Int,
        difficulty : String
    ){
        repository.getQuestion(category, difficulty)?.forEach {
            questionList.add(it)
        }
    }



}