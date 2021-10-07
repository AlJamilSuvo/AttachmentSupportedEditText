package al.jamil.suvo.keyboardattachment

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import androidx.core.view.inputmethod.EditorInfoCompat
import androidx.core.view.inputmethod.InputConnectionCompat
import androidx.core.view.inputmethod.InputConnectionCompat.OnCommitContentListener
import java.lang.Exception

class AttachmentSupportedEditText : androidx.appcompat.widget.AppCompatEditText {
    constructor(context: Context, attr: AttributeSet?) : super(context, attr)
    constructor(context: Context) : super(context)
    constructor(context: Context, attr: AttributeSet?, defStyle: Int) : super(
        context,
        attr,
        defStyle
    )

    var imageKeyboardSupportMediaListener: ImageKeyboardSupportMediaListener? = null

    override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection {
        val inputConnection = super.onCreateInputConnection(outAttrs)
        val contentMimeTypes = arrayOf("image/gif", "image/png", "image/jpg")
        EditorInfoCompat.setContentMimeTypes(outAttrs, contentMimeTypes)
        val callback =
            OnCommitContentListener { inputContentInfo, flags, _ ->
                if (imageKeyboardSupportMediaListener == null) {
                    true
                } else {
                    Log.d(
                        "Image Support Keyboard",
                        inputContentInfo.contentUri.toString() + " flags: " + flags + " permission=" + 1
                    )
                    try {
                        inputContentInfo.requestPermission()
                    } catch (var5: Exception) {
                        var5.printStackTrace()
                        return@OnCommitContentListener false
                    }
                    run {
                        imageKeyboardSupportMediaListener?.onMediaSelected(
                            inputContentInfo.contentUri
                        )
                        true
                    }
                }
            }
        return InputConnectionCompat.createWrapper(inputConnection!!, outAttrs, callback)
    }

}