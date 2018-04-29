package ru.tblsk.owlz.busschedule.ui.base;

import java.util.List;

public interface CopyItems<T> {
    List<T> getCopy(List<T> items);
}
