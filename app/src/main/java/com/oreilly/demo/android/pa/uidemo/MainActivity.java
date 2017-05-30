package com.oreilly.demo.android.pa.uidemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.TextView;

import com.oreilly.demo.android.pa.uidemo.controller.Alert;
import com.oreilly.demo.android.pa.uidemo.controller.MonsterGenerator;
import com.oreilly.demo.android.pa.uidemo.model.Monster;
import com.oreilly.demo.android.pa.uidemo.model.Monsters;
import com.oreilly.demo.android.pa.uidemo.view.MonsterView;

import java.util.ArrayList;
import java.util.List;




public class MainActivity extends Activity {

    public String TAG = "MonsterTap";
    public static final int Monster_radius= 40;


    private static final class TrackingTouchListener // keeps track of the number of times the screen is touched
            implements View.OnTouchListener {
        private final Monsters mMonsters;
        private List<Integer> tracks = new ArrayList<Integer>();

        TrackingTouchListener(Monsters Monsters) {
            mMonsters = Monsters;
        }

        @Override
        public boolean onTouch(View v, MotionEvent evt) {
            int a, index;
            int action = evt.getAction();
            switch (action & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    index = (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                            >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                    tracks.add(Integer.valueOf(evt.getPointerId(index)));
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    index = (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                            >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                    tracks.remove(Integer.valueOf(evt.getPointerId(index)));
                    break;
                case MotionEvent.ACTION_MOVE:
                    a = evt.getHistorySize();
                    for (Integer i : tracks) {
                        index = evt.findPointerIndex(i.intValue());
                        for (int j = 0; j < a; j++) {
                            mMonsters.tapMonster(evt.getX(), evt.getY());}}
                    break;
                default:
                    return false;}

            for (Integer i : tracks) {
                index = evt.findPointerIndex(i.intValue());
                tapMonster(
                        mMonsters,
                        evt.getX(index),
                        evt.getY(index));}
            return true;}

        private void tapMonster(Monsters Monsters, float x, float y) {
            Monsters.tapMonster(x, y);
        }
            }

    public Monsters MonsterModel = new Monsters();//Final model

    MonsterView MonsterView;// the final view
    int timeleft;

    public MonsterGenerator MonsterGenerator;//Monster generator

    CountDownTimer letscdt;//Starts a timer

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        //creates the view
        setContentView(R.layout.main);

        MonsterView= (MonsterView) findViewById(R.id.monsters);
        MonsterView.setMonsters(MonsterModel);
        MonsterView.setOnCreateContextMenuListener(this);
        MonsterView.setOnTouchListener(new TrackingTouchListener(MonsterModel));

        MonsterGenerator = new MonsterGenerator(MonsterModel, MonsterView, 1);//generates new Monster
        MonsterGenerator.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
        System.out.println("task1");

        MonsterView.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean isFocus) {
                if (!isFocus && (null != MonsterGenerator)) {
                    MonsterGenerator = null;}
                else if (isFocus && (null == MonsterGenerator)) {
                    MonsterGenerator = new MonsterGenerator(MonsterModel, MonsterView, 1);}}});

        findViewById(R.id.button1).setOnClickListener((final View v) -> {
            final TextView tb3 = (TextView) findViewById(R.id.text3);
            final TextView buttonName = (TextView) findViewById(R.id.button1);
            MonsterGenerator.setStart(true);
            Alert alert = new Alert(MonsterView, MonsterModel );
            findViewById(R.id.button1).setEnabled(false);
            if(letscdt!=null){
                letscdt.cancel();}
            letscdt = new CountDownTimer(61000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tb3.setText("Time: " + Integer.toString((int) ((millisUntilFinished / 1000) - 1)) + "s");
                    timeleft = (int) ((millisUntilFinished / 1000) - 1);}

                @Override
                public void onFinish() {
                    alert.gameOverAlert();
                    MonsterGenerator.setStart(false);
                    MonsterModel.setLevel(1);
                    MonsterModel.setScore(0);
                    if(MonsterModel.getMonster().size()!=0){
                        MonsterModel.clearMonsters();}
                    MonsterView.invalidate();
                    findViewById(R.id.button1).setEnabled(true);
                    buttonName.setText("Replay");
                    MonsterGenerator.setReset(true);}}.start();});

        findViewById(R.id.button2).setOnClickListener((final View v) -> { System.exit(0); });

        final TextView tbb1 = (TextView) findViewById(R.id.text1);
        final TextView tbb2 = (TextView) findViewById(R.id.text2);
        final TextView tbb3 = (TextView) findViewById(R.id.text3);

        MonsterModel.setMonstersChangeListener(new Monsters.MonstersChangeListener(){
            @Override
            public void onMonstersChange(Monsters Monsters) {
                final int Scored = Monsters.getScore();
                final int Level =Monsters.getLevel();
                final long time = Monsters.getTimeLeft();
                tbb1.setText("Level : " + String.valueOf(Level));
                tbb2.setText("Score : " + String.valueOf(Scored));
                MonsterView.invalidate();}});}


    //Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.simple_menu, menu);
        return true;}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                MonsterModel.clearMonsters();
                return true;
            default:
                return super.onOptionsItemSelected(item);}}


    @Override
    public void onCreateContextMenu(// menu at the bottom
            ContextMenu menu,
            View v,
            ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, 1, Menu.NONE, "Clear")
                .setAlphabeticShortcut('x');}

    @Override
    public boolean onContextItemSelected(MenuItem item) {// how to select play or quit
        switch (item.getItemId()) {
            case 1:
                MonsterModel.clearMonsters();
                return true;
            default:
                ;
        } return false; }

    private boolean start = false;
    public void isstart(boolean foo){
        if (foo)
            start = true;
        else
            start = false;}}
