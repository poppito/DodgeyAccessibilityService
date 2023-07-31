package com.embry.dodgeyaccessibilityservice

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.annotation.SuppressLint
import android.media.AudioManager
import android.media.AudioManager.ADJUST_RAISE
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class DodgeyAccessibilityService : AccessibilityService() {

    private var info = AccessibilityServiceInfo()

    @SuppressLint("ServiceCast")
    private var audioManager: AudioManager? = null

    override fun onCreate() {
        super.onCreate()
        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
    }


    override fun onAccessibilityEvent(p0: AccessibilityEvent?) {
        Log.v("TAGGARTY", "$p0?.isAccessibilityDataSensitive ?: false")

        if (p0?.source?.text == "Increase volume") {
            audioManager?.adjustStreamVolume(AudioManager.STREAM_ACCESSIBILITY, ADJUST_RAISE, 0)
        }
    }

    override fun onInterrupt() {
        // TODO
    }

    override fun onServiceConnected() {
        info.apply {
            // Set the type of events that this service wants to listen to. Others
            // won't be passed to this service.
            eventTypes =
                AccessibilityEvent.TYPE_VIEW_CLICKED or AccessibilityEvent.TYPE_VIEW_FOCUSED

            // Set the type of feedback your service will provide.
            feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN

            // Default services are invoked only if no package-specific ones are present
            // for the type of AccessibilityEvent generated. This service *is*
            // application-specific, so the flag isn't necessary. If this was a
            // general-purpose service, it would be worth considering setting the
            // DEFAULT flag.

            // flags = AccessibilityServiceInfo.DEFAULT;

            notificationTimeout = 100
        }

        this.serviceInfo = info
    }
}