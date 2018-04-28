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

    <h1>The Scintillating Conversation</h1>

    <%
      Conversation conversation = (Conversation) request.getAttribute("conversationToBeShown");
      User author = (User) request.getAttribute("author");
      if (conversation == null) {
    %>
      <p>That's weird. The conversation is goneversation.</p>
    <%
      } else {
    %>
      <form method="POST">
        <ul class="mdl-list">
          <li>Title: <%= conversation.getTitle() %></li>
          <li>ID: <%= conversation.getId() %></li>
	  <li>Author: <a href="/admin/user?username=<%= author.getName() %>"><%= author.getName() %></a></li>
	  <li><%
                if (conversation.isMuted()) {
              %>
	          <input type="submit" name="action" value="Unmute">
              <%
	        } else {
              %>
                  <input type="submit" name="action" value="Mute">
             <%
                }
             %>
	  </li>
        </ul>
      </form>
    <%
    }
    %>
  </div>
</body>
</html>
