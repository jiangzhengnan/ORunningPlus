package com.oplayer.orunningplus.function.welcome

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewAnimationUtils
import com.oplayer.common.utils.Slog
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.base.BaseActivity
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.main.MainActivity
import kotlinx.android.synthetic.main.activity_welcome.*
import java.lang.Math.hypot

class WelcomeActivity : BaseActivity() {


    override fun getLayoutId(): Int {
        return R.layout.activity_welcome
    }

    override fun initData() {

    }

    override fun initView() {
//        val alphaAnimator = ObjectAnimator.ofFloat(iv_icon, "alpha", 0F, 1F, 0F, 1F)
//        alphaAnimator.duration = 4000
//        alphaAnimator.start()


        rv_bg.viewTreeObserver.addOnGlobalLayoutListener {

            animateBG()

        }


    }

    override fun onResume() {
        super.onResume()


        htv_name.animateText(getString(R.string.app_name));
    }

    var animatorTime = 2000L
    var startTime = 3000L

    private fun animateBG() {
        var ivBG = rv_bg as View
        // 中心点的坐标
        // 中心点的坐标
        val centerX: Int = (ivBG.left + ivBG.right) / 2
        val centerY: Int = (ivBG.top + ivBG.bottom) / 2
        // Math.hypot(x,y):  返回sqrt(x2 +y2)
        // 获取扩散的半径
        // Math.hypot(x,y):  返回sqrt(x2 +y2)
        // 获取扩散的半径
        val finalRadius = hypot(centerX.toDouble(), centerY.toDouble()).toFloat()
//            val finalRadius =(ivBG.left+ivBG.bottom)/2.toFloat()
        // 定义揭露动画
//            val mCircularReveal: Animator = ViewAnimationUtils.createCircularReveal(ivBG, 0, 0, 0F, finalRadius)
        val mCircularReveal: Animator =
            ViewAnimationUtils.createCircularReveal(
                findViewById(R.id.rv_bg),
                centerX,
                centerY,
                0F,
                finalRadius
            )
        // 设置动画持续时间，并开始动画
        mCircularReveal.setDuration(animatorTime).start()


        val alphaAnimator = ObjectAnimator.ofFloat(iv_icon, "alpha", 0F, 1F)
        alphaAnimator.duration = animatorTime
        alphaAnimator.start()

        htv_name.animateText(getString(R.string.app_name));

        Handler().postDelayed(
            Runnable {
                alphaAnimator.end()
                alphaAnimator.cancel()
                mCircularReveal.end()
                mCircularReveal.cancel()

                Slog.d("  跳转界面 ")
                var intent = Intent(this, MainActivity::class.java)

//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                startActivity(intent)

                finish()
            }

            , startTime)

    }


    override fun onRestart() {
        super.onRestart()


    }

    override fun initInfo() {

    }

    override fun onClick(v: View) {

    }

    override fun onGetEvent(event: MessageEvent) {

    }
}
