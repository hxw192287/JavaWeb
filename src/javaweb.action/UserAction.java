/**
 * 
 */
package com.sxt.yunlai.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.sxt.yunlai.action.utils.BaseServlet;
import com.sxt.yunlai.entity.Post;
import com.sxt.yunlai.entity.User;
import com.sxt.yunlai.service.UserService;
import com.sxt.yunlai.service.impl.UserServiceImpl;
import com.sxt.yunlai.utils.PageBean;

/**
 * @author 系航宇
 * @date 2015-9-16下午12:16:00
 * @version V.1.0
 */
public class UserAction extends BaseServlet {

	private UserService userService;

	// 公共的无参构造方法
	public UserAction() {
		this.userService = new UserServiceImpl();
	}
    
	/**
	 * xihy
	 * 封装
	 * @param req
	 * @return
	 * @throws ParseException
	 */
	private User wrap(HttpServletRequest req) throws ParseException {
		// 1.得到输入的数据
		String userName = req.getParameter("userName") != null ? req
				.getParameter("userName") : null;
		System.out.println(userName);	
		
		String realName = req.getParameter("name") != null ? req
				.getParameter("name") : null;
		String sex = req.getParameter("sex") != null ? req.getParameter("sex")
				: null;
		Integer deptId = req.getParameter("dept") != null ? Integer
				.parseInt(req.getParameter("dept")) : null;
		Integer roleId = req.getParameter("role") != null ? Integer
				.parseInt(req.getParameter("role")) : null;
		String school = req.getParameter("school") != null ? req
				.getParameter("school") : null;
		String dateBirthday = req.getParameter("birthdaty") != null ? req
				.getParameter("birthdaty") : null;
		Date birthday;
		if (null != dateBirthday) {
			birthday = DateFormat.getDateInstance().parse(dateBirthday);
		} else {
			birthday = null;
		}
		String dateInTime = req.getParameter("intime") != null ? req
				.getParameter("intime") : null;
		Date inTime;
		if (null != dateBirthday) {
			inTime = DateFormat.getDateInstance().parse(dateInTime);
		} else {
			inTime = null;
		}
		String phoneNumber = req.getParameter("phonenumber") != null ? req
				.getParameter("phonenumber") : null;
		String email = req.getParameter("email") != null ? req
				.getParameter("email") : null;
		Integer userId = req.getParameter("userId") != null ? Integer
				.parseInt(req.getParameter("userId")) : null;
		return null;
	
	}
	
	/**
	 * Luoj
	 * 得到所有用户
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
 
	public String getAllUser(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int pageCode = req.getParameter("pageCode") == null ? 1 : new Integer(
				req.getParameter("pageCode"));
		// 1.得到所有用户 数据 依赖UserService
		PageBean pageBean = new PageBean();
		pageBean.setPageCode(pageCode);
		pageBean.setPageSize(7);
		List<User> allUser = this.userService.getAllUser(pageBean);
		System.out.println(pageBean.getPageCode() + "    "
				+ pageBean.getNextPage());
		pageBean.setBeanList(allUser);
		// 2.封装到作用域
		HttpSession session = req.getSession();
		session.setAttribute("pageBean", pageBean);
		// 3.跳转页面
		return "f:user_jsp/userList.jsp";

	}

	/**
	 * 封装User
	 * 
	 * @author dengzm
	 */
	public User wrap(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<String, Object> map = req.getParameterMap();
		User user = new User();
		try {
			BeanUtils.populate(user, map);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	
	/***
	 * xihy
	 * 添加用户
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws ParseException
	 */
	public String addUser(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, ParseException {
		User user = wrap(req);
		// 2.依赖UserService
		this.userService.save(user);
		return this.getAllUser(req, resp);
	}

	
	/***
	 * xihy
	 * 修改用户
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws ParseException
	 */
	public String getUserById(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, ParseException {
		Integer userId = req.getParameter("userId") != null ? Integer
				.parseInt(req.getParameter("userId")) : 0;
		// 1.得到用户 数据 根据Id 依赖UserService
		User user = this.userService.getUserById(userId);
		// 2.封装到作用域
		HttpSession session = req.getSession();
		session.setAttribute("user", user);
		// 3.跳转页面
		return "f:user_jsp/userUpdate.jsp";

	}

	
	/***
	 * xihy
	 *  删除用户
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String delUserById(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 通过ID得到对应ID的数据
		Integer userId = req.getParameter("userId") != null ? Integer
				.parseInt(req.getParameter("userId")) : 0;

		this.userService.delUserById(userId);
		// 刷新界面（调用getAllUser）
		return this.getAllUser(req, resp);

	}
     /***
      * xihy
      * 精确查找
      * @param req
      * @param resp
      * @return
      * @throws ServletException
      * @throws IOException
      * @throws ParseException
      */
	public String toCheck(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, ParseException {
		User user = wrap(req);
		List<User> user1 = this.userService.toCheck(user);
		// 2.封装到作用域
		HttpSession session = req.getSession();
		session.setAttribute("pageBean", user1);
		// 3.跳转页面
		return "f:UserAction?method=getAllUser";

	}
	
	private User wrap22(HttpServletRequest req) {
		System.out.println(req.getAttribute("username")+"测试");	
		
		return new User();
	}
	
	/**
	 * luoj
	 * @param req
	 * 检测用户，ajax
	 */
	 
	public String checkUser(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//ajax获得验证用户名
				String userName = req.getParameter("userName") != null ? req
						.getParameter("userName") : null;	
				User user=new User(null, userName, null, null);
				user = this.userService.findUserByInfo(user);	
				if(user!=null){
					return"";
				}else{
					int result=1;
					//流封装
					PrintWriter out=resp.getWriter();
					out.print(result);
					out.flush();
					out.close();
					return "";
				}
	}
	/**
	 * 处理登录(新)
	 * 
	 * @author Luoj
	 */
	public String doLogin(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String userName = req.getParameter("userName") != null ? req
				.getParameter("userName") : null;	
		User user=new User(null, userName, null, null);
		user = this.userService.findUserByInfo(user);	
		//action post请求获得密码
		String userPwd=req.getParameter("password")!=null?req.getParameter("password"):null;
		System.out.println(userPwd);
		if (user!= null) {
			if(userPwd==null){
				PrintWriter out=resp.getWriter();
				out.print("请输入密码");
				out.flush();
				out.close();
				return "";
			}else{
				if(userPwd.equals(user.getUserPwd())){
					System.out.println(user.getUserPwd());
						this.session.setAttribute("loginUser", user);
						return "f:index.jsp";
				}else{
					PrintWriter out=resp.getWriter();
					out.print("<script language='JavaScript'>alert('密码或用户名错误！请重新输入');location.href='login.jsp';</script>");
					out.flush();
					out.close();
					return "";
				}
			}
		}else{
			//用户不存在
			int result=1;
			//流封装
			PrintWriter out=resp.getWriter();
			out.print(result);
			out.flush();
			out.close();
			return "f:login.jsp";
		}
	}
	
		
	/**
	 * 处理注销
	 * @author luoj
	 */
	public String loginOut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.session.invalidate();
		return "r:login.jsp";
	}
	
	
}
