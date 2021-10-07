package al.jamil.suvo.keyboardattachment

import android.net.Uri


fun interface ImageKeyboardSupportMediaListener {
    fun onMediaSelected(uri: Uri)
}
