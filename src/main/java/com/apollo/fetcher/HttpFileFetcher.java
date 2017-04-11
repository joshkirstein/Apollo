package com.apollo.fetcher;

import com.google.common.base.Preconditions;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

// TODO: Some sort of caching?
public class HttpFileFetcher extends FileFetcher<URL> {
    private static HttpFileFetcher SingletonInstance;

    public static synchronized HttpFileFetcher getSingleton() {
        if (SingletonInstance == null) {
            SingletonInstance = new HttpFileFetcher();
        }
        return SingletonInstance;
    }

    @Override
    public File fetch(URL locater) throws Exception {
        Preconditions.checkNotNull(locater, "locater must not be null!");
        try {
            File file = File.createTempFile("apollo", "temp");
            file.deleteOnExit();
            FileUtils.copyURLToFile(locater, file);
            return file;
        } catch (IOException ex) {
            throw new Exception("Error creating temp file!", ex);
        }
    }
}
