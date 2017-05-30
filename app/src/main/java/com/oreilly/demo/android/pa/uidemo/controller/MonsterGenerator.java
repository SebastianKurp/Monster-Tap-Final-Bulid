package com.oreilly.demo.android.pa.uidemo.controller;

import android.os.AsyncTask;
import com.oreilly.demo.android.pa.uidemo.model.Monsters;
import com.oreilly.demo.android.pa.uidemo.view.MonsterView;

import java.util.concurrent.Semaphore;

public final class MonsterGenerator extends AsyncTask<Void, Void, Void> { //Generates the Monsters
    Monsters Monsters;
    MonsterView ViewAs;
    Alert alert;
    boolean StartAs;
    boolean ResetAs;
    boolean SetAs = true;
    boolean LevelsChange = true;
    MonsterWork MWork;
    Semaphore lock;

    public MonsterGenerator(Monsters Monsters, MonsterView view, int level) {
        this.Monsters = Monsters;           //generates monsters
        this.ViewAs = view;           //generate view
        Monsters.setLevel(level);       //generate levels
        alert = new Alert(view, Monsters );
        MWork = new MonsterWork(view);
        Monsters.setDelay(2000);
        lock = new Semaphore(1);}

    @Override
    protected void onProgressUpdate(final Void... params) {
        if (Monsters.getLevel() > 5) {
            alert.gameOverAlert();
            cancel(true);}

        if (Monsters.countMonsters() == 0 || LevelsChange) {


            try {
                MWork.makeMonster(Monsters, ViewAs); } //Creates newMonster in case no Monster is found
            catch (InterruptedException e) { e.printStackTrace(); }
            if (Monsters.getLevel() > 1 && LevelsChange&& SetAs) {
                alert.congratsAlert(Monsters.getLevel() - 1);
                SetAs = false;}
            LevelsChange = false;}
        else {          //move the Monsters on UI
            for (int i = 0; i < Monsters.countMonsters(); i++)
                MWork.moveMonster(Monsters, ViewAs, i);}}

    @Override
    protected Void doInBackground(final Void... params) {
        long time;
        time = System.currentTimeMillis();
        while (!isCancelled()) {
            if (StartAs) {
                publishProgress(null);
                MWork.releaseLock();

                if (Monsters.getScore() == 10 * 5  *   (Monsters.getLevel()*2-1))  {//level is moved up by when all monsters are tapped out
                    Monsters.setLevel(Monsters.getLevel() + 1);
                    Monsters.setDelay(2000 / Monsters.getLevel());
                    SetAs = true;
                    LevelsChange = true;
                    Monsters.setTap(0);}

                try { Thread.sleep(Monsters.getDelay()); }
                catch (InterruptedException e) { e.printStackTrace(); }}}
        return null;}

    @Override
    protected void onCancelled() {
        try {
            lock.acquire(1);
            alert.gameOverAlert();
            Monsters.setLevel(1);
            Monsters.setScore(0);
            Monsters.setTimeLeft(0);
            Monsters.clearMonsters();
            Monsters = null;
            cancel(true);}
         catch(InterruptedException e) { e.printStackTrace(); }
        finally { lock.release(1); }}

    public void setStart(boolean foo){ StartAs = foo; }

    public void setReset(boolean reset){ this.ResetAs = reset; }

    public void res(){
        try { lock.acquire(1); }
        catch(InterruptedException e) { e.printStackTrace(); }
        Monsters.setLevel(1);
        Monsters.setScore(0);
        ResetAs = false;
        StartAs = false;}}