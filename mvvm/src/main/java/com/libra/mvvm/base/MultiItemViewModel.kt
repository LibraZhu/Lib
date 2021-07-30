package com.libra.mvvm.base

/**
 * Created by zjf on 2021/3/22
 * RecycleView多布局ItemViewModel是封装
 */
open class MultiItemViewModel<VM : BaseViewModel<*>>(viewModel: VM) : ItemViewModel<VM>(viewModel) {
    var itemType: Any? = null

    fun multiItemType(multiType: Any) {
        itemType = multiType
    }
}