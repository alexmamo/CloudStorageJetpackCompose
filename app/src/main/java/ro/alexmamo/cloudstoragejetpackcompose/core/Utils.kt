package ro.alexmamo.cloudstoragejetpackcompose.core

import android.util.Log
import ro.alexmamo.cloudstoragejetpackcompose.core.Constants.TAG

class Utils {
    companion object {
        fun print(e: Exception?) = e?.apply {
            Log.e(TAG, stackTraceToString())
        }
    }
}