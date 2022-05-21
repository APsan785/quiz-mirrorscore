package com.apsan.quizbyap.frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.apsan.quizbyap.R
import com.apsan.quizbyap.databinding.FragHomeBinding
import com.hoc081098.viewbindingdelegate.viewBinding


class HomeFrag:Fragment(R.layout.frag_home) {

    private val binding by viewBinding(FragHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.goBtn.setOnClickListener{
            view.findNavController().navigate(R.id.action_homeFrag_to_categoryFrag)
        }

//        binding.scoresBtn.setOnClickListener{
//            view.findNavController().navigate(R.id.action_homeFrag_to_scoresFrag)
//        }

        binding.root
    }
// TODO : Scores Fragment code
}