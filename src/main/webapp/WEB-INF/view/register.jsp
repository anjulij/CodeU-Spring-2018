<!DOCTYPE html>

<html>

<head>
  <title>The Unnamed Ones&#39; CodeU Chat App</title>
  <link rel="stylesheet" href="/css/main.css">
    <style>
        label{
            display: inline-block;
            width 100px;
        }
    </style>
</head>

<body>
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
     <!-- <a href="/testdata">Test Data</a> -->
   </nav>

    <div id="container">

        <h1>Register</h1>

        <% if (request.getAttribute("error") != null){ %>
            <h2 style="color:#D79922"><%=
                request.getAttribute("error") %></h2>

        <% } %>

        <form action="/register" method="POST">
            <label for="username">Username: </label>
            <input type="text" name="username" id="username">
            <br/>

            <label for="password">Password: </label>
            <input type="password" name="password" id="password">
            <br/><br/>

            <button type="submit">Submit</button>
        </form>
    </div>

</body>

</html>
