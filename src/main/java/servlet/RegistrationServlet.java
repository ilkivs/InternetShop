package servlet;

import dao.UserDao;
import dao.impl.UserDaoHibernate;
import model.Role;
import model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegistrationServlet", value = "/registration")
public class RegistrationServlet extends HttpServlet {

    private static final UserDao userDao = new UserDaoHibernate();
    private static final Logger logger = Logger.getLogger(RegistrationServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        User newUser = new User(login, password, email, Role.USER);
        if (userDao.isUserExists(newUser)) {
            request.setAttribute("userExists", true);
            logger.debug("User exists: " + newUser.toString());
            response.sendRedirect("/registration");
        } else {
            userDao.add(newUser);
            request.getSession().setAttribute("user", newUser);
            logger.debug("Registered! User: " + newUser.toString());
            response.sendRedirect("/marketplace");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("registration.jsp").forward(req, resp);
    }
}
