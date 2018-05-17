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
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MentionStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;

/** Servlet class responsible for the activity page. */
public class ActivityServlet extends HttpServlet {

	/** Store class that gives access to Conversations. */
	private ConversationStore conversationStore;

	/** Store class that gives access to Messages. */
	private MessageStore messageStore;
	
	/** Store class that gives access to Users. */
	private UserStore userStore;
	
	/** Store class that gives access to Mentions. */
	private MentionStore mentionStore;
	
	
	/**
	 * Set up state for handling conversation-related requests. This method is only called when
	 * running in a server, not when running in a test.
	 */
	@Override
	public void init() throws ServletException {
		super.init();
		setConversationStore(ConversationStore.getInstance());
		setMessageStore(MessageStore.getInstance());
		setUserStore(UserStore.getInstance());
		setMentionStore(MentionStore.getInstance());
	}

	/**
	 * Sets the ConversationStore used by this servlet. This function provides a common setup method
	 * for use by the test framework or the servlet's init() function.
	 */
	void setConversationStore(ConversationStore conversationStore) {
		this.conversationStore = conversationStore;
	}

	/**
	 * Sets the MessageStore used by this servlet. This function provides a common setup method
	 * for use by the test framework or the servlet's init() function.
	 */
	void setMessageStore(MessageStore messageStore) {
		this.messageStore = messageStore;
	}
	
	/**
	 * Sets the UserStore used by this servlet. This function provides a common setup method for use
	 * by the test framework or the servlet's init() function.
	 */
	void setUserStore(UserStore userStore) {
		this.userStore = userStore;
	}
	
	/**
	 * Sets the MentionsStore used by this servlet. This function provides a common setup method
	 * for use by the test framework or the servlet's init() function.
	 */
	void setMentionStore(MentionStore mentionStore) {
		this.mentionStore = mentionStore;
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		/* Get the list of all conversations */
		List<Conversation> conversations =
				new ArrayList<Conversation>(conversationStore.getAllConversations());
		
		/* Get the list of all messages */
		List<Message> messages = 
				new ArrayList<Message>(messageStore.getAllMessges());

	  /* Get the current user */
		String username = (String) request.getSession().getAttribute("user");

		List<Mention> allMentions = new ArrayList<Mention>();

		if (username != null) {
			User user = userStore.getUser(username);

			/* Get all mentions for the user and by the user*/
			allMentions.addAll(mentionStore.getMentionsForUserId(user.getId()));
			allMentions.addAll(mentionStore.getMentionsByUserId(user.getId()));
		}
		
		/* Get the list of all users */
		List<User> users = new ArrayList<User>(userStore.getAllUsers());
		
		/* Add all lists into one list of Objects and sort it by creation time */
		List<Object> data = new ArrayList<Object>(conversations);
		data.addAll(messages);
		data.addAll(allMentions);
		data.addAll(users);

	  Collections.sort(data, (a, b) -> { Instant aTime = Instant.now(); Instant bTime = Instant.now(); 
	    		if(a instanceof Conversation) { aTime = ((Conversation)a).getCreationTime(); }
	    		if(a instanceof Message) { aTime = ((Message)a).getCreationTime(); }
	    		if(a instanceof User) { aTime = ((User)a).getCreationTime(); }
	    		if(a instanceof Mention) { aTime = ((Mention)a).getCreationTime(); }
	    		if(b instanceof Conversation) { bTime = ((Conversation)b).getCreationTime(); }
	    		if(b instanceof Message) { bTime = ((Message)b).getCreationTime(); }
	    		if(b instanceof User) { bTime = ((User)b).getCreationTime(); }
	    		if(b instanceof Mention) { bTime = ((Mention)b).getCreationTime(); }
	    		return bTime.compareTo(aTime);});
		request.setAttribute("data", data);
		request.setAttribute("conversationStore", conversationStore);
		request.setAttribute("messageStore", messageStore);
		request.setAttribute("userStore", userStore);
		request.setAttribute("mentionStore", mentionStore);
		request.getRequestDispatcher("/WEB-INF/view/activity.jsp").forward(request, response);
	}
}
