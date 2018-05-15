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
<%@ page import="codeu.model.data.Mention" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.store.basic.MentionStore" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.time.format.FormatStyle" %>
<%@ page import="java.time.ZoneId" %>


<!DOCTYPE html>
<html>
<%@ include file = "../../header.jsp" %>
  <% DateTimeFormatter formatter =
		    DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
            .withLocale( Locale.US )
            .withZone( ZoneId.systemDefault() ); %>
  <body>
    <div id="container">
      <h1>Activity</h1>
      <p>Here's everything that's happened on the site so far!</p>
      <div style="height:600px;width:750px;border:5px solid white;overflow:auto;">    
	    <%
	    List<Mention> mentions = (List<Mention>) request.getAttribute("mentions");
	    List<Conversation> conversations =
	      (List<Conversation>) request.getAttribute("conversations");
	    if(conversations == null || conversations.isEmpty()){
	    %>
	      <p>Create a conversation to get started.</p>
	    <%
	    }
	    else{
	    %>
	      <ul class="mdl-list">
	    <%
	      for(Conversation conversation : conversations){
	    %>
	      <li>
	        <%= formatter.format(conversation.getCreationTime()) %>: 
	        <%= UserStore.getInstance().getUser(conversation.getOwnerId()).getName() %> created a new conversation:
	        <a href="/chat/<%= conversation.getTitle()%>">
	        <%= conversation.getTitle() %></a>
	      </li>
	    <%
	    }
	    %>
	    <%
	      for(Mention mention : mentions){
	    %>
        <li>
          <%= formatter.format(mention.getCreationTime()) %>:
          <%= UserStore.getInstance().getUser(mention.getUserWhoDidTheMentioning()).getName() %> mentioned
          <%= UserStore.getInstance().getUser(mention.getUserWhoWasMentioned()).getName() %>
        </li>
      <% } %>
	    <%
	      }
	    %>
      </div>
   </div>
  </body>
</html>
