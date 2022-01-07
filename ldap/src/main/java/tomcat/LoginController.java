package tomcat;

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
        String name =new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8");
        String password =new String(request.getParameter("password").getBytes("ISO-8859-1"),"UTF-8");
        String docType = "<!DOCTYPE html> \n";
        out.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor=\"#f0f0f0\">\n" +
                "<h1 align=\"center\">" + title + "</h1>\n" +
                "<ul>\n" +
                "  <li><b>用户名：</b>："
                + name + "\n" +
                "  <li><b>密码：</b>："
                + password + "\n" +
                "</ul>\n" +
                "</body></html>");
    }

    public void destroy() {
        // 什么也不做
        System.out.println("LDAP 登录页面servlet被销毁");
    }

}
