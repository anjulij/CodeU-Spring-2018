<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.data.Mention" %>
<%@ page import="codeu.model.store.basic.ConversationStore" %>
<%@ page import="codeu.model.store.basic.MessageStore" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%@ page import="codeu.model.store.basic.MentionStore" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.time.format.FormatStyle" %>
<%@ page import="java.time.ZoneId" %>
<%@ page import="java.time.Instant" %> 


<!DOCTYPE html>
<html>
<%@ include file = "../../header.jsp" %>
  <body>
    <div id="container">
      <h1>Activity</h1>
      <% if(request.getSession().getAttribute("user") != null){ %>
        <p>Here's everything that's happened on the site so far!</p>
        <div style="height:600px;width:750px;border:5px solid white;overflow:auto;">    
	      <%
	      List<Object> data = (List<Object>) request.getAttribute("data");
	      ConversationStore conversationStore = (ConversationStore) 
	    		request.getAttribute("conversationStore");
	      MessageStore messageStore = (MessageStore) 
	     		request.getAttribute("messageStore");
	      UserStore userStore = (UserStore) 
	    		request.getAttribute("userStore");
	      MentionStore mentionStore = (MentionStore) 
	    		request.getAttribute("mentionStore");
	      DateTimeFormatter formatter =
			    DateTimeFormatter.ofLocalizedDateTime (FormatStyle.SHORT )
	            .withLocale( Locale.US )
	            .withZone( ZoneId.systemDefault() );
	      %>
            <ul class="mdl-list">
          <% 
          for(Object obj : data){
            /* Object is a Conversation, so display the creation time, user who created it
                and a link to the chat with the title*/
            if (obj instanceof Conversation){
              Conversation conversation = (Conversation) obj;
            %>
	          <li>
	            <%= formatter.format(conversation.getCreationTime()) %>: 
	            <%= userStore.getUser(conversation.getOwnerId()).getName() %> 
	            created a new conversation:
	            <a href="/chat/<%= conversation.getTitle() %>">
	            <%= conversation.getTitle() %></a>
	          </li>
	        <%  
    	    }
            /* Object is a Message, so display the creation time, user who sent it, a link
               to the chat with a title, and the message content*/
    	    else if (obj instanceof Message){
              Message message = (Message) obj; 
              if (!message.containsMention()){
    	        Conversation conversation = conversationStore.getConversationWithId
    	    	    (message.getConversationId());
              %>
  	            <li>
  	              <%= formatter.format(message.getCreationTime()) %>:
                  <%= userStore.getUser(message.getAuthorId()).getName() %> 
                  sent a message in 
                  <a href="/chat/<%= conversation.getTitle() %>">
                  <%= conversation.getTitle() %></a>:
                  <%= message.getContent() %>     
  	            </li>
  	          <%
              }  
    	    }
            /* Object is a User, so display the creation time and the name of the User */
    	    else if (obj instanceof User){
    	      User user = (User) obj;
            %>
  		    <li>
  		      <%= formatter.format(user.getCreationTime()) %>:
  	          <%= user.getName() %> registered! 
  	  	    </li>
  		   <%
    	   }
           /* Object is a Mention, so display the creation time and both users involved
             in Mention */
    	   else{
    	     Mention mention = (Mention) obj;
    	     Message message = messageStore.getMessageWithId(mention.getMessageId());
    	     Conversation conversation = conversationStore.getConversationWithId
  				 (message.getConversationId());
  	       %>
  	         <li>
              <%= formatter.format(mention.getCreationTime()) %>:
              <%= userStore.getUser(mention.getUserWhoDidTheMentioning()).getName() %> 
              mentioned
              <%= userStore.getUser(mention.getUserWhoWasMentioned()).getName() %> in 
              <a href="/chat/<%= conversation.getTitle() %>">
              <%= conversation.getTitle() %></a>:
              <%= message.getContent() %>   
            </li>
           <%
  	      }
        }
      } 
      else {
    	%>  
        <p><a href="/login">Login</a> to see activity page.</p>
        <%
      }
      %>
     </div>
   </div>
  </body>
</html>
