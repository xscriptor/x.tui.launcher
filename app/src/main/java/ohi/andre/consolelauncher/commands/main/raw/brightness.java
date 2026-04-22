package ohi.andre.consolelauncher.commands.main.raw;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecutePack;
import ohi.andre.consolelauncher.tuils.libsuperuser.Shell;

import static android.provider.Settings.System.SCREEN_BRIGHTNESS;
import static android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE;
import static android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
import static android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL;

/**
 * Created by francescoandreuzzi on 27/03/2018.
 */

public class brightness implements CommandAbstraction {
    @Override
    public String exec(final ExecutePack pack) throws Exception {
        final int brightness = Math.max(0, Math.min(100, pack.getInt()));
        final int brightness255 = brightness * 255 / 100;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.System.canWrite(pack.context)) {
            if (Shell.SU.available() && writeWithRoot(brightness255)) {
                if (pack.context instanceof Activity) {
                    ((Activity) pack.context).runOnUiThread(() -> refreshBrightness(((Activity) pack.context).getWindow(), brightness255 / 255f));
                }
                return null;
            }

            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:" + pack.context.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            pack.context.startActivity(intent);
            return pack.context.getString(R.string.output_opening_settings);
        }

        ((Activity) pack.context).runOnUiThread(() -> {
            int autobrightnessState;
            try {
                autobrightnessState = Settings.System.getInt(pack.context.getContentResolver(), SCREEN_BRIGHTNESS_MODE);
            } catch (Exception e) {
                autobrightnessState = SCREEN_BRIGHTNESS_MODE_MANUAL;
            }

            if(autobrightnessState == SCREEN_BRIGHTNESS_MODE_AUTOMATIC) Settings.System.putInt(pack.context.getContentResolver(), SCREEN_BRIGHTNESS_MODE, SCREEN_BRIGHTNESS_MODE_MANUAL);

            ContentResolver cResolver = pack.context.getApplicationContext().getContentResolver();
            Settings.System.putInt(cResolver, SCREEN_BRIGHTNESS, brightness255);

            refreshBrightness(((Activity) pack.context).getWindow(), brightness255 / 255f);

//                if(autobrightnessState == SCREEN_BRIGHTNESS_MODE_AUTOMATIC) setAutoBrightness((Activity) pack.context, true);
        });

        return null;
    }

    private boolean writeWithRoot(int brightness255) {
        String[] commands = new String[] {
                "settings put system screen_brightness_mode 0",
                "settings put system screen_brightness " + brightness255
        };

        try {
            return Shell.SU.run(commands) != null;
        } catch (Exception e) {
            return false;
        }
    }

    private void refreshBrightness(Window window, float brightness) {
        WindowManager.LayoutParams lp = window.getAttributes();
        if (brightness < 0) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        } else {
            lp.screenBrightness = brightness;
        }
        window.setAttributes(lp);
    }

    @Override
    public int[] argType() {
        return new int[] {CommandAbstraction.INT};
    }

    @Override
    public int priority() {
        return 3;
    }

    @Override
    public int helpRes() {
        return R.string.help_brightness;
    }

    @Override
    public String onArgNotFound(ExecutePack pack, int indexNotFound) {
        return pack.context.getString(R.string.invalid_integer);
    }

    @Override
    public String onNotArgEnough(ExecutePack pack, int nArgs) {
        return pack.context.getString(helpRes());
    }
}
