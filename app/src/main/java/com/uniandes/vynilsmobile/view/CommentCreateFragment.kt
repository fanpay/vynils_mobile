package com.uniandes.vynilsmobile.view
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.uniandes.vynilsmobile.R


class CommentCreateFragment : Fragment(R.layout.fragment_comment_create) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val editTextRating = view.findViewById<EditText>(R.id.editTextRating)
        val editTextComment = view.findViewById<EditText>(R.id.editTextComment)
        val buttonCreateComment = view.findViewById<Button>(R.id.buttonCreateComment)
        val buttonCancelComment = view.findViewById<Button>(R.id.buttonCancelComment)

        buttonCreateComment.setOnClickListener {

            val rating = editTextRating.text.toString()
            val commentText = editTextComment.text.toString()


            findNavController().popBackStack()
        }
        buttonCancelComment.setOnClickListener {
            cancelCommentCreation()
        }
    }
    private fun cancelCommentCreation() {

        findNavController().popBackStack()
    }
}
