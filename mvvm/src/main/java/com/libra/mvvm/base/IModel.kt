package com.libra.mvvm.base

/**
 * Created by zjf on 2021/3/22
 */
interface IModel {
    /**
     * ViewModel销毁时清除Model，与ViewModel共消亡。Model层同样不能持有长生命周期对象
     */
    fun onCleared()
}