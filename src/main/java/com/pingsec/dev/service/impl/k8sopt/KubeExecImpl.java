package com.pingsec.dev.service.impl.k8sopt;

import com.pingsec.dev.service.k8sopt.KubeExec;

import com.google.common.io.ByteStreams;
import io.kubernetes.client.Exec;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.util.Config;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.stereotype.Service;

@Service
public class KubeExecImpl implements KubeExec {
    public void done(String[] args){
        final Options options = new Options();
        options.addOption(new Option("p", "pod", true, "The name of the pod"));
        options.addOption(new Option("n", "namespace", true, "The namespace of the pod"));

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String podName = cmd.getOptionValue("p", "nginx-dbddb74b8-s4cx5");
        String namespace = cmd.getOptionValue("n", "default");
        List<String> commands = new ArrayList<>();

        args = cmd.getArgs();
        for (int i = 0; i < args.length; i++) {
            commands.add(args[i]);
        }

        ApiClient client = null;
        try {
            client = Config.defaultClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Configuration.setDefaultApiClient(client);

        Exec exec = new Exec();
        boolean tty = System.console() != null;
        // final Process proc = exec.exec("default", "nginx-4217019353-k5sn9", new String[]
        //   {"sh", "-c", "echo foo"}, true, tty);
        Process proc = null;
        try {
            proc = exec.exec(
                namespace,
                podName,
                commands.isEmpty()
                    ? new String[] {"sh"}
                    : commands.toArray(new String[commands.size()]),
                true,
                tty);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread in =
            new Thread(
                new Runnable() {
                    public void run() {
                        try {
                            ByteStreams.copy(System.in, proc.getOutputStream());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
        in.start();

        Thread out =
            new Thread(
                new Runnable() {
                    public void run() {
                        try {
                            ByteStreams.copy(proc.getInputStream(), System.out);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
        out.start();

        try {
            proc.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // wait for any last output; no need to wait for input thread
        try {
            out.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        proc.destroy();

        System.exit(proc.exitValue());
    }
}
