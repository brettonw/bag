package com.brettonw.bag.expr;

import com.brettonw.bag.Bag;
import com.brettonw.bag.BagObject;

public class Not extends BooleanExpr {
    public static final String NOT = "not";

    private BooleanExpr left;

    public Not (BagObject expr) {
        left = (BooleanExpr) Exprs.get (expr.getObject (LEFT));
    }

    @Override
    Object evaluate (Bag bag) {
        return ! left.evaluateIsTrue (bag);
    }

    public static BagObject bag (BagObject left) {
        return bag (NOT, left);
    }
}
