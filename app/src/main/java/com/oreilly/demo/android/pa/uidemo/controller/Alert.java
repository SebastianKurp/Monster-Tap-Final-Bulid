package com.oreilly.demo.android.pa.uidemo.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

import com.oreilly.demo.android.pa.uidemo.model.Monsters;
import com.oreilly.demo.android.pa.uidemo.view.MonsterView;


public class Alert {
    View view;
    Monsters Monsters;

    public Alert(MonsterView view, Monsters Monsters)
    {
        this.view = view;
        this.Monsters=Monsters;
    }


    public void gameOverAlert() {     //Screen will display after the game is over
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Game Over!");
        builder.setNegativeButton("ok...", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) { dialog.cancel();}});

        AlertDialog alertDialog = builder.create();
        try { alertDialog.show(); }
        catch (NumberFormatException e) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
            builder1.setTitle("Error");
            builder1.setMessage("no inputs");
            builder1.setPositiveButton("Okay!", null);
            AlertDialog alertDialog1 = builder1.create();
            alertDialog1.show();}}

    public void congratsAlert(int level) { // an alert for when the level is cleared

        CharSequence msg = "Congrats! Level " + level + " cleared.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(view.getContext(), msg, duration);
        try { toast.show(); }
        catch (Exception e) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
            builder1.setTitle("Error");
            builder1.setMessage("no inputs");
            builder1.setPositiveButton("Okay!", null);
            AlertDialog alertDialog1 = builder1.create();
            alertDialog1.show();}}}