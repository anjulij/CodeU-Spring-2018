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
<%@ page import="codeu.model.store.basic.UserStore" %>
<%
Conversation conversation = (Conversation) request.getAttribute("conversation");
List<Message> messages = (List<Message>) request.getAttribute("messages");
%>

<!DOCTYPE html>
<html>
<head>
<title>The Unnamed Ones&#39; CodeU Chat App</title>
  <link rel="stylesheet" href="/css/main.css">
  <style>
    #chat {
      background-color: #EFE2BA;
      height: 500px;
      overflow-y: scroll
    }
  </style>

  <script>
    // scroll the chat div to the bottom
    function scrollChat() {
      var chatDiv = document.getElementById('chat');
      chatDiv.scrollTop = chatDiv.scrollHeight;
    };
  </script>
</head>
<body onload="scrollChat()">
  <nav>
    <a id="navTitle" href="/">The Unnamed Ones&#39; Chat App</a>
    
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
      <a href="/activity">Activity</a> 
      <a href="/conversations">Conversations</a> 
      <a href="/about.jsp">About</a>
      <a href="/logout">Logout</a>
    <% } else{ %>
      <a href="/login">Login</a>
      <a href="/activity">Activity</a> 
      <a href="/conversations">Conversations</a> 
      <a href="/about.jsp">About</a>
    <% } %>
   <!--  <a href="/testdata">Test Data</a> -->
  </nav>
  <div id="container">

    <h1><%= conversation.getTitle() %>
      <a href="" style="float: right">&#8635;</a></h1>

    <hr/>

    <div id="chat">
      <ul>
        <%
          if (!conversation.isMuted()) {
            for (Message message : messages) {
              String author = UserStore.getInstance()
                .getUser(message.getAuthorId()).getName();
            %>
              <li><strong><%= author %>:</strong> <%= message.getContent() %></li>
            <%
            }
          } else {
            %>
	      <li>This conversation is muted.</li>
	    <%
	  }
	%>
      </ul>
    </div>

    <hr/>

    <% if (request.getSession().getAttribute("user") != null && !conversation.isMuted()) { %>
    <form action="/chat/<%= conversation.getTitle() %>" method="POST">
        <input type="text" name="message">
        <br/>
        <button type="submit">Send</button>
    </form>
    <% } %>
    <hr/>

  </div>

</body>
</html>
