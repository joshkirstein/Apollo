package com.apollo.fetcher;

import com.apollo.storage.DistributedLog;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class HDFSFileFetcher extends FileFetcher<String> {
    private static final String IMAGE_DIRECTORY = "/apollo/images/";
    private static final Logger LOGGER = LoggerFactory.getLogger(HDFSFileFetcher.class);
    private static FileSystem dfs;

    static {
        Configuration conf = new Configuration();
        conf.addResource(DistributedLog.class.getClassLoader().getResourceAsStream("core-site.xml"));
        conf.addResource(DistributedLog.class.getClassLoader().getResourceAsStream("hdfs-site.xml"));
        try {
            dfs = FileSystem.get(conf);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public File fetch(String filename) {
        String imagePath = IMAGE_DIRECTORY + filename;
        Path path = new Path(imagePath);
        try {
            FSDataInputStream inputStream = dfs.open(path);
            byte[] data = IOUtils.toByteArray(inputStream);
            File file = File.createTempFile("apollo", "temp");
            file.deleteOnExit();
            FileUtils.writeByteArrayToFile(file, data);
            return file;
        } catch (IOException ex) {
            LOGGER.info("Failed to read image " + filename + "!");
            return null;
        }
    }
}
