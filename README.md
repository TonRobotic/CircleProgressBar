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

	class MainActivity : AppCompatActivity() {

	    lateinit var mainBinding:ActivityMainBinding
	    lateinit var valueAnimator:ValueAnimator

	    override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		mainBinding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(mainBinding.root)

		valueAnimator = ValueAnimator.ofInt(0,65) // offset value progress bar 0 .. 100 
		valueAnimator.apply {
		    duration = 2000 // time delay in millisec (ms)
		    addUpdateListener(mainBinding.progressbar) 
		}.start()

	    }
	}

