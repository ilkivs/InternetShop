package servlet;

import dao.GoodDao;
import dao.GoodDaoHibernate;
import dao.UserDao;
import dao.UserDaoHibernate;
import model.Good;
import model.Role;
import model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddServlet", value = "/add")
public class AddServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AddServlet.class);
    private static final UserDao USER_DAO = new UserDaoHibernate();
    private static final GoodDao GOOD_DAO = new GoodDaoHibernate();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        switch (type) {
            case "user":
                addUser(req, resp);
                break;
            case "good":
                addGood(req, resp);
                break;
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        int roleId = Integer.parseInt(request.getParameter("roleId"));

        User user = new User(login, password, email, Role.values()[roleId]);
        USER_DAO.add(user);

        LOGGER.info("Added user: " + user.toString());
        response.sendRedirect("userlist.jsp");
    }

    private void addGood(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));

        Good good = new Good(name, description, price);
        GOOD_DAO.add(good);

        LOGGER.info("Added good: " + good.toString());
        response.sendRedirect("marketplace.jsp");
    }
}
