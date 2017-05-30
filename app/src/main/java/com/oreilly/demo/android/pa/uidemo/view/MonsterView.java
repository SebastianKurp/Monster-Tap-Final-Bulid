package com.oreilly.demo.android.pa.uidemo.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.oreilly.demo.android.pa.uidemo.R;
import com.oreilly.demo.android.pa.uidemo.model.Monster;
import com.oreilly.demo.android.pa.uidemo.model.Monsters;


public class MonsterView extends View { //View for the monsters

    private volatile Monsters Monsters;

    public MonsterView(Context context) {
        super(context);
        setFocusableInTouchMode(true);
    }

    public MonsterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusableInTouchMode(true);
    }

    public MonsterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFocusableInTouchMode(true);
    }

    public float pxToDp(float px) {
        return px / getContext().getResources().getDisplayMetrics().density;}  // convert px to DP based on the density

    int cellSize = 0;
    int numrows = 0;
    int numcolumns = 0;

    public int getNumberOfColumns() {
        return numcolumns;
    }

    public int getNumberOfRows() {
        return numrows;
    }

    public int getCellSize() {
        return cellSize; }

    public void setMonsters(Monsters Monsters) { this.Monsters = Monsters; }//Setter for the monsters

    @Override
    protected void onDraw(Canvas canvas) { //creates the squared grid and scary monsters

        Paint paint = new Paint(); //from the second project

        paint.setStyle(Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(9);

        canvas.drawRect(0, 0, cellSize * numcolumns, cellSize * numrows, paint);
        cellSize = (int) pxToDp(300);
        numrows = getHeight() / getCellSize();
        numcolumns = getWidth() / getCellSize();


        for (int m = 1; m <=numcolumns; m++) {//draws vertical lines
            canvas.drawLine(m * cellSize, 0, m * cellSize, getHeight(), paint);}


        for (int n = 1; n <=numrows; n++) {//draws horizontal lines
            canvas.drawLine(0, n * cellSize, getWidth(), n * cellSize, paint);}

        if (null == Monsters) {
            return; }
        paint.setStyle(Style.FILL);

        Bitmap pmonster = BitmapFactory.decodeResource(getResources(), R.drawable.green_monster);//protected monsters
        Bitmap unpmonster = BitmapFactory.decodeResource(getResources(), R.drawable.blue_monster); //unprotected monsters

        pmonster = Bitmap.createScaledBitmap(pmonster, cellSize, cellSize, true);
        unpmonster = Bitmap.createScaledBitmap(unpmonster, cellSize, cellSize, true);

        //draws Monsters
        for (Monster Monster : Monsters.getMonster()) {
            if (Monster.getProtectedState()) //// TODO: 5/3/2017 check if error goes away
                canvas.drawBitmap(pmonster, Monster.getX(), Monster.getY(), null);
            else
                canvas.drawBitmap(unpmonster, Monster.getX(), Monster.getY(), null);}}}