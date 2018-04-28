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

import static org.mockito.Mockito.*;

import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class LoginServletTest {

  private LoginServlet loginServlet;

  @Mock private HttpServletRequest mockRequest;
  @Mock private HttpServletResponse mockResponse;
  @Mock private HttpSession mockSession;
  @Mock private RequestDispatcher mockRequestDispatcher;
  @Mock private UserStore mockUserStore;
  @Mock private User mockUser;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    loginServlet = new LoginServlet();
    loginServlet.setUserStore(mockUserStore);
    when(mockRequest.getSession()).thenReturn(mockSession);
    when(mockRequest.getRequestDispatcher("/WEB-INF/view/login.jsp"))
        .thenReturn(mockRequestDispatcher);
  }

  @Test
  public void testDoGet() throws IOException, ServletException {
    loginServlet.doGet(mockRequest, mockResponse);

    verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoPost_MissingUsername() throws IOException, ServletException {
    loginServlet.doPost(mockRequest, mockResponse);

    verify(mockRequest).setAttribute("error", "Missing username or password.");
    verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoPost_MissingPassword() throws IOException, ServletException {
    when(mockRequest.getParameter("username")).thenReturn("bad !@#$% username");

    loginServlet.doPost(mockRequest, mockResponse);

    verify(mockRequest).setAttribute("error", "Missing username or password.");
    verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoPost_BadUsername() throws IOException, ServletException {
    when(mockRequest.getParameter("username")).thenReturn("bad !@#$% username");
    when(mockRequest.getParameter("password")).thenReturn("some password");

    loginServlet.doPost(mockRequest, mockResponse);

    verify(mockRequest).setAttribute("error", "That username was not found.");
    verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoPost_ExistingUser() throws IOException, ServletException {
    when(mockRequest.getParameter("username")).thenReturn("test username");
    when(mockRequest.getParameter("password")).thenReturn("some password");
    when(mockUser.getPassword()).thenReturn("some password");
    when(mockUserStore.isUserRegistered("test username")).thenReturn(true);
    when(mockUserStore.getUser("test username")).thenReturn(mockUser);

    loginServlet.doPost(mockRequest, mockResponse);

    verify(mockUserStore, Mockito.never()).addUser(Mockito.any(User.class));
    verify(mockSession).setAttribute("user", "test username");
    verify(mockResponse).sendRedirect("/conversations");
  }

  @Test
  public void testDoPost_blockedUser() throws IOException, ServletException {
    when(mockRequest.getParameter("username")).thenReturn("test username");
    when(mockRequest.getParameter("password")).thenReturn("some password");
    when(mockUser.getPassword()).thenReturn("some password");
    when(mockUser.isBlocked()).thenReturn(true);
    when(mockUserStore.isUserRegistered("test username")).thenReturn(true);
    when(mockUserStore.getUser("test username")).thenReturn(mockUser);
    
    loginServlet.doPost(mockRequest, mockResponse);

    verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    verify(mockRequest).setAttribute("error", "You have been blocked. Bummer.");
    verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }
}
