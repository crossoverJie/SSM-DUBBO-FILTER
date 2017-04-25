package com.crossoverJie.dubbo.filter;
/**
 * Function:拦截对象
 * @author chenjiec
 * Date: 2017/4/24 下午9:27
 * @since JDK 1.7
 */
public class FilterDesc {

    private String interfaceName ;//接口名
    private String methodName ;//方法名
    private Object[] args ;//参数

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
