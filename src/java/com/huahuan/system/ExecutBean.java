/*
 * 这个类存放在执行CMD命令时所需的输入和输出
 * POJ运行CMD时只有编译和运行两种状态
 * 如果是编译模式则只管错误信息
 * 如果是运行模式则要管输入、输出、进程号和内存
 */
package com.huahuan.system;

/**
 * 命令模型 系统根据命令模型去执行不同命令
 * @author hyberbin
 */
public class ExecutBean {

    /** 运行模式 */
    public static final int RUNMODE = 1;//运行模式
    /** 编译模式 */
    public static final int COMPILEMODE = 2;//编译模式
    private int status = 2;//默认下模式为编译模式
    private String cmd;//cmd命令
    private String input;//输入信息
    private String output;//输出信息
    private String error;//错误信息
    private double pid;//进程号
    private int mem;//内存
    private boolean isSuccess;

    /**
     * 带一个参数的构造方法则是编译模式
     * @param cmd CMD命令
     */
    public ExecutBean(String cmd) {
        this.cmd = cmd;
        this.status = COMPILEMODE;
        input = null;
        output = null;
        error = "";
        isSuccess = true;
    }

    /**
     * 带两个参数的构造方法是在运行模式下
     * @param cmd CMD命令
     * @param input 测试数据
     */
    public ExecutBean(String cmd, String input) {
        this.cmd = cmd;
        this.input = input;
        this.status = RUNMODE;
        output = null;
        error = "";
        isSuccess = true;
    }

    /**
     * 是否通过执行
     * @return
     */
    public boolean isIsSuccess() {
        return isSuccess;
    }

    /**
     * 设置通过状态
     * @param isSuccess true成功false失败
     */
    public void setFailed() {
            this.isSuccess = false;
    }

    /**
     * 获得CMD命令
     * @return
     */
    public String getCmd() {
        return cmd;
    }

    /**
     * 获得当前状态
     * @return
     */
    public int getStatus() {
        return status;
    }

    /**
     * 获得错误信息
     * @return
     */
    public String getError() {
        return error;
    }

    /**
     * 设置错误信息
     * @param error
     */
    public void setError(String error) {
        this.error += error;
    }

    /**
     * 获得测试数据
     * @return
     */
    public String getInput() {
        return input;
    }

    /**
     * 获得内存
     * @return
     */
    public int getMem() {
        return mem;
    }

    /**
     * 设置内存
     * @param mem 内存大小
     */
    public void setMem(int mem) {
        this.mem = mem;
    }

    /**
     * 获得输出信息
     * @return
     */
    public String getOutput() {
        return output;
    }

    /**
     * 设置输出信息
     * @param output
     */
    public void setOutput(String output) {
        this.output = output;
    }

    /**
     * 获得进程图像号
     * @return
     */
    public double getPid() {
        return pid;
    }

    /**
     * 设置进程图像号
     * @param pid 进程图像号
     */
    public void setPid(double pid) {
        this.pid = pid;
    }
}
