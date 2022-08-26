# CircleProgressBar
Hello Android Devloper 

How to get Library

Step 1. Add the JitPack repository to your build file

	allprojects{
		   repositories{
		
			maven { url 'https://jitpack.io' }
			
		   }
        }
 
Step 2. Add the dependency
 
	dependencies{
		implementation 'com.github.TonRobotic:CircleProgressBar:1.0.1'
	}

 
How to use.

xml

	<?xml version="1.0" encoding="utf-8"?>
	<androidx.constraintlayout.widget.ConstraintLayout
	    xmlns:android="http://schemas.android.com/apk/res/android"
	    xmlns:app="http://schemas.android.com/apk/res-auto"
	    xmlns:tools="http://schemas.android.com/tools"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent"
	    tools:context=".MainActivity">

	    <com.mt.circleprogressbar.Progressbar
		android:id="@+id/progressbar"
		android:layout_width="120dp"
		android:layout_height="120dp"
		app:scaleColor="@android:color/darker_gray"
		app:valueColor="@android:color/holo_blue_dark"
		app:scaleMode="oval"
		app:borderProgressBarSize="8dp"
		app:layout_constraintBottom_toBottomOf="parent"
		app:layout_constraintEnd_toEndOf="parent"
		app:layout_constraintStart_toStartOf="parent"
		app:layout_constraintTop_toTopOf="parent" />

	</androidx.constraintlayout.widget.ConstraintLayout>
	
Activity Class

	class MainActivity : AppCompatActivity() {

	    lateinit var mainBinding:ActivityMainBinding
	    lateinit var valueAnimator:ValueAnimator

	    override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		mainBinding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(mainBinding.root)

		valueAnimator = ValueAnimator.ofInt(0,65) // offset value progressbar 0 .. 100 
		valueAnimator.apply {
		    duration = 2000 // time delay in millisec (ms)
		    addUpdateListener(mainBinding.progressbar) 
		}.start()

	    }
	}

