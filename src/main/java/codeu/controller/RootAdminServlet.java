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
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet class responsible for the Admin page. */
public class RootAdminServlet extends BaseAdminServlet {
  private static final String SELF_JSP = "/WEB-INF/view/admin.jsp";

  @Override
  protected void onValidatedGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    List<Conversation> conversations = conversationStore.getAllConversations();
    List<User> users = userStore.getAllUsers();
    request.setAttribute("conversations", conversations);
    request.setAttribute("users", users);
    request.getRequestDispatcher(SELF_JSP).forward(request, response);
  }

  @Override
  protected void onValidatedPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    // Do nothing for now.
  }
}
