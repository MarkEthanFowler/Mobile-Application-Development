package com.example.assignment03;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private final int[] imageViewIds = {R.id.foodImageOne, R.id.foodImageTwo, R.id.foodImageThree, R.id.foodImageFour, R.id.foodImageFive, R.id.foodImageSix,
                                        R.id.foodImageSeven, R.id.foodImageEight, R.id.foodImageNine, R.id.foodImageTen, R.id.foodImageEleven, R.id.foodImageTwelve,
                                        R.id.foodImageThirteen, R.id.foodImageFourteen, R.id.foodImageFifteen, R.id.foodImageSixteen, R.id.foodImageSeventeen,
                                        R.id.foodImageEighteen, R.id.foodImageNineteen, R.id.foodImageTwenty, R.id.foodImageTwentyOne, R.id.foodImageTwentyTwo,
                                        R.id.foodImageTwentyThree, R.id.foodImageTwentyFour, R.id.foodImageTwentyFive};
    private final int[] foodImageIds = {R.drawable.apple, R.drawable.lemon, R.drawable.mango, R.drawable.peach, R.drawable.strawberry, R.drawable.tomato};
    TextView objectiveTextView;

    Random rand = new Random();
    int focusIndex;
    int focus;
    int randomRange;
    String foodName;
    ArrayList<TileInfo> tiles = new ArrayList<>();
    ArrayList<TileInfo> removedTiles = new ArrayList<>();
    ArrayList<Object> shuffler = new ArrayList<>();
    ArrayList<Integer> unselectedFoodIds = new ArrayList<>();
    ArrayList<Integer> unselectedImageViewIds = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        objectiveTextView = findViewById(R.id.objectiveTextView);

        setupNewGame();

        findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setupNewGame();
            }
        });

    }

    private void setupNewGame()
    {
        ArrayList<Integer> shuffleFoodIds = new ArrayList<>();
        tiles.removeAll(tiles);
        unselectedFoodIds.removeAll(unselectedFoodIds);
        unselectedImageViewIds.removeAll(unselectedImageViewIds);

        focusIndex = rand.nextInt(6);
        focus = foodImageIds[focusIndex];
        randomRange = rand.nextInt(7);
        randomRange = randomRange + 1;
        for (int i = 0; i != randomRange; i++)
        {
            shuffleFoodIds.add(focus);
        }
        for(int i = 0; i != (imageViewIds.length - randomRange); i++)
        {
            int temp = rand.nextInt(6);
            while(temp == focusIndex)
            {
                temp = rand.nextInt(6);
            }
            shuffleFoodIds.add(foodImageIds[temp]);
        }

        Collections.shuffle(shuffleFoodIds);

        decideFocus(focus);

        objectiveTextView.setText(String.format("Find All " + foodName + " (" + randomRange + ")"));

        for (int i = 0; i < shuffleFoodIds.size(); i++)
        {
            int imageViewIndex = imageViewIds[i];
            int foodViewIndex = shuffleFoodIds.get(i);

            TileInfo tileInfo = new TileInfo(foodViewIndex, imageViewIndex);
            tiles.add(tileInfo);
            unselectedFoodIds.add(foodViewIndex);
            unselectedImageViewIds.add(imageViewIndex);

            ImageView tileImageView = findViewById(imageViewIndex);
            tileImageView.setTag(tileInfo);
            shuffler.add(tileImageView.getTag());
            tileImageView.setOnClickListener(this);

            tileImageView.setImageResource(tileInfo.getFoodImageId());

        }
    }

    public void decideFocus(int focus)
    {
        if(focus == R.drawable.apple)
        {
            foodName = "Apples";
        }
        else if(focus == R.drawable.lemon)
        {
            foodName = "Lemons";
        }
        else if(focus == R.drawable.mango)
        {
            foodName = "Mangoes";
        }
        else if(focus == R.drawable.peach)
        {
            foodName = "Peaches";
        }
        else if(focus == R.drawable.strawberry)
        {
            foodName = "Strawberries";
        }
        else
        {
            foodName = "Tomatoes";
        }
    }

    @Override
    public void onClick(View v)
    {
        ImageView tileView = (ImageView) v;
        TileInfo tileInfo = (TileInfo) tileView.getTag();

        //Log.d("demo", "onClick: " + getResources().getResourceName(tileInfo.getFoodImageId()));
        //Log.d("demo", "onClick: " + getResources().getResourceName(focus));

        //Used Brave AI for getResources().getResourceName
        if(!tileInfo.isSelected() && getResources().getResourceName(tileInfo.getFoodImageId()).equals(getResources().getResourceName(focus)))
        {
            tileInfo.setSelected(true);
            tileView.setAlpha(0.5f);
            randomRange--;
            objectiveTextView.setText(String.format("Find All " + foodName + " (" + randomRange + ")"));


            unselectedFoodIds.remove((Integer) tileInfo.getFoodImageId());
            unselectedImageViewIds.remove((Integer) tileInfo.getImageViewId());

            removedTiles.add(tileInfo);
            tiles.remove(tileInfo);
            shuffler.remove(tileInfo);

            Collections.shuffle(unselectedFoodIds);
            Log.d("demo", "onClick: " + unselectedImageViewIds.size());
            for (int i = 0; i < unselectedFoodIds.size(); i++)
            {

                TileInfo tileInfo1 = new TileInfo(unselectedFoodIds.get(i), unselectedImageViewIds.get(i));
                ImageView resetImage = findViewById(unselectedImageViewIds.get(i));
                resetImage.setTag(tileInfo1);
                resetImage.setImageResource(tileInfo1.getFoodImageId());

            }
            if(randomRange == 0)
            {
                removedTiles.removeAll(removedTiles);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Found All Food")
                        .setMessage("Congratulations! You have found all of the " + foodName)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                setupNewGame();

                                for (int i = 0; i < removedTiles.size(); i++)
                                {
                                    ImageView resetAlpha = findViewById(removedTiles.get(i).getImageViewId());
                                    resetAlpha.setAlpha(1f);
                                }
                                for (int i = 0; i < tiles.size(); i++)
                                {
                                    ImageView resetAlpha = findViewById(tiles.get(i).getImageViewId());
                                    resetAlpha.setAlpha(1f);
                                }
                            }
                        });

                builder.create().show();

            }
        }
    }
}