package com.example.accuratetask.widget

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.accuratetask.R
import com.example.accuratetask.databinding.WidgetSortDialogBinding

class SortDialog : DialogFragment() {

    private lateinit var mBinding: WidgetSortDialogBinding
    private var sCallback: Callback? = null
    private var mSort: String = ""
    private var mSortList: MutableList<String> = mutableListOf()

    companion object {
        private const val EXTRA_DATA = "extra_data"
        @JvmStatic
        fun newInstance(sort: String?) = SortDialog().apply {
            arguments = bundleOf(
                EXTRA_DATA to sort
            )
        }
    }

    fun setCallback(callback: Callback) {
        sCallback = callback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { b ->
            val s = b.getString(EXTRA_DATA, "")
            if (!s.isNullOrEmpty()) {
                mSort = s
                val strSplit = s.split(",")
                if (strSplit.isNotEmpty()) {
                    for (i in strSplit.indices) {
                        mSortList.add(strSplit[i])
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mBinding = WidgetSortDialogBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateUI()
    }

    private fun populateUI() {
        with(mBinding) {
            chkName.isChecked = (mSortList.size > 0 && mSortList.contains(getString(R.string.name).lowercase()))

            chkName.setOnCheckedChangeListener{ _, b ->
                addOrRemoveSortListItem(b)
            }

            btnOk.setOnClickListener {
                this@SortDialog.dismiss()
                val result = if (mSortList.size > 0) mSortList.joinToString(",") else ""
                if (result != mSort) sCallback?.onChanged(result)
            }
        }
    }

    private fun addOrRemoveSortListItem(b: Boolean) {
        val s = "name"
        if (b) {
            mSortList.add(s)
        } else {
            mSortList.remove(s)
        }

        if (mSortList.size > 0) mSortList.sort()
    }

    interface Callback {
        fun onChanged(sort: String)
    }

}