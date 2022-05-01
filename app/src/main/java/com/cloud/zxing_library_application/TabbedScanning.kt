package com.cloud.zxing_library_application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.*
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import com.journeyapps.barcodescanner.CameraPreview
import com.journeyapps.barcodescanner.DecoratedBarcodeView


class TabbedScanning : AppCompatActivity(), ActionBar.TabListener {
    /**
     * The [PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * [FragmentPagerAdapter] derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null


    /**
     * The [ViewPager] that will host the section contents.
     */
    private var mViewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabbed_scanning)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container)
        mViewPager!!.adapter = mSectionsPagerAdapter

        // Set up the action bar.
        val actionBar = supportActionBar
        actionBar!!.navigationMode = ActionBar.NAVIGATION_MODE_TABS

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager!!.setOffscreenPageLimit(0)
        mViewPager!!.addOnPageChangeListener(object : SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                actionBar.setSelectedNavigationItem(position)
            }
        })

        // For each of the sections in the app, add a tab to the action bar.
        for (i in 0 until mSectionsPagerAdapter!!.count) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                actionBar.newTab()
                    .setText(mSectionsPagerAdapter!!.getPageTitle(i))
                    .setTabListener(this)
            )
        }
    }

    override fun onTabSelected(tab: ActionBar.Tab, fragmentTransaction: FragmentTransaction?) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager!!.currentItem = tab.position
    }

    override fun onTabUnselected(tab: ActionBar.Tab?, fragmentTransaction: FragmentTransaction?) {}

    override fun onTabReselected(tab: ActionBar.Tab?, fragmentTransaction: FragmentTransaction?) {}

    /**
     * A placeholder fragment containing a simple view.
     */
    class ScanFragment : Fragment() {
        var barcodeView: DecoratedBarcodeView? = null
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            val rootView: View =
                inflater.inflate(R.layout.fragment_tabbed_scanning, container, false)
            barcodeView = rootView.findViewById(R.id.barcode_view)
            return rootView
        }

        override fun setUserVisibleHint(isVisibleToUser: Boolean) {
            super.setUserVisibleHint(isVisibleToUser)
            if (barcodeView != null) {
                if (isVisibleToUser) {
                    barcodeView!!.resume()
                } else {
                    barcodeView!!.pauseAndWait()
                }
            }
        }

        override fun onPause() {
            super.onPause()
            barcodeView!!.pauseAndWait()
        }

        override fun onResume() {
            super.onResume()
            barcodeView!!.resume()
        }

        companion object {
            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(): ScanFragment {
                return ScanFragment()
            }
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    class CameraFragment : Fragment() {
        private var cameraPreview: CameraPreview? = null
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            val rootView: View = inflater.inflate(R.layout.fragment_tabbed_camera, container, false)
            cameraPreview = rootView.findViewById(R.id.camera_preview)
            return rootView
        }

        override fun setUserVisibleHint(isVisibleToUser: Boolean) {
            super.setUserVisibleHint(isVisibleToUser)
            if (cameraPreview != null) {
                if (isVisibleToUser) {
                    cameraPreview!!.resume()
                } else {
                    cameraPreview!!.pauseAndWait()
                }
            }
        }

        override fun onPause() {
            super.onPause()
            cameraPreview!!.pauseAndWait()
        }

        override fun onResume() {
            super.onResume()
        }

        companion object {
            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(): CameraFragment {
                return CameraFragment()
            }
        }
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    class SectionsPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
        override fun getItem(position: Int): Fragment {
            return if (position == 0) {
                ScanFragment.newInstance()
            } else {
                CameraFragment.newInstance()
            }
        }

        override fun getCount(): Int {
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return "Scan"
                1 -> return "Camera"
            }
            return null
        }
    }
}