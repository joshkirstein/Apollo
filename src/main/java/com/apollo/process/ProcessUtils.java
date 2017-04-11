package com.apollo.process;

import com.google.common.base.Preconditions;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Utility class for process stuff
public class ProcessUtils {
    // runs the process, gets output, and waits for exit
    public static final List<String> runProcess(ProcessBuilder pb) throws IOException, InterruptedException {
        return runProcess(pb, null);
    }

    public static final List<String> runProcess(ProcessBuilder pb, byte[] stdin) throws IOException, InterruptedException {
        Preconditions.checkNotNull(pb, "pb must not be null!");
        Process p = pb.start();
        if (stdin != null) {
            OutputStream os = p.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            dos.write(stdin, 0, stdin.length);
            dos.flush();
            os.close();
        }
        InputStream is = p.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        List<String> input = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            input.add(line);
        }
        int exit = p.waitFor();
        if (exit != 0) {
            throw new RuntimeException("Process return code non-zero(" + exit + ")!");
        }
        br.close();
        return input;
    }
}
