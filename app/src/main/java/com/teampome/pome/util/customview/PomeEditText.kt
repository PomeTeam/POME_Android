package com.teampome.pome.util.customview

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.teampome.pome.R
import com.teampome.pome.databinding.PomeEdittextBinding

/**
 *  TextView를 동적으로 달아 글자수를 표시해주는 EditText
 */
class PomeEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : ConstraintLayout(context, attrs), TextWatcher {
    private var binding: PomeEdittextBinding
    private var maxCount: Int = 0
        set(value) {
            field = value

            binding.pomeCharCountTv.text = context.getString(R.string.edittext_char_count_text, 0 ,maxCount)

            binding.pomeContentAet.filters = arrayOf(InputFilter.LengthFilter(value))
        }

    init {
        binding = PomeEdittextBinding.inflate(LayoutInflater.from(context), this, false)

        addView(binding.root)

        binding.pomeContentAet.addTextChangedListener(this)

        val attr = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PomeEditText,
            0, 0
        )

        try {
            maxCount = attr.getInteger(R.styleable.PomeEditText_setMaxCount, 0)
            binding.pomeContentAet.hint = attr.getString(R.styleable.PomeEditText_setHint)
            binding.pomeContentAet.maxLines = attr.getInteger(R.styleable.PomeEditText_setMaxLines, 1)
        } finally {
            attr.recycle()
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(p0: Editable?) {
        binding.pomeCharCountTv.text = context.getString(R.string.edittext_char_count_text, p0?.length ,maxCount)
    }
}