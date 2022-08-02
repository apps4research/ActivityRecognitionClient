package eu.a4r.activityrecognition

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.DetectedActivity

class MainActivity : AppCompatActivity() {

    private val tag = MainActivity::class.java.simpleName
    private lateinit var broadcastReceiver: BroadcastReceiver

    private lateinit var txtActivity: TextView
    private lateinit var txtConfidence: TextView
    private lateinit var btnStartTracking: Button
    private lateinit var btnStopTracking: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtActivity = findViewById(R.id.txt_activity)
        txtConfidence = findViewById(R.id.txt_confidence)
        btnStartTracking = findViewById(R.id.btn_start_tracking)
        btnStopTracking = findViewById(R.id.btn_stop_tracking)

        btnStartTracking.setOnClickListener { startTracking() }
        btnStopTracking.setOnClickListener { stopTracking() }

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == BROADCAST_DETECTED_ACTIVITY) {
                    val type = intent.getIntExtra("type", -1)
                    val confidence = intent.getIntExtra("confidence", 0)
                    handleUserActivity(type, confidence)
                }
            }
        }
        startTracking()
    }
    private fun handleUserActivity(type: Int, confidence: Int) {
        var label = getString(R.string.activity_unknown)
        when (type) {
            DetectedActivity.IN_VEHICLE -> {
                label = getString(R.string.activity_in_vehicle)
            }
            DetectedActivity.ON_BICYCLE -> {
                label = getString(R.string.activity_on_bicycle)
            }
            DetectedActivity.ON_FOOT -> {
                label = getString(R.string.activity_on_foot)
            }
            DetectedActivity.RUNNING -> {
                label = getString(R.string.activity_running)
            }
            DetectedActivity.STILL -> {
                label = getString(R.string.activity_still)
            }
            DetectedActivity.TILTING -> {
                label = getString(R.string.activity_tilting)
            }
            DetectedActivity.WALKING -> {
                label = getString(R.string.activity_walking)
            }
            DetectedActivity.UNKNOWN -> {
                label = getString(R.string.activity_unknown)
            }
        }
        Log.e(tag, "User activity: $label, Confidence: $confidence")
        if (confidence > CONFIDENCE) {
            txtActivity.text = label
            txtConfidence.text = "Confidence: $confidence"
        }
    }
    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
            IntentFilter(BROADCAST_DETECTED_ACTIVITY)
        )
    }
    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    }
    private fun startTracking() {
        val intent = Intent(this@MainActivity, BackgroundDetectedActivitiesService::class.java)
        startService(intent)
    }
    private fun stopTracking() {
        val intent = Intent(this@MainActivity, BackgroundDetectedActivitiesService::class.java)
        stopService(intent)
    }

    companion object {
        val BROADCAST_DETECTED_ACTIVITY = "activity_intent"
        internal val DETECTION_INTERVAL_IN_MILLISECONDS: Long = 1000
        val CONFIDENCE = 70
    }
}