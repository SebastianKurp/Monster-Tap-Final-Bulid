package com.oreilly.demo.android.pa.uidemo.controller;

import com.oreilly.demo.android.pa.uidemo.model.Monsters;

/**
 * Created by Matthew Goldsmith on 5/4/17.
 */

public interface ClickContainerVisitor<Result>
{
    Result setOnMenuItemClickListener(Monsters m);
    Result onDismiss(PopupMenu popupMenu);
}
