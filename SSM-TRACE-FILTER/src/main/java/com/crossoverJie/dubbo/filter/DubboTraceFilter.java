package com.crossoverJie.dubbo.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Function:dubbo日志拦截插件，方便排查定位异常。
 * @author crossoverJie
 * Date: 2017/4/24 下午9:24
 * @since JDK 1.7
 */
@Activate(group = Constants.PROVIDER, order = -999)
public class DubboTraceFilter implements Filter{

    private static final Logger logger = LoggerFactory.getLogger(DubboTraceFilter.class);

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        try {
            FilterDesc filterReq = new FilterDesc() ;
            filterReq.setInterfaceName(invocation.getInvoker().getInterface().getName());
            filterReq.setMethodName(invocation.getMethodName()) ;
            filterReq.setArgs(invocation.getArguments());

            logger.debug("dubbo请求数据:"+JSON.toJSONString(filterReq));

            Result result = invoker.invoke(invocation);
            if (result.hasException() && invoker.getInterface() != GenericService.class){
                logger.error("dubbo执行异常",result.getException());
            }else {
                logger.info("dubbo执行成功");

                FilterDesc filterRsp = new FilterDesc() ;
                filterRsp.setMethodName(invocation.getMethodName());
                filterRsp.setInterfaceName(invocation.getInvoker().getInterface().getName());
                filterRsp.setArgs(new Object[]{result.getValue()});
                logger.debug("dubbo返回数据"+JSON.toJSONString(filterRsp));

            }
            return result ;

        }catch (RuntimeException e){
            logger.error("dubbo未知异常" + RpcContext.getContext().getRemoteHost()
                    + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName()
                    + ", exception: " + e.getClass().getName() + ": " + e.getMessage(), e);
            throw e ;
        }
    }
}
