package ohi.andre.consolelauncher.commands.main.raw;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecutePack;
import ohi.andre.consolelauncher.commands.main.MainPack;
import ohi.andre.consolelauncher.commands.main.specific.APICommand;
import ohi.andre.consolelauncher.tuils.libsuperuser.Shell;

/**
 * Created by andre on 03/12/15.
 */
public class airplane implements APICommand, CommandAbstraction {

    @Override
    public String exec(ExecutePack pack) {
        MainPack info = (MainPack) pack;
        boolean enabled = isEnabled(info.context);
        boolean target = !enabled;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1 && toggleLegacy(info.context, target)) {
            return info.res.getString(R.string.output_airplane) + target;
        }

        if (Shell.SU.available() && toggleRoot(target)) {
            return info.res.getString(R.string.output_airplane) + target + " (root)";
        }

        Intent settingsIntent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        info.context.startActivity(settingsIntent);
        return info.res.getString(R.string.output_opening_settings);
    }

    private boolean toggleLegacy(Context context, boolean enable) {
        try {
            Settings.System.putInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, enable ? 1 : 0);

            Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            intent.putExtra("state", enable);
            context.sendBroadcast(intent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean toggleRoot(boolean enable) {
        String state = enable ? "1" : "0";

        String[] commands = new String[] {
                "settings put global airplane_mode_on " + state,
                "am broadcast -a android.intent.action.AIRPLANE_MODE --ez state " + (enable ? "true" : "false")
        };

        try {
            return Shell.SU.run(commands) != null;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isEnabled(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0;
    }

    @Override
    public int priority() {
        return 2;
    }

    @Override
    public int[] argType() {
        return new int[0];
    }

    @Override
    public int helpRes() {
        return R.string.help_airplane;
    }

    @Override
    public String onArgNotFound(ExecutePack info, int index) {
        return null;
    }

    @Override
    public String onNotArgEnough(ExecutePack info, int nArgs) {
        return null;
    }

    @Override
    public boolean willWorkOn(int api) {
        return true;
    }
}
