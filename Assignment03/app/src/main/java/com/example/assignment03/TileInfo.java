package com.example.assignment03;

import android.media.Image;
import android.widget.ImageView;

public class TileInfo
{
    private int imageViewId;
    private int foodImageId;
    private boolean selected = false;

    public TileInfo(int foodImageId, int imageViewId)
    {
        this.foodImageId = foodImageId;
        this.imageViewId = imageViewId;
    }

    public int getImageViewId()
    {
        return imageViewId;
    }

    public void setImageViewId(int imageViewId)
    {
        this.imageViewId = imageViewId;
    }

    public int getFoodImageId() {
        return foodImageId;
    }

    public void setFoodImageId(int foodImageId)
    {
        this.foodImageId = foodImageId;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    @Override
    public String toString()
    {
        return "TileInfo{" + "imageViewId=" + imageViewId + ", foodImageId=" + foodImageId + ", selected=" + selected + '}';
    }
}
