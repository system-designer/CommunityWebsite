package com.huahuan.system;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 执行CMD命令
 *
 * @author hyberbin
 */
public class ExecutCmd {

    /** 当前运行的进程 */
    private Process process;

    /**
     * 只允许单个运行
     * @param eb 命令模型
     */
    public void execut(ExecutBean eb, String basePath) {
        Runtime runtime = Runtime.getRuntime();
        InputStream is;
        try {
            process = runtime.exec(eb.getCmd());
        } catch (IOException e) {
            eb.setFailed();
            eb.setError("运行时出错\n");
            try {
                process.destroy();
            } catch (Exception ex) {
            }
            //System.out.println("运行出错" + eb.getCmd());
            return;
        }
        if (eb.getStatus() == ExecutBean.COMPILEMODE) {//如果是编译模式
            is = process.getErrorStream();//获得错误流
            /** 去掉基本路径，防止在错误信息中暴露地址信息 */
            String er=ReplaceAll.replaceAll(readStream(is), "\\", "/");
            er=ReplaceAll.replaceAll(er, "//", "/");
            basePath=ReplaceAll.replaceAll( basePath, "\\", "/");
            basePath=ReplaceAll.replaceAll( basePath, "//", "/");
            er=ReplaceAll.replaceAll(er, basePath, "");
            if (er != null) {
                eb.setError(er);//读取错误信息
            }
        } else { //如果是运行模式
            //eb.setPid(GetPID.GetPID(process));//获得pid
            OutputStream outputToChild = process.getOutputStream();
            try {
                outputToChild.write((eb.getInput() + "\n").getBytes());
                outputToChild.write(26);
                outputToChild.write(4086);
                outputToChild.flush();
                outputToChild.close();
            } catch (IOException e) {
                eb.setFailed();
                eb.setError("输入测试数据有误！\n");
                process.destroy();
                //System.out.println("输入测试数据有误！！！");
                return;
            }
            //eb.setMem((int) QueryAndKillProgress.getMemory((int) eb.getPid()));
            //下面获得输出信息
            is = process.getInputStream();
            String out = readStream(is);
            eb.setOutput(out);
            try {//等待这个程序运行完毕！！！
                process.waitFor();
            } catch (InterruptedException ex) {
                eb.setError("运行时出错\n");
            }
        }
    }

    private String readStream(InputStream is) {
        InputStreamReader isr = null;
        StringBuilder runsesult =new StringBuilder("");
        String line;
        try {
            isr = new InputStreamReader(is, "GB2312");
            BufferedReader ibr = new BufferedReader(isr);
            try {
                while ((line = ibr.readLine()) != null) {
                    runsesult.append(line).append("\n");
                }
            } catch (IOException e) {
                System.out.println("读取流出错！！！");
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ExecutCmd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                isr.close();
            } catch (IOException ex) {
                Logger.getLogger(ExecutCmd.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return runsesult.toString();
    }

    public Process getProcess() {
        return process;
    }
}
