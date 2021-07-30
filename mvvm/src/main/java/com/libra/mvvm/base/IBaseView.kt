package com.libra.mvvm.base

/**
 * Created by zjf on 2021/3/22
 */
interface IBaseView {
    /**
     * 初始化界面传递参数
     */
    fun initParam()

    /**
     * 初始化一些view
     */
    fun initView()

    /**
     * 初始化数据
     */
    fun initData()

    /**
     * 初始化界面观察者的监听
     */
    fun initViewObservable()
}