package com.collect.library.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.collect.library.R;

public class LimitedNumberEdit extends RelativeLayout implements TextWatcher {

    private EditText mEdit;
    private TextView mText;
    private int maxLength = 0;

    public LimitedNumberEdit(Context context) {
        super(context);
    }

    public LimitedNumberEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.LimitedNumberEdit);
        maxLength = mTypedArray.getInt(R.styleable.LimitedNumberEdit_maxLength, 0);
        RelativeLayout mRelativeLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.custom_edittext, this, true);
        mEdit = (EditText) mRelativeLayout.findViewById(R.id.edit);
        mText = (TextView) mRelativeLayout.findViewById(R.id.text);
        mText.setHint("还可输入" + maxLength + "字");
        //限定最多可输入多少字符
        mEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        mEdit.addTextChangedListener(this);
    }

    public LimitedNumberEdit(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initUI(Context context) {
        RelativeLayout mRelativeLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.custom_edittext, this, true);
        mEdit = (EditText) mRelativeLayout.findViewById(R.id.edit);
        mText = (TextView) mRelativeLayout.findViewById(R.id.text);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mText.setHint("还可输入" + (maxLength - s.toString().length()) + "字");
        mText.setHint(Html.fromHtml(getResources().getString(R.string.limit_number_edit_notify, (maxLength - s.toString().length()) + "")));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public String getEditTextStr() {
        return mEdit.getText().toString();
    }
}
