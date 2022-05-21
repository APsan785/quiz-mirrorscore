package com.apsan.quizbyap.frags

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import cn.pedant.SweetAlert.SweetAlertDialog
import com.apsan.quizbyap.R
import com.apsan.quizbyap.databinding.FragResultBinding
import com.apsan.quizbyap.viewmodel.QuizViewModel
import com.hoc081098.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class ResultFrag : Fragment(R.layout.frag_result) {

    private val binding by viewBinding(FragResultBinding::bind)
    private val viewmodel: QuizViewModel by activityViewModels()
    private val args : ResultFragArgs by navArgs()
    var score by Delegates.notNull<Int>()
    private lateinit var category : String
    private lateinit var difficulty : String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        score = args.score
        binding.scoreTv.text = "$score/10"
        setCategory_Diff(
            args.category,
            args.difficulty
        )

        binding.goResultToCategBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_resultFrag_to_categoryFrag)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Wanna go back to category selection ?")
//                .setContentText("You won't be able to join again")
                .setConfirmText("Yes lesgoo")
                .setCancelText("No!!!")
                .setConfirmClickListener {
                    view.findNavController().navigate(R.id.action_resultFrag_to_categoryFrag)
                    it.dismissWithAnimation()
                }
                .show()
        }

        binding.root
    }

    private fun setCategory_Diff(categ : String, diff:String){
        category = categ
        difficulty = diff
        binding.categoryTv.text = "$categ\n(${diff.toUpperCase()})"
    }

}