package com.oreilly.demo.android.pa.uidemo.controller;



import com.oreilly.demo.android.pa.uidemo.model.Monsters;

/**
 * Created by Matthew Goldsmith on 5/4/17.
 */

public abstract class CollectVisitor implements ClickContainerVisitor<Void>, Monsters.MonstersChangeListener {


    abstract class DefaultWarehouseClickManagerListener implements ClickContainerVisitor {

        private ClickContainer clickContainer;

        public DefaultWarehouseClickManagerListener(final ClickContainer clickContainer) {
            assert clickContainer != null;
        }

        //needs more work here
    }
}
