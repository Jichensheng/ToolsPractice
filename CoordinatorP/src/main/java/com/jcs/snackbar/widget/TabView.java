package com.jcs.snackbar.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcs.snackbar.R;


public class TabView extends LinearLayout {
    private Context context;
    private TextView mIconText;
    private TextView mTextView;

    public TabView(Context context) {
        this(context,null);
    }

    public TabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);

    }

    public TabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    private void init(Context context,AttributeSet attrs) {
        Typeface iconfont = Typeface.createFromAsset(context.getAssets(), "iconfont.ttf");

        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.TabView);
        String name=ta.getString(R.styleable.TabView_iconText);
        String icon=ta.getString(R.styleable.TabView_iconFont);
        ta.recycle();
        View view = View.inflate(context, R.layout.tab_view, null);
        mIconText = (TextView) view.findViewById(R.id.tab_icon);
        mIconText.setTypeface(iconfont);
        setIcon(icon);

        mTextView = (TextView) view.findViewById(R.id.tab_text);
        setText(name);
        this.addView(view);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setText(CharSequence text) {
        mTextView.setText(text);
    }

    public void setIcon(String icon) {
        mIconText.setText(icon);
    }
}