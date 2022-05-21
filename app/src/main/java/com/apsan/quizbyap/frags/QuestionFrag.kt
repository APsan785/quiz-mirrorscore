package com.apsan.quizbyap.frags

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import cn.pedant.SweetAlert.SweetAlertDialog
import com.apsan.quizbyap.R
import com.apsan.quizbyap.databinding.FragQuestionBinding
import com.apsan.quizbyap.viewmodel.QuizViewModel
import com.hoc081098.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QuestionFrag : Fragment(R.layout.frag_question) {

    private val binding by viewBinding(FragQuestionBinding::bind)
    private val args: QuestionFragArgs by navArgs()
    lateinit var difficulty: String
    lateinit var category: String
    private val viewModel: QuizViewModel by activityViewModels()
    val TAG = "tag"
    private lateinit var options_btn_list : Array<Button>

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        difficulty = args.difficulty
        category = args.category
        Log.d(TAG, "onViewCreated: $category")

        binding.topic.text = underlinedCategory(category)


        viewModel.score.value = 0
        viewModel.questionNumber.value = 1

        viewModel.score.observe(viewLifecycleOwner) {
            binding.scoreTv.text = "Score : $it"
        }

        Log.d(TAG, "onViewCreated: ${viewModel.questionList.size}")

        options_btn_list = arrayOf(
            binding.op1,
            binding.op2,
            binding.op3,
            binding.op4
        )

        displayQuestions()
        options_listener(*options_btn_list)
        continue_next_question()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){

            SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Do you want to quit this quiz ?")
                .setContentText("You won't be able to join again")
                .setConfirmText("Yes quit")
                .setCancelText("No!!!")
                .setConfirmClickListener {
                    viewModel.questionList.clear()
                    view.findNavController().navigateUp()
                    it.dismissWithAnimation()
                }
                .show()

        }

        binding.root
    }

    private fun underlinedCategory(category: String): CharSequence {
        val content = SpannableString(category)
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        return content
    }

    private fun continue_next_question() {
        binding.continueBtn.setOnClickListener { ctn_btn ->
            viewModel.questionNumber.value = viewModel.questionNumber.value!! + 1
            options_btn_list.forEach {
                it.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.white))
            }
            ctn_btn.visibility = View.GONE
        }
    }

    private fun options_listener(
        vararg btn: Button
    ) {
        btn.forEach { btn_ ->
            btn_.setOnClickListener {
                SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Are you sure?")
//                    .setContentText("Won't be able to recover this file!")
                    .setConfirmText("Yes, confirm this")
                    .setCancelText("No wait")
                    .setConfirmClickListener { sweet_dialog ->
                        val correct_ans = viewModel.questionList[viewModel.questionNumber.value?.minus(1)!!].correctAnswer!!.format_html()
                        if (btn_.text == correct_ans) {
                            Toast.makeText(
                                requireContext(),
                                "Correct Answer dude !!!",
                                Toast.LENGTH_SHORT
                            ).show()
                            viewModel.score.value = viewModel.score.value!! + 1
                            binding.continueBtn.visibility = View.VISIBLE
                            btn_.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.correct_option_green));
//                            viewModel.questionNumber.value = viewModel.questionNumber.value!! + 1
                        }else{
                            Toast.makeText(
                                requireContext(),
                                "Wrong Answer, be careful",
                                Toast.LENGTH_SHORT
                            ).show()
//                            viewModel.questionNumber.value = viewModel.questionNumber.value!! + 1
                            btn_.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.wrong_option_red));
                            btn.forEach {
                                if(it.text == correct_ans) it.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.correct_option_green))
                            }
                            binding.continueBtn.visibility = View.VISIBLE

                        }
                        sweet_dialog.dismiss()
                    }
                    .show()
            }
        }
    }

    private fun displayQuestions() {
        viewModel.questionNumber.observe(viewLifecycleOwner) {
            try {
                when(viewModel.questionNumber.value?.rem(2)){
                    0 -> binding.scrollMain.setBackgroundColor(resources.getColor(R.color.ques_bg1))
                    1 -> binding.scrollMain.setBackgroundColor(resources.getColor(R.color.ques_bg2))
                }
                val current_question = viewModel.questionList[it - 1]
                binding.question.text = current_question.question?.format_html()
                binding.qNo.text = "$it."
                val current_option_no = (1..4).random()
                when (current_option_no) {
                    1 -> {
                        binding.op1.text = current_question.correctAnswer?.format_html()
                        binding.op2.text = current_question.incorrectAnswers[0].format_html()
                        binding.op3.text = current_question.incorrectAnswers[1].format_html()
                        binding.op4.text = current_question.incorrectAnswers[2].format_html()
                    }
                    2 -> {
                        binding.op2.text = current_question.correctAnswer?.format_html()
                        binding.op1.text = current_question.incorrectAnswers[0].format_html()
                        binding.op3.text = current_question.incorrectAnswers[1].format_html()
                        binding.op4.text = current_question.incorrectAnswers[2].format_html()
                    }
                    3 -> {
                        binding.op3.text = current_question.correctAnswer?.format_html()
                        binding.op2.text = current_question.incorrectAnswers[0].format_html()
                        binding.op1.text = current_question.incorrectAnswers[1].format_html()
                        binding.op4.text = current_question.incorrectAnswers[2].format_html()
                    }
                    4 -> {
                        binding.op4.text = current_question.correctAnswer?.format_html()
                        binding.op2.text = current_question.incorrectAnswers[0].format_html()
                        binding.op3.text = current_question.incorrectAnswers[1].format_html()
                        binding.op1.text = current_question.incorrectAnswers[2].format_html()
                    }
                }

            }catch (e : IndexOutOfBoundsException){
                val action = QuestionFragDirections.actionQuestionFragToResultFrag(
                    category = category,
                    difficulty = difficulty,
                    score = viewModel.score.value!!
                )
                viewModel.questionList.clear()
                requireView().findNavController().navigate(action)
            }
        }
    }

    private fun String.format_html():String{
//        this.replace("&#039", "'")
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            Html.fromHtml(this).toString()
        }
    }


}

