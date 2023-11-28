package com.uniandes.vynilsmobile.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.data.model.Comment
import com.uniandes.vynilsmobile.databinding.CommentCreateFragmentBinding
import com.uniandes.vynilsmobile.viewmodel.CommentCreateViewModel


class CommentCreateFragment : Fragment() {

    private var _binding: CommentCreateFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CommentCreateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CommentCreateFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonCreateComment: View = binding.buttonCreateComment
        val buttonCancelComment: View = binding.buttonCancelComment

        val args: CommentCreateFragmentArgs by navArgs()
        val idAlbum = args.album.id!!

        buttonCreateComment.setOnClickListener {

            val rating = binding.editTextRating.text.toString().toInt()
            val commentText = binding.editTextComment.text.toString()

            val comment = Comment(
                rating = rating,
                description = commentText,
            )

            viewModel.addComment(idAlbum, comment)
        }
        buttonCancelComment.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        val bar = (activity as? AppCompatActivity)?.supportActionBar
        bar?.title = getString(R.string.createAlbumCommentTitle)

        val args: CommentCreateFragmentArgs by navArgs()
        val idAlbum = args.album.id

        viewModel = ViewModelProvider(this, CommentCreateViewModel.Factory(activity.application))[CommentCreateViewModel::class.java]
        viewModel.comment.observe(viewLifecycleOwner) { comment ->
            Log.v("CommentCreateFragment", "Comment created successfully. AlbumID->${idAlbum}  ID-> ${comment.id}")
            Toast.makeText(activity, "Se ha creado el comentario exitosamente", Toast.LENGTH_LONG).show()
            findNavController().popBackStack()
        }

        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(
                activity,
                resources.getString(R.string.error_network_connection),
                Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}
