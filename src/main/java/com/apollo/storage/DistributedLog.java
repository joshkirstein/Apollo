package com.apollo.storage;

import com.apollo.scheduler.SchedulerStateManager;
import com.apollo.thriftgen.Agent;
import com.apollo.thriftgen.SchedulerState;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class DistributedLog {
    private static final Path LOG_FILE_PATH = new Path("/apollo/transactions.log");
    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedLog.class);
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

    public static synchronized void writeSnapshot(SchedulerState state) {
        // TODO: currently saving as json for debugging purposes.
        TSerializer serializer = new TSerializer(new TJSONProtocol.Factory());
        byte[] bytes = new byte[0];
        try {
            bytes = serializer.serialize(state);
        } catch (TException ex) {
            LOGGER.error("Problem serializing SchedulerState object.");
            ex.printStackTrace();
        }
        try {
            FSDataOutputStream outputStream = dfs.create(LOG_FILE_PATH, true);
            outputStream.write(bytes);
            outputStream.close();
        } catch (IOException ex) {
            LOGGER.error("Error writing snapshot to log file!");
            ex.printStackTrace();
        }
    }

    public static synchronized SchedulerState readSnapshot() {
        try {
            FSDataInputStream inputStream = dfs.open(LOG_FILE_PATH);
            byte[] data = IOUtils.toByteArray(inputStream);

            TDeserializer deserializer = new TDeserializer(new TJSONProtocol.Factory());
            SchedulerState ret = new SchedulerState();
            deserializer.deserialize(ret, data);
            inputStream.close();
            return ret;
        } catch (IOException ex) {
            LOGGER.info("Tried to read snapshot when log file doesn't exist!");
            return null;
        } catch (TException ex) {
            LOGGER.error("Error trying to deserialize snapshot.");
            ex.printStackTrace();
            return null; // doesn't reach this point, just to shut java compiler up
        }
    }

    public static void main(String[] args) throws IOException {
        writeSnapshot(new SchedulerState()); // clear current snapshot
    }
}