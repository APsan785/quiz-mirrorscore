package com.apsan.quizbyap.frags

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.apsan.quizbyap.R
import com.apsan.quizbyap.databinding.FragScoresBinding
import com.hoc081098.viewbindingdelegate.viewBinding


class ScoresFrag:Fragment(R.layout.frag_scores) {

    private val binding by viewBinding(FragScoresBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.root
    }
}