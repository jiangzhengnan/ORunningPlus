package  com.oplayer.common.mvp

interface IBaseView  {

    fun showAlert(message:String,enablePro: Boolean,iconResId: Int,showIcon: Boolean)
    fun hideLoading()
    fun onError(message:String)

}