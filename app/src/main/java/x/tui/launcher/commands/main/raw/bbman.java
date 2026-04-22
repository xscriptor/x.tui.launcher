package x.tui.launcher.commands.main.raw;

import android.graphics.Color;
import x.tui.launcher.R;
import x.tui.launcher.commands.ExecutePack;
import x.tui.launcher.commands.main.specific.ParamCommand;
import x.tui.launcher.tuils.BusyBoxInstaller;
import x.tui.launcher.tuils.Tuils;
import x.tui.launcher.managers.TerminalManager;

public class bbman extends ParamCommand {

    private enum Param implements x.tui.launcher.commands.main.Param {
        install {
            @Override
            public String exec(ExecutePack pack) {
                Tuils.sendOutput(Color.YELLOW, pack.context, "Downloading and verifying BusyBox...");
                BusyBoxInstaller.install(pack.context, new BusyBoxInstaller.InstallationCallback() {
                    @Override
                    public void onSuccess() {
                        Tuils.sendOutput(Color.GREEN, pack.context, "BusyBox installed successfully!");
                    }

                    @Override
                    public void onError(String error) {
                        Tuils.sendOutput(Color.RED, pack.context, "Installation failed: " + error);
                    }
                });
                return null;
            }
        },
        remove {
            @Override
            public String exec(ExecutePack pack) {
                if (!BusyBoxInstaller.isInstalled(pack.context)) {
                    return "BusyBox is not installed.";
                }
                BusyBoxInstaller.uninstall(pack.context);
                return "BusyBox removed successfully.";
            }
        };

        static Param get(String p) {
            for (Param p1 : values()) if (p.endsWith(p1.label())) return p1;
            return null;
        }

        @Override
        public String label() { return "-" + name(); }
        @Override
        public int[] args() { return new int[0]; }
        @Override
        public String onNotArgEnough(ExecutePack pack, int n) { return null; }
        @Override
        public String onArgNotFound(ExecutePack pack, int index) { return null; }
    }

    @Override
    public String[] params() {
        Param[] ps = Param.values();
        String[] ss = new String[ps.length];
        for (int i = 0; i < ps.length; i++) ss[i] = ps[i].label();
        return ss;
    }

    @Override
    protected x.tui.launcher.commands.main.Param paramForString(x.tui.launcher.commands.main.MainPack pack, String param) {
        return Param.get(param);
    }

    @Override
    public int priority() { return 5; }

    @Override
    public int helpRes() { return R.string.help_bbman; }

    @Override
    protected String doThings(ExecutePack pack) { return null; }
}
