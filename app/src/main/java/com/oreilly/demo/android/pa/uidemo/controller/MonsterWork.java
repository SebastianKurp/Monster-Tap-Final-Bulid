package com.oreilly.demo.android.pa.uidemo.controller;

import com.oreilly.demo.android.pa.uidemo.model.Monster;
import com.oreilly.demo.android.pa.uidemo.model.Monsters;
import com.oreilly.demo.android.pa.uidemo.view.MonsterView;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class MonsterWork {
    private final int MAX;
    private final Semaphore available;

    public MonsterWork(MonsterView view) {
        MAX = view.getNumberOfColumns() * view.getNumberOfRows();
        available = new Semaphore(MAX, true);}

    public void getLock() throws InterruptedException { available.acquire(10); }

    public void releaseLock() { available.release(10); }

    /* Make the Monsters on UI
     * @param Monsters
     * @param view
     * @throws InterruptedException
     */
    void makeMonster(Monsters Monsters, MonsterView view) throws InterruptedException {
        Random rand = new Random();
        int cellSize = view.getCellSize();
        int rows = view.getNumberOfRows();
        int cols = view.getNumberOfColumns();
        int initMonsters;
        if (cols > 0 && rows > 0) {
            Monsters.setTap(0);
            /*The level will determine the number of Dots appearing on the screen*/
            initMonsters = (Monsters.getLevel() * 5) % (40);
            for (int i = 0; i < initMonsters; i++) {
                int x = rand.nextInt(cols);
                int y = rand.nextInt(rows);
                boolean state = rand.nextBoolean();
                try {
                    this.getLock();
                    Monsters.addMonster(cellSize * x, cellSize * y, cellSize, state);}
                catch (InterruptedException e) {
                    e.printStackTrace();}
                finally {
                    this.releaseLock();}}}}
    /*
     * Move the Monster to the random neighbor square
     * @param Monsters
     * @param view
     * @param index
     */
    public void moveMonster(Monsters Monsters, MonsterView view, int index) {
        Random rand = new Random();
        Monster Monster  = Monsters.getMonster(index);

        int cellSize = view.getCellSize();
        int width = view.getWidth();
        int height = view.getHeight();

        float newX = Monster.getX() + cellSize * (rand.nextInt(3) - 1);
        float newY = Monster.getY() + cellSize * (rand.nextInt(3) - 1);

        if (newX < 0)
            newX = Monster.getX() + cellSize * (rand.nextInt(2) + 1);
        else if (newX + cellSize >= width)
            newX = Monster.getX() - cellSize * (rand.nextInt(2) + 1);

        if (newY < 0)
            newY = Monster.getY() + cellSize * (rand.nextInt(2) + 1);
        else if (newY + cellSize >= height)
            newY = Monster.getY() - cellSize * (rand.nextInt(2) + 1);
        boolean newState = !Monster.getProtectedState();
        try {
            this.getLock();
            Monsters.migrateMonsters(Monster, newX, newY, cellSize, newState);}
        catch (InterruptedException e) { e.printStackTrace(); }
        finally {
            this.releaseLock();
        }
    }
}