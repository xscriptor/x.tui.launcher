package x.tui.launcher.commands.main.raw;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.provider.Settings;

import x.tui.launcher.R;
import x.tui.launcher.commands.CommandAbstraction;
import x.tui.launcher.commands.ExecutePack;
import x.tui.launcher.commands.main.MainPack;
import x.tui.launcher.tuils.libsuperuser.Shell;

public class bluetooth implements CommandAbstraction {

    @Override
    public String exec(ExecutePack pack) {
        MainPack info = (MainPack) pack;

        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

        if(adapter == null) return info.context.getString(R.string.output_bluetooth_unavailable);

        boolean enabled;
        try {
            enabled = adapter.isEnabled();
        } catch (SecurityException e) {
            enabled = false;
        }

        boolean target = !enabled;

        try {
            boolean changed;
            if (target) {
                changed = adapter.enable();
            } else {
                changed = adapter.disable();
            }

            if (changed) {
                return info.context.getString(R.string.output_bluetooth) + " " + target;
            }
        } catch (SecurityException ignored) {
        }

        if (Shell.SU.available() && tryRootToggle(target)) {
            return info.context.getString(R.string.output_bluetooth) + " " + target + " (root)";
        }

        Intent settingsIntent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        info.context.startActivity(settingsIntent);
        return info.context.getString(R.string.output_opening_settings);
    }

    private boolean tryRootToggle(boolean enable) {
        String[] commands = new String[] {
                "cmd bluetooth_manager " + (enable ? "enable" : "disable"),
                "svc bluetooth " + (enable ? "enable" : "disable")
        };

        try {
            return Shell.SU.run(commands) != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int helpRes() {
        return R.string.help_bluetooth;
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
