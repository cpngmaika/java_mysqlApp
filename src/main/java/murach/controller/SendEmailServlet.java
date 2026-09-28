package murach.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import murach.util.MailUtil;

import java.io.IOException;

@WebServlet("/sendEmail")
public class SendEmailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/email_form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mailUsername = System.getenv("MAIL_USERNAME");
        if (mailUsername == null || mailUsername.trim().isEmpty()) {
            mailUsername = getServletContext().getInitParameter("mailUsername");
        }

        String mailPassword = System.getenv("MAIL_PASSWORD");
        if (mailPassword == null || mailPassword.trim().isEmpty()) {
            mailPassword = getServletContext().getInitParameter("mailPassword");
        }

        String to = request.getParameter("to");
        String from = request.getParameter("from");
        if (from == null || from.trim().isEmpty()) {
            from = mailUsername;
        }
        String subject = request.getParameter("subject");
        String body = request.getParameter("body");

        try {
            MailUtil.sendMail(to, from, subject, body, false, mailUsername, mailPassword);
            request.setAttribute("message", "Email đã được gửi thành công đến " + to);
        } catch (MessagingException e) {
            e.printStackTrace();
            request.setAttribute("message", "Có lỗi xảy ra khi gửi email: " + e.getMessage());
        }

        request.getRequestDispatcher("/message.jsp").forward(request, response);
    }
}
