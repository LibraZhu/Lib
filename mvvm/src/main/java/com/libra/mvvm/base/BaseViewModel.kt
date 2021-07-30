package com.libra.mvvm.base

import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.libra.mvvm.base.event.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import java.util.*

/**
 * Created by zjf on 2021/3/22
 */
class BaseViewModel<M : BaseModel?> constructor(var model: M? = null) : ViewModel(), IBaseViewModel,
    Consumer<Disposable> {
    //管理RxJava，主要针对RxJava异步操作造成的内存泄漏
    private var mCompositeDisposable: CompositeDisposable?
    protected fun addDisposable(disposable: Disposable?) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(disposable!!)
    }

    var uiEvent = UIEvent()

    fun showToast(message: String?) {
        if (!TextUtils.isEmpty(message)) {
            uiEvent.showToastEvent.postValue(message)
        }
    }

    @JvmOverloads
    fun showProgressDialog(title: String? = "") {
        uiEvent.showDialogEvent.postValue(title)
    }

    fun dismissProgressDialog() {
        uiEvent.dismissDialogEvent.call()
    }
    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    @JvmOverloads
    fun startActivity(clz: Class<*>, bundle: Bundle? = null) {
        val params: MutableMap<String, Any> = HashMap()
        params[ParameterField.CLASS] = clz
        if (bundle != null) {
            params[ParameterField.BUNDLE] = bundle
        }
        uiEvent.startActivityEvent.postValue(params)
    }

    /**
     * 跳转页面,请求返回
     *
     * @param clz         所跳转的目的Activity类
     * @param requestCode 请求码
     */
    fun startActivityForResult(clz: Class<*>, requestCode: Int) {
        startActivityForResult(clz, null, requestCode)
    }

    /**
     * 跳转页面,请求返回
     *
     * @param clz         所跳转的目的Activity类
     * @param bundle      跳转所携带的信息
     * @param requestCode 请求码
     */
    fun startActivityForResult(clz: Class<*>, bundle: Bundle?, requestCode: Int) {
        val params: MutableMap<String, Any> = HashMap()
        params[ParameterField.CLASS] = clz
        params[ParameterField.REQUESTCODE] = requestCode
        if (bundle != null) {
            params[ParameterField.BUNDLE] = bundle
        }
        uiEvent.startActivityForResultEvent.postValue(params)
    }
    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     */
    @JvmOverloads
    fun startContainerActivity(canonicalName: String, bundle: Bundle? = null) {
        val params: MutableMap<String, Any> = HashMap()
        params[ParameterField.CANONICAL_NAME] = canonicalName
        if (bundle != null) {
            params[ParameterField.BUNDLE] = bundle
        }
        uiEvent.startContainerActivityEvent.postValue(params)
    }

    /**
     * 关闭界面,返回上一级
     *
     * @param bundle 跳转所携带的信息
     */
    fun finishForResult(bundle: Bundle?) {
        val params: MutableMap<String, Any> = HashMap()
        if (bundle != null) {
            params[ParameterField.BUNDLE] = bundle
        }
        uiEvent.finishForResultEvent.postValue(params)
    }

    /**
     * 关闭界面
     */
    fun finish() {
        uiEvent.finishEvent.call()
    }

    /**
     * 返回上一层
     */
    fun onBackPressed() {
        uiEvent.onBackPressedEvent.call()
    }

    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?) {}
    override fun onCreate() {}
    override fun onDestroy() {}
    override fun onStart() {}
    override fun onStop() {}
    override fun onResume() {}
    override fun onPause() {}
    override fun registerRxBus() {}
    override fun removeRxBus() {}
    override fun onCleared() {
        super.onCleared()
        if (model != null) {
            model!!.onCleared()
        }
        //ViewModel销毁时会执行，同时取消所有异步任务
        if (mCompositeDisposable != null) {
            mCompositeDisposable!!.clear()
        }
    }

    override fun accept(disposable: Disposable?) {
        addDisposable(disposable)
    }

    inner class UIEvent {
        var showToastEvent = SingleLiveEvent<String>()
        var showDialogEvent = SingleLiveEvent<String>()
        var dismissDialogEvent = SingleLiveEvent<Void>()
        var startActivityEvent = SingleLiveEvent<Map<String, Any>>()
        var startActivityForResultEvent = SingleLiveEvent<Map<String, Any>>()
        var startContainerActivityEvent = SingleLiveEvent<Map<String, Any>>()
        var finishEvent = SingleLiveEvent<Void>()
        var finishForResultEvent = SingleLiveEvent<Map<String, Any>>()
        var onBackPressedEvent = SingleLiveEvent<Void>()
    }

    object ParameterField {
        var CLASS = "CLASS"
        var CANONICAL_NAME = "CANONICAL_NAME"
        var BUNDLE = "BUNDLE"
        var REQUESTCODE = "REQUESTCODE"
    }

    init {
        mCompositeDisposable = CompositeDisposable()
    }
}