package com.example.rectangularsapp.presentation.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.example.rectangularsapp.databinding.ActivityMainBinding
import com.example.rectangularsapp.di.ViewModelProviderFactory
import com.example.rectangularsapp.presentation.viewmodel.MainViewModel
import com.example.rectangularsapp.presentation.viewstate.MainViewAction
import com.example.rectangularsapp.presentation.viewstate.MainViewState
import com.example.rectangularsapp.util.MyApplication
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    private lateinit var dis: DisplayMetrics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MyApplication).provideAppComponent().inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dis = resources.displayMetrics

        viewModel.postAction(
            MainViewAction.StoreScreenMetrics(
                width = dis.widthPixels.toFloat(),
                height = dis.heightPixels.toFloat(),
                time = System.currentTimeMillis()
            )
        )

        observeOnViewViewState()

        rect1TouchListener()
        rect2TouchListener()
    }

    private fun observeOnViewViewState() {
        viewModel.viewState.observe(this) { viewState ->
            binding.progressBar.isVisible = viewState is MainViewState.Loading
            binding.successGroup.isVisible = viewState is MainViewState.Success

            when (viewState) {
                MainViewState.Loading -> {}
                is MainViewState.Success -> {
                    binding.rectangleView1.apply {
                        updateLayoutParams<ConstraintLayout.LayoutParams> {
                            horizontalBias = viewState.rectangles[0].x
                            verticalBias = viewState.rectangles[0].y

                            height = viewState.rectangles[0].height.toInt()
                            width = viewState.rectangles[0].width.toInt()
                        }
                    }
                    binding.rectangleView2.apply {
                        updateLayoutParams<ConstraintLayout.LayoutParams> {
                            horizontalBias = viewState.rectangles[1].x
                            verticalBias = viewState.rectangles[1].y

                            height = viewState.rectangles[1].height.toInt()
                            width = viewState.rectangles[1].width.toInt()
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun rect1TouchListener() {

        binding.rectangleView1.setOnTouchListener { view, motionEvent ->
            when (motionEvent.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    val newX = motionEvent.rawX - view.width
                    val newY = motionEvent.rawY
                    val vBias = (newY + (view.height/2))/dis.heightPixels
                    val hBias = (newX + (view.width/2))/dis.widthPixels

                    viewModel.postAction(MainViewAction.UpdateRectangle1(
                        x = hBias,
                        y = vBias,
                        time = System.currentTimeMillis()
                    ))
                }
                MotionEvent.ACTION_MOVE -> {
                    val newX = motionEvent.rawX - view.width
                    val newY = motionEvent.rawY
                    val vBias = (newY + (view.height/2))/dis.heightPixels
                    val hBias = (newX + (view.width/2))/dis.widthPixels

                    viewModel.postAction(MainViewAction.UpdateRectangle1(
                        x = hBias,
                        y = vBias,
                        time = System.currentTimeMillis()
                    ))
                }
                else -> return@setOnTouchListener false
            }
            return@setOnTouchListener true
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun rect2TouchListener() {
        binding.rectangleView2.setOnTouchListener { view, motionEvent ->
            when (motionEvent.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    val newX = motionEvent.rawX - view.width
                    val newY = motionEvent.rawY
                    val vBias = (newY + (view.height/2))/dis.heightPixels
                    val hBias = (newX + (view.width/2))/dis.widthPixels

                    viewModel.postAction(MainViewAction.UpdateRectangle2(
                        x = hBias,
                        y = vBias,
                        time = System.currentTimeMillis()
                    ))
                }
                MotionEvent.ACTION_MOVE -> {
                    val newX = motionEvent.rawX - view.width
                    val newY = motionEvent.rawY
                    val vBias = (newY + (view.height/2))/dis.heightPixels
                    val hBias = (newX + (view.width/2))/dis.widthPixels

                    viewModel.postAction(MainViewAction.UpdateRectangle2(
                        x = hBias,
                        y = vBias,
                        time = System.currentTimeMillis()
                    ))
                }
                else -> return@setOnTouchListener false
            }
            return@setOnTouchListener true
        }
    }
}