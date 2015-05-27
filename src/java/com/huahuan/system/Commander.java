package com.huahuan.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hyberbin
 */
public class Commander {

    private Runtime runtime = Runtime.getRuntime();
    private LinkedList<String> outLines = new LinkedList<String>();
    private LinkedList<String> inLines = new LinkedList<String>();
    private Process process = null;
    private OutputStream outputToChild;
    private InputThread inputThread = new InputThread();
    private OutputThread outputThread = new OutputThread();
    private ErrorThread errorThread = new ErrorThread();
    private InputStream is;
    private InputStreamReader outIsr;
    private InputStreamReader errorIsr;
    private InputStream errorStream;

    public Commander() {
        try {
            process = runtime.exec("cmd");
            outputToChild = process.getOutputStream();
            is = process.getInputStream();
            errorStream = process.getErrorStream();
        } catch (IOException e) {
        }
        inputThread.start();
        outputThread.start();
        errorThread.start();
    }

    public void addLines(String cmd) {
        inLines.add(cmd);
        notifyThread(inputThread);
        notifyThread(outputThread);
        notifyThread(errorThread);
    }

    public void waitThread(Thread thred) {
        synchronized (thred) {
            try {
                thred.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Commander.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void notifyThread(Thread thred) {
        synchronized (thred) {
            thred.notify();
        }
    }

    public static String taskList() {
        return runCmd("tasklist");
    }

    public static String runCmd(String cmd) {
        ExecutBean eb = new ExecutBean(cmd, "");
        new ExecutCmd().execut(eb, "");
        return eb.getOutput() + "\n" + eb.getError();
    }

    public void doClear() {
        outLines.clear();
    }

    public void doEnd() {
        try {
            addLines("exit");
            this.errorIsr.close();
            this.outIsr.close();
            outputToChild.close();
            inputThread.interrupt();
            errorThread.interrupt();
            outputThread.interrupt();
            errorStream.close();
            is.close();
        } catch (IOException ex) {
            Logger.getLogger(ExecutCmd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void readStream(InputStream is, InputStreamReader isr) {
        String line;
        try {
            isr = new InputStreamReader(is, "GB2312");
            BufferedReader ibr = new BufferedReader(isr);
            try {
                while ((line = ibr.readLine()) != null) {
                    outLines.add(line.trim() + "\n");
                }
            } catch (IOException e) {
                System.out.println("读取流出错！！！");
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ExecutCmd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getOutString() {
        StringBuilder sb = new StringBuilder();
        for (String str : outLines) {
            sb.append(str);
        }
        return sb.toString().trim();
    }

    private class InputThread extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    while (inLines.size() != 0) {
                        outputToChild.write((inLines.removeFirst() + "\n").getBytes());
                    }
                    outputToChild.flush();
                    waitThread(this);
                }
            } catch (Exception e) {
                System.out.println("写入失败");
            }
        }
    }

    private class OutputThread extends Thread {

        @Override
        public void run() {
            while (true) {
                readStream(is, outIsr);
                waitThread(this);
            }
        }
    }

    private class ErrorThread extends Thread {

        @Override
        public void run() {
            while (true) {
                readStream(errorStream, errorIsr);
                waitThread(this);
            }
        }
    }
}
