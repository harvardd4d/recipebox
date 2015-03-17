package com.example.d4d.recipebox;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.test.AndroidTestCase;

import junit.framework.TestCase;

import java.util.Collections;

/**
 * Created by Richard on 3/17/2015.
 */
public class RecipeTest extends TestCase{

    public void testEquals() {
        Recipe blank = new Recipe();
        Recipe notblank = new Recipe();
        notblank.setDescription("I am special");
        assertTrue("Equals is broken - identity", blank.equals(blank));
        assertTrue("Equals is broken - two same", blank.equals(new Recipe()));
        assertTrue("Equals is broken - different", !blank.equals(notblank));

    }

    public void testParcelingNoArgs() {
        // create a test no-arg recipe
        Recipe test = new Recipe();

        // obtain a parcel object
        Parcel parcel = Parcel.obtain();

        // write to parcel
        test.writeToParcel(parcel, 0);

        // reset parcel for reading
        parcel.setDataPosition(0);

        Recipe recreatedtest = Recipe.CREATOR.createFromParcel(parcel);

        // recreate no-args recipe
        assertTrue("Recipe parceling failure", test.equals(recreatedtest));
    }

    public void testParcelingWithBitmap() {
        Bitmap testbitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888);
        Recipe test = new Recipe("toast", "toast", 1, 1, 1, Collections.singletonList("toast"), "toast", 1, testbitmap);

        // obtain a parcel object
        Parcel parcel = Parcel.obtain();

        // write to parcel
        test.writeToParcel(parcel, 0);

        // reset parcel for reading
        parcel.setDataPosition(0);

        Recipe recreatedtest = Recipe.CREATOR.createFromParcel(parcel);

        // recreate no-args recipe
        assertTrue("Recipe parceling failure", test.equals(recreatedtest));

    }

}
