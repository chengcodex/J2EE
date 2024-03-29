package controller;

import service.AtmService;
import util.MySpring;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class InquiryController extends HttpServlet {

    //控制层--- 接受请求发送过来的信息  找寻业务层的方法干活  给予响应信息

    private AtmService service = MySpring.getBean("service.AtmService");

    //doGet  doPost
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(this.getServletName());
        System.out.println("你好啊");
        System.out.println(this.getServletConfig());
        System.out.println(this.getServletContext());
        //0.处理中文字符集
        //1.接受请求发送过来的账号--->
        String aname = request.getParameter("aname");
        System.out.println("接收到了请求发送的名字" + aname);
        //2.调用业务层的方法 ---> 负责查询
        Float abalance = service.inquiry(aname);//请求的时候没有发送钱
        System.out.println("通过业务方法查询到了余额" + abalance);
        //balance这个值是控制层找寻底层的业务得来的结果  请求的时候不知道 后面的JSP想要
        //像这样的值我们需要带走给后面的JSP使用
        request.setAttribute("abalance", abalance);//从业务层来的钱 自己带走
        //request对象中有两个map集合
        //  Map<String,String>      String value = request.getParameter("key");
        //  Map<String,Object>      Object value = request.getAttribute("key");
        //3.根据结果给予响应
        request.getRequestDispatcher("showBalance.jsp").forward(request, response);

    }
}
