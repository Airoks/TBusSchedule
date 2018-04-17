package ru.tblsk.owlz.busschedule.ui.base;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import ru.tblsk.owlz.busschedule.R;



public abstract class BaseActivity extends AppCompatActivity
        implements MvpView {

    public void showSnackBar(String message) {
        //android.R.id.content - получаем корневое представление Activity
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.white));
        snackbar.show();
    }

    @Override
    public void onError(String message) {
        if(message != null) {
            showSnackBar(message);
        } else {
            showSnackBar(getString(R.string.error));
        }
    }

    abstract protected void setUp();
}
