package com.ekalips.wldev.arcitecturetry.recycler_view_stuff.click_adapters;

/**
 * Created by wldev on 8/1/17.
 */

public abstract class PositionClickAdapter extends ClickAdapter {

    private int position;

    public PositionClickAdapter(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
