package tomcat;

import sun.LdapOperation;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author sumu
 * @date 1/7/2022 4:44 PM
 */
@WebServlet("/loginLdap")
public class LoginController extends HttpServlet {

    private String message;

    public void init() throws ServletException {
        // 执行必需的初始化
        System.out.println("LDAP 登录页面被调用");
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        // 设置响应内容类型
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        String title = "使用 LDAP 登录";
        // 处理中文
        String name =new String(request.getParameter("name").getBytes("utf-8"),"UTF-8");
        String password =new String(request.getParameter("password").getBytes("utf-8"),"UTF-8");
        LdapOperation ldapOperation = new LdapOperation("ldap://212.129.137.221:389/",
                " cn=admin,dc=tetacloud2,dc=cn","123456");
        ldapOperation.login();
        System.out.println(name + " " + password);
        boolean islogin = ldapOperation.userLogin(name, password);
        String status = "";
        if(islogin) {
            status = "登录成功";
        } else {
            status = "登录失败";
        }

        String docType = "<!DOCTYPE html> \n";
        out.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor=\"#f0f0f0\">\n" +
                "<h1 align=\"center\">" + title + "</h1>\n" +
                "<ul>\n" +
                "  <li><b>用户名：</b>："
                + name + "\n" +
                "  <li><b>登录状态：</b>："
                + status + "\n" +
                "</ul>\n" +
                "</body></html>");
    }

    public void destroy() {
        // 什么也不做
        System.out.println("LDAP 登录页面servlet被销毁");
    }

}
