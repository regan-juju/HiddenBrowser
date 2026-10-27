package acr.browser.lightning.permissions

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Callback for a runtime permission request made through [PermissionsManager].
 */
abstract class PermissionsResultAction {
    abstract fun onGranted()
    open fun onDenied(permission: String?) = Unit
}

/**
 * A small runtime permissions helper, replacing the discontinued
 * com.anthonycr.grant:permissions library (no longer resolvable now that
 * jcenter is shut down).
 */
class PermissionsManager private constructor() {

    private class PendingRequest(
        val permissions: Set<String>,
        val action: PermissionsResultAction?
    )

    private var nextRequestCode = 1000
    private val pendingRequests = mutableListOf<PendingRequest>()

    fun hasPermission(activity: Activity?, permission: String): Boolean =
        activity != null &&
            ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED

    fun requestPermissionsIfNecessaryForResult(
        activity: Activity,
        permissions: Array<String>,
        action: PermissionsResultAction?
    ) {
        val missing = permissions.filter {
            ContextCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED
        }
        if (missing.isEmpty()) {
            action?.onGranted()
            return
        }
        pendingRequests.add(PendingRequest(missing.toSet(), action))
        ActivityCompat.requestPermissions(activity, missing.toTypedArray(), nextRequestCode++)
    }

    fun notifyPermissionsChange(permissions: Array<String>, grantResults: IntArray) {
        val results = permissions.zip(grantResults.toList())
        val iterator = pendingRequests.iterator()
        while (iterator.hasNext()) {
            val pending = iterator.next()
            if (pending.permissions.any { permission -> results.any { it.first == permission } }) {
                val denied = results.firstOrNull { (permission, result) ->
                    permission in pending.permissions && result != PackageManager.PERMISSION_GRANTED
                }
                if (denied == null) {
                    pending.action?.onGranted()
                } else {
                    pending.action?.onDenied(denied.first)
                }
                iterator.remove()
            }
        }
    }

    companion object {
        private val instance = PermissionsManager()

        @JvmStatic
        fun getInstance(): PermissionsManager = instance
    }
}
