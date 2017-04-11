package com.apollo.fetcher;

import java.io.File;

// Fetches a file. Could be HTTPFetcher, HDFSFileFetcher, etc...
public abstract class FileFetcher<T> {

    // downloads the file and stores from a remote location
    // and stores it in a temporary file
    public abstract File fetch(T locater) throws Exception;
}
