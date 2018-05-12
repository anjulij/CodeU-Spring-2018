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

import codeu.model.data.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet class responsible for the admin actions for a single user. */
public class UserAdminServlet extends BaseAdminServlet {
  private static final String SELF_JSP = "/WEB-INF/view/admin-user.jsp";

  @Override
  protected void onValidatedGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    // Note that on the login page, the username is a form parameter, but gets turned into
    // a user object on the *session*.
    String username = request.getParameter("username");
    User userToBeShown = userStore.getUser(username);
    request.setAttribute("userToBeShown", userToBeShown);
    request.getRequestDispatcher(SELF_JSP).forward(request, response);
  }

  /** Performs editing of a user. */
  @Override
  protected void onValidatedPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String username = request.getParameter("username");
    User userToBeEdited = userStore.getUser(username);
    // TODO(ncjones): there should be better error checking here.
    if (userToBeEdited != null) {
      String action = request.getParameter("action");
      User editedUser;
      if ("Block".equals(action)) {
        editedUser = User.blockUser(userToBeEdited);
        userStore.addUser(editedUser);
      } else if ("Unblock".equals(action)) {
        editedUser = User.unblockUser(userToBeEdited);
        userStore.addUser(editedUser);
      } else if ("Reset".equals(action)) {
        String newPassword = request.getParameter("new_password");
        if (newPassword == null) {
          newPassword = "password";
        }
        editedUser = User.resetPassword(userToBeEdited, newPassword);
        userStore.addUser(editedUser);
      } else {
        // Unsupported command; don't edit anything.
        editedUser = userToBeEdited;
      }
      request.setAttribute("userToBeShown", editedUser);
    }
    request.getRequestDispatcher(SELF_JSP).forward(request, response);
  }
}
