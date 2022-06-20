package de.baumann.browser.view.dialog.compose

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import de.baumann.browser.Ninja.R
import de.baumann.browser.database.Bookmark
import de.baumann.browser.preference.ConfigManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class ComposeDialogFragment: AppCompatDialogFragment(), KoinComponent {
    protected val config: ConfigManager by inject()
    protected lateinit var composeView: ComposeView


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NO_FRAME, 0)
        return super.onCreateDialog(savedInstanceState)
    }

    protected fun setupDialog() {
        dialog?.apply {
            window?.setGravity((if (config.isToolbarOnTop) Gravity.CENTER else Gravity.BOTTOM) or Gravity.END)
            window?.setBackgroundDrawableResource(R.drawable.background_with_border_margin)
            window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            setCanceledOnTouchOutside(true)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setupDialog()

        composeView = ComposeView(requireContext())
        setupComposeView()

        return composeView
    }

    abstract fun setupComposeView()

}

fun Dialog.runClickAndDismiss(config: ConfigManager?, action: ()-> Unit) {
    config ?: return
    action()
    dismiss()
}

@Composable
fun HorizontalSeparator() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color = MaterialTheme.colors.onBackground)
    )
}

@Composable
fun VerticalSeparator() {
    Spacer(
        modifier = Modifier
            .width(1.dp)
            .height(30.dp)
            .background(color = MaterialTheme.colors.onBackground)
    )
}
