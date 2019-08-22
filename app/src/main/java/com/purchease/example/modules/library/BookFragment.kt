package com.purchease.example.modules.library

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.purchease.example.R
import kotlinx.android.synthetic.main.fragment_book.*


class BookFragment : androidx.fragment.app.Fragment() {

    private lateinit var bookTitle: String
    private lateinit var bookPublishDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.apply {
            bookTitle = getString(KEY_TITLE, "")
            bookPublishDate = getString(KEY_DATE, "")
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        book_title.text = bookTitle
        book_publish_date.text = bookPublishDate
    }



    companion object {

        const val KEY_TITLE = "title"
        const val KEY_DATE = "date"

    }

}
