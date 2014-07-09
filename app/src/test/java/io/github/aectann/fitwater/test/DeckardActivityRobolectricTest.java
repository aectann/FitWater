package io.github.aectann.fitwater.test;

import android.app.Activity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.github.aectann.fitwater.MainActivity;

import static org.junit.Assert.assertTrue;

@Config(manifest = "./src/main/AndroidManifest.xml", emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DeckardActivityRobolectricTest {

    @Test
    public void testSomething() throws Exception {
        Activity activity = Robolectric.buildActivity(MainActivity.class).create().get();
        assertTrue(activity != null);
    }
}