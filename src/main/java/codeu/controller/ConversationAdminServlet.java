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

/** Servlet class responsible for the Admin actions for a single conversation. */
public class ConversationAdminServlet extends BaseAdminServlet {
  private static final String SELF_JSP = "/WEB-INF/view/admin-conversation.jsp";

  @Override
  protected void onValidatedGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String conversation = request.getParameter("conversation"); 
    Conversation conversationToBeShown = conversationStore.getConversationWithTitle(conversation);
    if (conversationToBeShown != null) {
      User author = userStore.getUser(conversationToBeShown.getOwnerId());
      request.setAttribute("author", author);
      request.setAttribute("conversationToBeShown", conversationToBeShown);
    }
    request.getRequestDispatcher(SELF_JSP).forward(request, response);
  }

  @Override
  protected void onValidatedPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    // Do nothing for now.
  }
}
