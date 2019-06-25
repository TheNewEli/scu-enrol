package com.xujd.controller;

import com.xujd.model.PageUtil;
import com.xujd.model.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseController {
    protected PageUtil page;

    public PageUtil getPage() {
        if (page == null) {
            page = new PageUtil();
        }
        return page;
    }

    public void setPage(PageUtil page) {
        this.page = page;
    }

    protected HttpServletRequest request;
    protected HttpServletResponse response;

    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * 获得HttpServletResponse对象
     *
     * @return HttpServletResponse
     */
    protected HttpServletResponse getResponse()
    {
        return response;
    }

    /**
     * 获得HttpSession对象  如果当前session不存在是否重新创建session
     * @param
     * @return HttpSession
     */
    protected HttpSession getSession()
    {
        return this.getRequest().getSession();
    }


    protected <T> T getSessionAttr(String attrName, Class<T> clazz) {
        return (T) this.getSession().getAttribute(attrName);
    }

    /**
     * 从session中获取当前登录用户对象
     *
     * @param
     * @param
     * @return
     */
    public UserInfo getCurrUser() {
        return (UserInfo) this.getSessionAttr("userInfo",
                UserInfo.class);

    }
}
