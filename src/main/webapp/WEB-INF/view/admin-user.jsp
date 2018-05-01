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
<%@ page import="codeu.model.data.User" %>

<!DOCTYPE html>
<html>
<%@ include file = "../../header.jsp" %>
<body>
  <div id="container">

    <% if(request.getAttribute("error") != null){ %>
        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>

    <h1>The Esteemed User</h1>

    <%
      User user = (User) request.getAttribute("userToBeShown");
      if (user == null) {
    %>
      <p>User?! What user? We don't have no stinkin' user!</p>
    <%
      } else {
    %>
      <form method="POST">
        <ul class="mdl-list">
          <li>User: <%= user.getName() %> |
            <% if (user.isBlocked()) { %>
              <input type="submit" name="action" value="Unblock">
            <% } else { %>
              <input type="submit" name="action" value="Block">
            <% } %>
          </li> 
          <li>ID: <%= user.getId() %></li>
	  <li>Reset password: <input type="text" name="new_password"><input type="submit" name="action" value="Reset"></li>
        </ul>
      </form>
    <%
    }
    %>
  </div>
</body>
</html>
