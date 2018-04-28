// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Absract class responsible for common functionality in all Admin pages. */
public abstract class BaseAdminServlet extends HttpServlet {

  /** Store class that gives access to Users. */
  protected UserStore userStore;

  /** Store class that gives access to Conversations. */
  protected ConversationStore conversationStore;

  private static final String LOGIN_URL = "/login";
  private static final String CONVERSATION_URL = "/conversations";

  /** Special magic username that allows access. */
  private static final String ADMIN_USER = "admin";

  /**
   * Set up state for handling admin-related requests. This basically means
   * we need everything. Not called in unit tests, which is a bit troubling.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
    setConversationStore(ConversationStore.getInstance());
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /**
   * Sets the ConversationStore used by this servlet. This function provides a common setup method
   * for use by the test framework or the servlet's init() function.
   */
  void setConversationStore(ConversationStore conversationStore) {
    this.conversationStore = conversationStore;
  }

  protected boolean userIsValid(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String username = (String) request.getSession().getAttribute("user");
    if (username == null) {
      // user is not logged in, so force them into that first.
      response.sendRedirect(LOGIN_URL);
      return false;
    } else if (!ADMIN_USER.equals(username)) {
      response.sendRedirect(CONVERSATION_URL);
      return false;
    } else {
      return true;
    }
  }

  /**
   * Functionality that an implementation class provides to fulfill a GET command.
   */
  protected abstract void onValidatedGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException;

  /**
   * Functionality that an implementation class provides to fulfill a POST command.
   */
  protected abstract void onValidatedPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException;

  /**
   * This function fires when a user navigates to the admin page. It gets all of the
   * conversations from the model and forwards to admin.jsp for rendering the list.
   * Note that this needs to gate on user.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    if (userIsValid(request, response)) {
      onValidatedGet(request, response);
	    /*
      List<Conversation> conversations = conversationStore.getAllConversations();
      List<User> users = userStore.getAllUsers();
      request.setAttribute("conversations", conversations);
      request.setAttribute("users", users);
      request.getRequestDispatcher(SELF_JSP).forward(request, response);
      */
    }
  }

  /**
   * This function fires when a user submits the form on the conversations page. It gets the
   * logged-in username from the session and the new conversation title from the submitted form
   * data. It uses this to create a new Conversation object that it adds to the model.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    if (userIsValid(request, response)) {
      onValidatedPost(request, response);
    }
  }
}
