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
<%@ page import="codeu.model.data.User" %>

<!DOCTYPE html>
<html>
<%@ include file = "../../header.jsp" %>
<body>
  <div id="container">

    <% if(request.getAttribute("error") != null){ %>
        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>

    <h1>Conversations</h1>

    <%
    List<Conversation> conversations = (List<Conversation>) request.getAttribute("conversations");
    if (conversations == null || conversations.isEmpty()) {
    %>
      <p>There are <i>no</i> conversations!</p>
    <% } else { %>
      <ul class="mdl-list">
    <%
      for(Conversation conversation : conversations) {
    %>
      <li>
        <a href="/admin/conversation?conversation_id=<%= conversation.getId() %>">
          <%= conversation.getTitle() %>
	</a>
      </li>
    <%
      }
    %>
      </ul>

    <%
    }
    %>

    <h1>Users</h1>

    <%
    List<User> users = (List<User>) request.getAttribute("users");
    if (users == null || users.isEmpty()) {
    %>
    <p>There are <i>no</i>users, which is weird, because how could you see this page?</p>
    <% } else { %>
      <ul class="mdl-list">
    <%
      for (User user : users) {
    %>
      <li>
        <a href="/admin/user?user_id=<%= user.getId() %>">
          <%= user.getName() %>
	</a>
      </li>
    <%
      }
    %>
      </ul>
    <%
    }
    %>
    
  </div>
</body>
</html>
