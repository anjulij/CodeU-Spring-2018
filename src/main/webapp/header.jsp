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
<!-- NOTE: register.jsp and chat.jsp do not use the header -->
<!DOCTYPE html>
<html>
<head>
  <title>The Unnamed Ones&#39; CodeU Chat App</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>
  <nav>
    <a id="navTitle" href="/">The Unnamed Ones&#39; CodeU Chat App</a>
    
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else{ %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/activity">Activity</a> 
    <a href="/conversations">Conversations</a> 
    <a href="/about.jsp">About</a>
    <a href="/testdata">Load Test Data</a>
  </nav>
</body>
</html>