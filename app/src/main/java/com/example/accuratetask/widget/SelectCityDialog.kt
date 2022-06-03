package com.example.accuratetask.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.Nullable
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.accuratetask.databinding.DialogCityBinding
import com.example.accuratetask.model.entity.City
import com.example.accuratetask.ui.home.adapter.SelectCityAdapter

class SelectCityDialog : DialogFragment() {

    private lateinit var mBinding: DialogCityBinding
    private var mCallback: Callback? = null
    private var mCity : List<City>? = null
    private var mCityAdapter : SelectCityAdapter = SelectCityAdapter()

    fun setCallback(callback: Callback) {
        mCallback = callback
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Callback) {
            mCallback = context
        }
    }

    companion object {
        private const val EXTRA_DATA = "extra_data"
        @JvmStatic fun newInstance(city : List<City>?) = SelectCityDialog().apply {
            arguments = bundleOf(
                EXTRA_DATA to city
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mCity = it.getParcelableArrayList(EXTRA_DATA)
        }

        mCityAdapter = SelectCityAdapter()
    }

    private fun setupRecyclerView(rv : RecyclerView, adapter : SelectCityAdapter) {
        val lm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv.layoutManager = lm
        rv.isNestedScrollingEnabled = true
        rv.itemAnimator = DefaultItemAnimator()
        rv.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mBinding = DialogCityBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onCreateDialog(@Nullable savedInstanceState: Bundle?) : Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(mBinding.rvListCity, mCityAdapter)
        mCity?.let { mCityAdapter.setCityList(it) }
        mCityAdapter.setOnItemClickListener(object : SelectCityAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                val city = mCityAdapter.getItem(position)
                val citySelected = city?.name
                mCallback?.onItemClicked(this@SelectCityDialog, citySelected!!)
            }
        })
    }

    interface Callback {
        fun onItemClicked(dialog: SelectCityDialog, s: String)
    }

}