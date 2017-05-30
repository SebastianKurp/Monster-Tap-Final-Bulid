package com.oreilly.demo.android.pa.uidemo.controller;

/**
 * Created by Matthew Goldsmith on 5/4/17.
 */

public interface ClickContainer
{
    <Result> Result accept( ClickContainerVisitor<Result> v );
}
