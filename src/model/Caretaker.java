package model;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Korzix on 2015-04-10.
 */
public class Caretaker {
    Stack<MementoImage> savedImageViews = new Stack<>();
    public void addMemento(MementoImage mementoImage){savedImageViews.push(mementoImage);}
    public MementoImage getMementoImage(){ return savedImageViews.pop();}
}
