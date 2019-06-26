package com.xujd.controller;

import com.xujd.model.PageUtil;
import com.xujd.model.UserInfo;
import com.xujd.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wechat.Question;
import wechat.Util;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/reply")
    public String reply(String reply,String question_id,Model model){
        Util.reply(reply,question_id);

        //获取所有数据数量
        PageUtil pageP = new PageUtil();
        UserInfo userInfo = new UserInfo();
        int count = Util.getTotalQuestionCount();
        PageUtil page = new PageUtil();
        //pageSize默认为15  currentPage默认为1
        page.setPageSize(pageP.getPageSize());
        page.setCurrentPage(pageP.getCurrentPage());

        List<Question> questionList = Util.getQuestionList(page.getStartRow(),page.getEndRow()-page.getStartRow());
//        //mysql中用 --这里用limit a,b 去分页，数据量比较大时不推荐limit
//        //a表示起始行，b表示数量，如 limit 0,5 表示查5条数据，从数据库中的第一条查起
//        userInfo.setStartRow(page.getStartRow());
//        userInfo.setEndRow(page.getEndRow() - page.getStartRow());
//        //分页查询数据
//        List<UserInfo> userList = userInfoService.selectUserByParams(userInfo);
//        //设置所有用户数量  如果有查询条件则以查询结果数量为准，不然为所有数量
//        if ((userInfo.getName() != null && !"".equals(userInfo.getName()))
//                || (userInfo.getLoginName() != null && !"".equals(userInfo.getLoginName()))) {
//            page.setTotalRecord(userList.size());
//        } else {
//            page.setTotalRecord(count);
//        }
        model.addAttribute("page1", page);
        //这里也可以通过page.setObjectLists(userList)传数据到页面
        model.addAttribute("userList","null");
        model.addAttribute("userInfo1", userInfo);
        model.addAttribute("questionList", questionList);

        return "/userManager";
    }

    /**
     * 用户登入
     *
     * @param userInfo
     * @return
     */
    @RequestMapping("/login")
    public String login(UserInfo userInfo, HttpServletRequest request, Model model) {
        String msg = "";
        boolean verified = Util.verifyTeacher(userInfo.getName(), userInfo.getPassword());

        if (verified) {
            /*将用户信息放入session*/
            request.getSession().setAttribute("userInfo", userInfo);
            //获取sessionid
            String sessionId = request.getSession().getId();
            model.addAttribute("sessionId", sessionId);
            //进入主界面
            return "/main";

        } else {
            msg = "该用户不存在！";
        }
        model.addAttribute("msg", msg);
        System.out.println("login:  " + Util.verifyTeacher(userInfo.getName(), userInfo.getPassword()));
        return "/userManager";
    }

    /**
     * 用户注册
     *
     * @param userInfo
     * @return
     */
    @RequestMapping("/register")
    public String register(UserInfo userInfo, Model model) {
        try {
            UserInfo user = userInfoService.selectUserByLoginName(userInfo.getLoginName());
            if (user != null) {
                model.addAttribute("msg", "注册失败,该登入名已存在！");
            } else {
                userInfoService.insertUser(userInfo);
                model.addAttribute("msg", "注册成功，请登入！");
            }
        } catch (Exception e) {
            model.addAttribute("msg", "注册失败！");
            e.printStackTrace();
        }
        return "/login";
    }

    /**
     * 退出系统
     *
     * @return
     */
    @RequestMapping("/loginOut")
    public String loginOut(HttpServletRequest request) {
        //清空session
        request.getSession().invalidate();
        return "login";
    }

    /**
     * 新增或编辑用户信息
     *
     * @param userInfo
     * @param model
     * @return
     */
    @RequestMapping("/addUser")
    public String addUser(UserInfo userInfo, Model model, String myid) {
        try {
            UserInfo user = userInfoService.selectUserByLoginName(userInfo.getLoginName());
            if (user != null) {
                model.addAttribute("msg", "操作失败,该登入名已存在！");
            } else {
                if (myid != null && !"".equals(myid)) {//myid存在表示编辑操作
                    userInfo.setId(Integer.parseInt(myid));
                    userInfoService.updateUser(userInfo);
                } else {
                    userInfoService.insertUser(userInfo);
                }
                model.addAttribute("msg", "操作成功！");
            }
        } catch (Exception e) {
            model.addAttribute("msg", "操作失败！");
            e.printStackTrace();
        }
        return "redirect:/user/selectAll";
    }

    /**
     * 根据ID获取用户
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("selectUserById")
    @ResponseBody
    public UserInfo selectUserById(int id, Model model) {
        UserInfo userInfo = userInfoService.selectUserById(id);
        return userInfo;
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return
     */
    @RequestMapping("/del")
    @ResponseBody
    public String del(int id) {
        try {
            userInfoService.deleteUser(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 权限管理
     *
     * @return
     */
    @RequestMapping("roleManager")
    public String roleManager() {
        return "roleManager";
    }

    /**
     * 菜单管理
     *
     * @return
     */
    @RequestMapping("menuManager")
    public String menuManager() {
        return "menuManager";
    }

    /**
     * 获取所有用户
     *
     * @return
     */
    @RequestMapping("/selectAll")
    public String selectAll(Model model, UserInfo userInfo, PageUtil pageP) {
        //获取所有数据数量
        int count = Util.getTotalQuestionCount();
        PageUtil page = new PageUtil();
        //pageSize默认为15  currentPage默认为1
        page.setPageSize(pageP.getPageSize());
        page.setCurrentPage(pageP.getCurrentPage());

        List<Question> questionList = Util.getQuestionList(page.getStartRow(),page.getEndRow()-page.getStartRow());
//        //mysql中用 --这里用limit a,b 去分页，数据量比较大时不推荐limit
//        //a表示起始行，b表示数量，如 limit 0,5 表示查5条数据，从数据库中的第一条查起
//        userInfo.setStartRow(page.getStartRow());
//        userInfo.setEndRow(page.getEndRow() - page.getStartRow());
//        //分页查询数据
//        List<UserInfo> userList = userInfoService.selectUserByParams(userInfo);
//        //设置所有用户数量  如果有查询条件则以查询结果数量为准，不然为所有数量
//        if ((userInfo.getName() != null && !"".equals(userInfo.getName()))
//                || (userInfo.getLoginName() != null && !"".equals(userInfo.getLoginName()))) {
//            page.setTotalRecord(userList.size());
//        } else {
//            page.setTotalRecord(count);
//        }
        model.addAttribute("page1", page);
        //这里也可以通过page.setObjectLists(userList)传数据到页面
        model.addAttribute("userList","null");
        model.addAttribute("userInfo1", userInfo);
        model.addAttribute("questionList", questionList);

        return "/userManager";
    }
}
