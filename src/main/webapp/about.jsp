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
<!DOCTYPE html>
<html>
<%@ include file = "header.jsp" %>
<body>
  <div id="container">
    <h1>About Us:</h1>
    <h2>The Team: Unnamed Ones</h2>
    <ul>
      <li><strong>Anjuli:</strong> Xavier University of Louisiana</li>
      <li><strong>Esther:</strong> Duke University</li>
      <li><strong>Jorge:</strong> The University of Texas at Austin</li>
    </ul>
    <h2>Features:</h2>
    <ul>
      <li>Activity page: Displays user registrations, conversation creations, 
          messages sent in conversations and @Mentions in conversations. </li>
      <li>@Mentions: Once a user is mentioned, they will be able to see it in the 
          activity page. Also, the user who did the mentioning will the see it on the 
          activity page but other users will not be able to see the mention. (currently being fixed)</li>
    </ul>
    <h2>Improvements:</h2>
    <ul>
      <li>Changed website colors</li>
      <li>Password support</li>
      <li>Encrypted passwords</li>
      <li>Protection of data but changing some methods to return immutable lists</li>
      <li>Restriction of access if not logged in</li>
    </ul>
  </div>
</body>
</html>
