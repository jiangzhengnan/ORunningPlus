package com.oplayer.orunningplus.function.main.today


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ng.lib_common.base.BaseFragment
import com.oplayer.common.common.SportModel
import com.oplayer.common.utils.Slog
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.bean.SportModelItem
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.main.sport.SportModeSelectAdapter
import com.oplayer.orunningplus.function.main.today.mvp.SportContract
import com.oplayer.orunningplus.function.main.today.mvp.SportPresenter
import kotlinx.android.synthetic.main.fragment_sport.*

/**
 * A simple [Fragment] subclass.
 */
class SportFragment : BaseFragment(), SportContract.View {


    lateinit var mPresenter: SportContract.Presenter


    override fun testMessage(message: String) {


    }

    override fun onGetEvent(event: MessageEvent) {


    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_sport

    }

    override fun initInjector() {
        mPresenter = SportPresenter()
        mPresenter.attachView(this)
    }

    override fun initView() {
        tv_sport_mode.setOnClickListener {

            showPopupWindow(rv_toolbar)

        }
    }

    override fun lazyLoadData() {

        mPresenter.getTestMessage()
    }


    override fun showAlert(message: String, enablePro: Boolean, iconResId: Int, showIcon: Boolean) {


    }

    override fun hideLoading() {


    }

    override fun onError(message: String) {


    }

    override fun onDetach() {
        super.onDetach()
        if (::mPresenter.isInitialized) {
            mPresenter.detachView()
        }


    }

    private var popupWindow = PopupWindow(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
    )

    fun showPopupWindow(view: View) {


        val popupView = LayoutInflater.from(context).inflate(R.layout.pop_sport_model, null)

        var recyclerView = popupView.findViewById<RecyclerView>(R.id.rv_sport_model)

        initPopRV(recyclerView)

        popupWindow.contentView = popupView

        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true
//        popupWindow.animationStyle = R.style.RtcPopupAnimation
        popupWindow.isClippingEnabled = false


        if (!popupWindow.isShowing) {
            popupWindow.showAsDropDown(view)
        }

    }


    var selectModelPosion = SportModel.MODE_ALL

    private fun initPopRV(recyclerView: RecyclerView?) {


        val modes = initSportModel(selectModelPosion)
        var sportModelAdapter = SportModeSelectAdapter(R.layout.item_sport_model, modes)
        recyclerView!!.layoutManager = GridLayoutManager(activity, 4)
        recyclerView.adapter = sportModelAdapter

        sportModelAdapter.setOnItemClickListener { adapter, view, position ->
            var sportModelItem = adapter.data[position] as SportModelItem
            selectModelPosion = sportModelItem.ModelType
            Slog.d("当前选中  position $position   sportModelItem $sportModelItem ")
            tv_sport_mode.text = sportModelItem.ModelTypeStr
            if (popupWindow.isShowing) {
                popupWindow.dismiss()
            }

        }

    }

    private fun initSportModel(selectModel: Int): List<SportModelItem> {
        var models = mutableListOf<SportModelItem>()
        models.add(
            SportModelItem(
                selectModel==SportModel.MODE_ALL,
                R.mipmap.sport_type_all,
                R.mipmap.sport_type_all_gray,
                getString(R.string.sport_type_all),
                SportModel.MODE_ALL
            
            )
        )
        models.add(
            SportModelItem(
                selectModel==SportModel.MODE_CROSS_RUN,
                R.mipmap.sport_type_running,
                R.mipmap.sport_type_running_gray,
                getString(R.string.sport_type_run),
                SportModel.MODE_CROSS_RUN
            )
        )
        models.add(
            SportModelItem(
                selectModel==SportModel.MODE_WALKING,
                R.mipmap.sport_type_walking,
                R.mipmap.sport_type_walking_gray,
                getString(R.string.sport_type_walk),
                SportModel.MODE_WALKING
            )
        )
        models.add(
            SportModelItem(
                selectModel==SportModel.MODE_RUN_INSIDE,
                R.mipmap.sport_type_runindoor,
                R.mipmap.sport_type_runindoor_gray,
                getString(R.string.sport_type_runindoor),
                SportModel.MODE_RUN_INSIDE
            )
        )
        models.add(
            SportModelItem(
                selectModel==SportModel.MODE_CYCLING,
                R.mipmap.sport_type_cycling,
                R.mipmap.sport_type_cycling_gray,
                getString(R.string.sport_type_cycing),
                SportModel.MODE_CYCLING
            )
        )
        models.add(
            SportModelItem(
                selectModel==SportModel.MODE_SWIMMING,
                R.mipmap.sport_type_swimming,
                R.mipmap.sport_type_swimming_gray,
                getString(R.string.sport_type_swim)
                ,
                SportModel.MODE_SWIMMING
            )
        )
        models.add(
            SportModelItem(
                selectModel==SportModel.MODE_HIKING,
                R.mipmap.sport_type_hiking,
                R.mipmap.sport_type_hiking_gray,
                getString(R.string.sport_type_hiking),
                SportModel.MODE_HIKING
            )
        )



    


        return models
    }

}
