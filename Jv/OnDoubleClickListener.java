package ltd.nickolay.listclick.Jv;

import android.view.View;

/**Class from internet for div Single and Double click*/

public abstract class OnDoubleClickListener implements View.OnClickListener {
    private static final long TIME_DELTA = 300;
    private long lastTimeClick = 0;

    abstract void onSingleClick(View view);
    abstract void onDoubleClick(View view);

    @Override
    public void onClick(View v) {
        long currentTimeClick = System.currentTimeMillis();
        if (currentTimeClick - lastTimeClick < TIME_DELTA)
            onDoubleClick(v);
        else
            onSingleClick(v);
        lastTimeClick = currentTimeClick;
    }
}
