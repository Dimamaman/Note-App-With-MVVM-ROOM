package uz.gita.dimanote.presentation.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.gita.dimanote.R

class MyBottomSheetDialog: BottomSheetDialogFragment(R.layout.bottom_sheet_dialog) {
    private var clickPinButtonListener : (()-> Unit)? = null
    fun setClickPinButtonListener(block: () -> Unit) {
        clickPinButtonListener = block
    }
    override fun getTheme(): Int {
        return R.style.BottomSheetTheme
    }

    private var clickDeleteButtonListener : (()-> Unit)? = null
    fun setClickDeleteButtonListener(block: () -> Unit) {
        clickDeleteButtonListener = block
    }

    private var isPin = 0

    @SuppressLint("SetTextI18n")
    fun isPin(pin: Int) {
        isPin = pin
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isPin == 1) {
            view.findViewById<AppCompatTextView>(R.id.text_pin_unpin).text = "UnPin"
        } else {
            view.findViewById<AppCompatTextView>(R.id.text_pin_unpin).text = "Pin"
        }

        view.findViewById<LinearLayoutCompat>(R.id.linePin).setOnClickListener {
            clickPinButtonListener?.invoke()
        }

        view.findViewById<LinearLayoutCompat>(R.id.lineDelete).setOnClickListener {
            clickDeleteButtonListener?.invoke()
        }
    }
}