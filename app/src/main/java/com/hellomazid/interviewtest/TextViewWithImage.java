package com.hellomazid.interviewtest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


public class TextViewWithImage extends View {

    private String text;
    private Bitmap image;
    private Paint paint;


    public TextViewWithImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.custom_attribute, 0, 0);
        try {
            text = a.getString(R.styleable.custom_attribute_text_with_image);
        } finally {
            a.recycle();
        }

        image = BitmapFactory.decodeResource(getResources(), R.drawable.liilab);
    }


    protected void onDraw(Canvas canvas) {
        // This part is for drawing multi-line text
        // Using StaticLayout would be a good idea
        // But I learnt StaticLayout after manually implementing this :(
        paint.setTextAlign(Paint.Align.LEFT);
        int textSize = 25;
        paint.setTextSize(textSize);
        paint.setColor(Color.WHITE);
        int start=0, y=textSize+2;
        while(start<text.length()) {
            int end = Math.min(start+30, text.length());
            if(end != text.length()) {
                while(text.charAt(end) != ' ') {
                    end--;
                }
            }
            canvas.drawText(text, start, end, 0, y, paint);
            start = end;
            if(start<text.length() && text.charAt(start)==' ') start++;
            y += textSize+2;
        }

        // This part is for drawing image
        canvas.drawBitmap(image, 0, 0, paint);
    }


}
