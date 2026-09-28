package murach.controller;

import java.io.IOException;

import murach.dao.UserDAO;
import murach.dao.UserDAOImpl;
import murach.exception.DAOException;
import murach.model.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/emailList")
public class EmailListServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "join"; // hành động mặc định
        }

        String url;
        try {
            if (action.equals("join")) {
                url = "/email_list.jsp";
            } else if (action.equals("add")) {
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String email = request.getParameter("email");

                String message;
                if (userDAO.emailExists(email)) {
                    message = "Địa chỉ email này đã tồn tại. "
                            + "Vui lòng nhập một email khác.";
                    url = "/email_list.jsp";
                } else {
                    User user = new User(email, firstName, lastName);
                    userDAO.insert(user);
                    message = "";
                    url = "/thanks.jsp";
                }
                request.setAttribute("user",
                        new User(email, firstName, lastName));
                request.setAttribute("message", message);
            } else {
                url = "/email_list.jsp";
            }
        } catch (DAOException e) {
            request.setAttribute("message",
                    "Đã có lỗi xảy ra, vui lòng thử lại sau.");
            url = "/email_list.jsp";
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
