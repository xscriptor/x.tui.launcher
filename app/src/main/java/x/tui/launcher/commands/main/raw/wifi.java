package x.tui.launcher.commands.main.raw;

import android.content.Intent;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;

import x.tui.launcher.R;
import x.tui.launcher.commands.CommandAbstraction;
import x.tui.launcher.commands.ExecutePack;
import x.tui.launcher.commands.main.MainPack;
import x.tui.launcher.tuils.libsuperuser.Shell;

public class wifi implements CommandAbstraction {

    @Override
    public String exec(ExecutePack pack) {
        MainPack info = (MainPack) pack;
        if (info.wifi == null)
            info.wifi = (WifiManager) info.context.getSystemService(Context.WIFI_SERVICE);

        boolean current = false;
        try {
            current = info.wifi != null && info.wifi.isWifiEnabled();
        } catch (Exception ignored) {
        }

        boolean target = !current;

        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && info.wifi != null && info.wifi.setWifiEnabled(target)) {
                return info.res.getString(R.string.output_wifi) + " " + target;
            }
        } catch (Exception ignored) {
        }

        if (Shell.SU.available() && tryRootToggle(target)) {
            return info.res.getString(R.string.output_wifi) + " " + target + " (root)";
        }

        Intent settingsIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        info.context.startActivity(settingsIntent);
        return info.res.getString(R.string.output_opening_settings);
    }

    private boolean tryRootToggle(boolean enable) {
        String[] commands = new String[] {
                "cmd wifi set-wifi-enabled " + (enable ? "enabled" : "disabled"),
                "svc wifi " + (enable ? "enable" : "disable")
        };

        try {
            return Shell.SU.run(commands) != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int helpRes() {
        return R.string.help_wifi;
    }

    @Override
    public int[] argType() {
        return new int[0];
    }

    @Override
    public int priority() {
        return 2;
    }

    @Override
    public String onNotArgEnough(ExecutePack info, int nArgs) {
        return null;
    }

    @Override
    public String onArgNotFound(ExecutePack info, int index) {
        return null;
    }

}
