package com.jiangchengframework.mvc.render;

import com.jiangchengframework.mvc.RequestHandlerChain;

/**
 * 默认渲染 200
 */
public class DefaultRender implements Render {
    @Override
    public void render(RequestHandlerChain handlerChain) throws Exception {
        int status = handlerChain.getResponseStatus();
        handlerChain.getResponse().setStatus(status);
    }
}
