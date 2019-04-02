package rz.com.catolico.interfaces

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Adapter

interface IFragment{

    fun changeView(layout: Int, view: ViewGroup, mInflater: LayoutInflater, mContainer: ViewGroup?) {
        val newView = mInflater.inflate(layout, mContainer, false)
        view.removeAllViews()
        view.addView(newView)
    }

    fun setupAdapter()


}