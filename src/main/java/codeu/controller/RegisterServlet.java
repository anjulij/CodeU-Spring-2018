package codeu.controller;

import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

/** Servlet class responsible for user registration */
public class RegisterServlet extends HttpServlet {

  /** Store class that gives access to Users. */
  private final UserStore userStore;

  public RegisterServlet() {
    this(UserStore.getInstance());
  }

  public RegisterServlet(UserStore userStore) {
    this.userStore = userStore;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
    // response.getWriter().println("<h1>RegisterServlet GET request.</h1>");
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    /**Checks if username is valid*/
    if (!username.matches("[\\w*\\s*]*")) {
      request.setAttribute("error", "Please enter only letters, numbers, and spaces.");
      request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
      return;
    }
    if(!username.matches(".*\\w.*")){
      //username only has white spaces
      request.setAttribute("error", "A username must contain at least one letter or number");
      request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
      return;
    }
    if (userStore.isUserRegistered(username)) {
      //username already exists
      request.setAttribute("error", "That username is already taken.");
      request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
      return;
    }

      /**Checks if password is valid*/
    if(!password.matches(".*\\w.*")){
      //password only has white space
      request.setAttribute("error", "A password must contain at least one letter or number");
      request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
      return;
    }
    if(!password.matches(".{8,}")){
      //password must have at least 8 places
      request.setAttribute("error", "A password must have at least 8 places");
      request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
      return;
    }
    if(!password.matches(".*[0-9].*")){
      //password must have at least one number
      request.setAttribute("error", "A password must have at least one number");
      request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
      return;
    }
    if(!password.matches(".*[A-Z].*")){
      //password must contain at least one capital letter
      request.setAttribute("error", "A password must contain at least one capital letter");
      request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
      return;
    }
    if(!password.matches(".*[a-z].*")){
      //password must contain at least one lowercase letter
      request.setAttribute("error", "A password must contain at least one lowercase letter");
      request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
      return;
    }

    /**Encrypts Password*/
    String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

    /**Adds user to userStore*/
    User user = new User(UUID.randomUUID(), username, passwordHash, Instant.now());
    userStore.addUser(user);

    response.sendRedirect("/login");

    // response.getWriter().println("<p>Username: " + username + "</p>");
    // response.getWriter().println("<p>Password: " + password + "</p>");

  }

  /**
   * Set up state for handling registration-related requests. This method is only called when
   * running a server, not when running in a test
   */

  /*
  @Override
  public void init() throws ServletException{
      super.init();
      setUserStore(UserStore.getInstance());
  }
  */

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */

  /*
  void setUserStore(UserStore userStore){
      this.userStore = userStore;
  }
  */

}
