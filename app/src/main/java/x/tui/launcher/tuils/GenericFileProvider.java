package x.tui.launcher.tuils;

import androidx.core.content.FileProvider;

import x.tui.launcher.BuildConfig;

public class GenericFileProvider extends FileProvider {
    public static final String PROVIDER_NAME = BuildConfig.APPLICATION_ID + ".FILE_PROVIDER";
}
