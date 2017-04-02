package com.example.sonic.whatdoyoudo.commons;

import android.view.View;

/**
 * Created by ramadhanrp on 2/5/2017.
 */

public interface RecyclerViewClickListener {
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}
