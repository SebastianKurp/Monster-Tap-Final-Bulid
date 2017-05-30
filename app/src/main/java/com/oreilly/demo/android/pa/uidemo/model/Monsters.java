package com.oreilly.demo.android.pa.uidemo.model;


import com.oreilly.demo.android.pa.uidemo.controller.ClickContainer;
import com.oreilly.demo.android.pa.uidemo.controller.ClickContainerVisitor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sebastian on 4/28/2017.
 */


public class Monsters implements ClickContainer{

    public interface MonstersChangeListener {
        /** @param monsters the Monsters that changed. */
        void onMonstersChange(Monsters monsters);
    }

    private int Scored = 0;
    private int Level = 1;
    private int TapCount = 0;
    private boolean GameOver = false;

    public long getTimeLeft()
    { //how much time is left till game over
        return timeLeft;
    }

    public void setTimeLeft(long timeLeft)
    {
        this.timeLeft = timeLeft;
    }
    private long timeLeft = 100;

    private final LinkedList<Monster> Monsters = new LinkedList<Monster>();
    private final List<Monster> MonsterList = Collections.unmodifiableList(Monsters);
    private MonstersChangeListener MonstersChangeListener;

    public void setMonstersChangeListener(MonstersChangeListener l) { // a parameter of 1 to set the change listener to
       MonstersChangeListener = l;
    }

    public Monster getLastMonster() { //gettermethod for the last monster
        return (Monsters.size() <= 0) ? null : Monsters.getLast(); }

    public List<Monster> getMonster() { //gettermethod for monster
        return MonsterList;
    }

    public void addMonster(float x, float y,  int radius, boolean state) { //adds monsters
        Monsters.add(new Monster(x, y, radius, state));
        notifyListener();
    }

    public void clearMonsters() { ///gets rid of monsters
        Monsters.clear();
        Scored = 0;
        notifyListener();}


    public int countMonsters() { //counts the numbers of monsters
        return Monsters.size();
    }

    public void migrateMonsters(Monster Monster, float newX, float newY, int radius, boolean state) { //mirgrates the dots
        if (null == getMonster(newX, newY)) {
            Monsters.remove(Monster);
            addMonster(newX, newY, radius, state);
            notifyListener();
        }
    }

    public Monster getMonster(int index) {
        return Monsters.get(index);
    }

    public Monster getMonster(float x, float y) {
        for (Monster Monster : Monsters) {
            if (x >= Monster.getX() && y >= Monster.getY()
                    && x <= Monster.getX() + Monster.getRadius() && y <= Monster.getY() + Monster.getRadius())
                return Monster;}
        return null;
    }

    public int getScore() { //gettermethod for the score

        return Scored;
    }

    public int getLevel() { //gettermethod for the level
        return Level;
    }

    public void setScore(int Score) { //settermethod for the score

        this.Scored = Score;
    }

    public void setLevel(int level) { //settermethod for the level

        this.Level = level;
    }

    public int getTapCount() {//gettermethod for tapcount
        return TapCount;
    }


    public void setGameOver() { //is it gameover?
        GameOver = true;
    }

    public boolean getGameOver() { //returns gameover when player loses

        return GameOver;
    }


    public void tapMonster(float x, float y)
    {
        Monster Monster = getMonster(x, y);

        if (Monster != null && !Monster.getProtectedState() )
        {
            Monsters.remove(Monster);
            setScore(getScore()+10);
            TapCount += 1;
            MonstersChangeListener.onMonstersChange(this);

        }
        notifyListener();
    }


    public void setTap(int tap) { //settermethod for kills
        this.TapCount = tap;
    }

    private void notifyListener()
    {
        if (null != MonstersChangeListener)
        {
            MonstersChangeListener.onMonstersChange(this);
        }
    }

    public long getDelay() {  //getter method for the delay
        return delay;
    }

    public void setDelay(
            long delay) { //setter method for the delay
        this.delay = delay; }

    long delay;

    @Override public <Result> Result accept(final ClickContainerVisitor<Result> v)
    {
        return v.setOnMenuItemClickListener(this);
    }


}