package  com.oplayer.common.mvp

interface IBasePresenter<in V : IBaseView> {

    fun attachView(mRootView: V)

    fun detachView()

}
