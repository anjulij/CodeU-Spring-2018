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
import codeu.model.data.Mention;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MentionStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet class responsible for the activity page. */
public class ActivityServlet extends HttpServlet {

  /** Store class that gives access to Users. */
  private UserStore userStore;

  /** Store class that gives access to Conversations. */
  private ConversationStore conversationStore;
  
  /** Store class that gives access to Mentions. */
  private MentionStore mentionStore;
  

  /**
   * Set up state for handling conversation-related requests. This method is only called when
   * running in a server, not when running in a test.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
    setConversationStore(ConversationStore.getInstance());
    setMentionStore(MentionStore.getInstance());
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
  
  void setMentionStore(MentionStore mentionStore) {
	this.mentionStore = mentionStore;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

	/* Get the list of all conversations */
    List<Conversation> conversations =
        new ArrayList<Conversation>(conversationStore.getAllConversations());
    Collections.sort(conversations, (a, b) -> b.getCreationTime().compareTo(a.getCreationTime()));
    
    /* Get the current user */
    String username = (String) request.getSession().getAttribute("user");
    User user = userStore.getUser(username);
    
    /* Get all mentions for the user and by the user*/
    List<Mention> allMentions = new ArrayList<Mention>();
    if (mentionStore.getMentionsForUserId(user.getId()) != null) {
    	List<Mention> mentionsForUser = new ArrayList<Mention>(mentionStore.getMentionsForUserId(user.getId()));
    	allMentions.addAll(mentionsForUser);
    }
    if (mentionStore.getMentionsByUserId(user.getId()) != null) {
    	List<Mention> mentionsByUser = new ArrayList<Mention>(mentionStore.getMentionsByUserId(user.getId())); 
        allMentions.addAll(mentionsByUser);
    }
    Collections.sort(allMentions, (a, b) -> b.getCreationTime().compareTo(a.getCreationTime()));
    
    request.setAttribute("mentions", allMentions);
    request.setAttribute("conversations", conversations);
    request.getRequestDispatcher("/WEB-INF/view/activity.jsp").forward(request, response);
  }
}
