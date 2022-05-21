package com.apsan.quizbyap.frags

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.apsan.quizbyap.R
import com.apsan.quizbyap.databinding.FragCategoryBinding
import com.apsan.quizbyap.models.categories
import com.apsan.quizbyap.utils.DifficultyQuestions
import com.apsan.quizbyap.viewmodel.QuizViewModel
import com.hoc081098.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class CategoryFrag : Fragment(R.layout.frag_category) {

    private val binding by viewBinding(FragCategoryBinding::bind)
    private val viewmodel: QuizViewModel by activityViewModels()
    val TAG = "tag"
    private lateinit var categoryList: List<categories>
    var category = "General Knowledge"
    var difficulty = DifficultyQuestions.EASY

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCategorySpinner()
        setupDifficultySpinner()

//        binding.categorySpinner.setOnSpinnerItemSelectedListener<String> { _, _, _, newItem ->
//            category = newItem
//        }
//        binding.difficultySpinner.setOnSpinnerItemSelectedListener<String>{_,_,_, newItem ->
//            difficulty = newItem
//        }


        binding.btnStartQuiz.setOnClickListener {

            category = binding.categorySpinner.selectedItem.toString()
            difficulty = binding.difficultySpinner.selectedItem.toString()

            binding.categoryProgressBar.visibility = View.VISIBLE
            Log.d(TAG, "onViewCreated: ${binding.categoryProgressBar.visibility}")

            Log.d(TAG, "onViewCreated: $category")

            val action = CategoryFragDirections.actionCategoryFragToQuestionFrag(
                category = category,
                difficulty = difficulty
            )

            lifecycleScope.launch(Dispatchers.IO) {
                viewmodel.getQuestions(
                    getIdByName(category),
                    difficulty
                )

                binding.categoryProgressBar.visibility = View.INVISIBLE
                Log.d(TAG, "onViewCreated: questionList Size ${viewmodel.questionList.size}")
                withContext(Dispatchers.Main) {
                    requireActivity().findNavController(R.id.fragContainer).navigate(action)
                }

            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            view.findNavController().navigate(R.id.action_categoryFrag_to_homeFrag)
        }


        binding.root
    }

    private fun setupDifficultySpinner() {
        val difficultyList = arrayListOf(
            DifficultyQuestions.EASY,
            DifficultyQuestions.MEDIUM,
            DifficultyQuestions.HARD
        )
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, difficultyList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.difficultySpinner.adapter = adapter
    }

    private fun setupCategorySpinner() {
        if (viewmodel.CategoryStringList.isEmpty()) {
            lifecycleScope.launch {

                binding.chooseCategProgressbar.visibility = View.VISIBLE
                categoryList = viewmodel.getCategoriesList()
                Log.d(TAG, "setupCategorySpinner: ${categoryList.size}")
                categoryList.forEach {
                    viewmodel.CategoryStringList.add(it.name)
                }

                binding.chooseCategProgressbar.visibility = View.INVISIBLE
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, viewmodel.CategoryStringList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.categorySpinner.adapter = adapter
            }
        } else {
            categoryList = viewmodel.CategoryList
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, viewmodel.CategoryStringList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.categorySpinner.adapter = adapter
        }
    }

    private fun getIdByName(category: String): Int {
        categoryList.forEach {
            if (it.name == category) return it.id
        }
        return 9
    }

}