package x.tui.launcher.commands.main.raw;

import android.content.Intent;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.provider.Settings;

import java.lang.reflect.Field;

import x.tui.launcher.R;
import x.tui.launcher.commands.CommandAbstraction;
import x.tui.launcher.commands.ExecutePack;
import x.tui.launcher.commands.main.MainPack;
import x.tui.launcher.commands.main.specific.APICommand;
import x.tui.launcher.tuils.Tuils;
import x.tui.launcher.tuils.libsuperuser.Shell;

public class data implements APICommand, CommandAbstraction {

    @Override
    public String exec(ExecutePack pack) {
        MainPack info = (MainPack) pack;
        boolean current = isMobileConnected(info);
        boolean target = !current;

        if (toggleLegacy(info, target)) {
            return info.res.getString(R.string.output_data) + Tuils.SPACE + target;
        }

        if (Shell.SU.available() && toggleRoot(target)) {
            return info.res.getString(R.string.output_data) + Tuils.SPACE + target + " (root)";
        }

        Intent settingsIntent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        info.context.startActivity(settingsIntent);
        return info.res.getString(R.string.output_opening_settings);
    }

    private boolean toggleLegacy(MainPack info, boolean target) {
        if (info.connectivityMgr == null) {
            try {
                init(info);
            } catch (Exception e) {
                return false;
            }
        }

        try {
            info.setMobileDataEnabledMethod.invoke(info.connectMgr, target);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isMobileConnected(MainPack info) {
        if (info.connectivityMgr == null) {
            info.connectivityMgr = (ConnectivityManager) info.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }

        if (info.connectivityMgr == null) {
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            android.net.Network network = info.connectivityMgr.getActiveNetwork();
            if (network == null) return false;

            NetworkCapabilities capabilities = info.connectivityMgr.getNetworkCapabilities(network);
            return capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
        }

        NetworkInfo mobileInfo = info.connectivityMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileInfo == null) return false;

        State state = mobileInfo.getState();
        return state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING;
    }

    private boolean toggleRoot(boolean enable) {
        String[] commands = new String[] {
                "svc data " + (enable ? "enable" : "disable"),
                "cmd phone data " + (enable ? "enable" : "disable")
        };

        try {
            return Shell.SU.run(commands) != null;
        } catch (Exception e) {
            return false;
        }
    }

    private void init(MainPack info) throws Exception {
        info.connectivityMgr = (ConnectivityManager) info.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Class<?> conmanClass = Class.forName(info.connectivityMgr.getClass().getName());
        Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
        iConnectivityManagerField.setAccessible(true);
        info.connectMgr = iConnectivityManagerField.get(info.connectivityMgr);
        Class<?> iConnectivityManagerClass = Class.forName(info.connectMgr.getClass().getName());
        info.setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
        info.setMobileDataEnabledMethod.setAccessible(true);
    }

    @Override
    public int helpRes() {
        return R.string.help_data;
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
        return onNotArgEnough(info, 0);
    }

    @Override
    public boolean willWorkOn(int api) {
        return true;
    }
}
