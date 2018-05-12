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
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserAdminServletTest {

  private UserAdminServlet adminServlet;

  @Mock private HttpServletRequest mockRequest;
  @Mock private HttpSession mockSession;
  @Mock private HttpServletResponse mockResponse;
  @Mock private RequestDispatcher mockRequestDispatcher;
  @Mock private ConversationStore mockConversationStore;
  @Mock private User mockUser;
  @Mock private UserStore mockUserStore;

  @Before
  public void setup() {
    adminServlet = new UserAdminServlet();

    MockitoAnnotations.initMocks(this);
    when(mockRequest.getSession()).thenReturn(mockSession);

    when(mockRequest.getRequestDispatcher("/WEB-INF/view/admin-user.jsp"))
        .thenReturn(mockRequestDispatcher);
    adminServlet.setConversationStore(mockConversationStore);
    adminServlet.setUserStore(mockUserStore);
  }

  @Test
  public void testDoGet() throws IOException, ServletException {
    when(mockRequest.getParameter("username")).thenReturn("foo");
    when(mockUserStore.getUser("foo")).thenReturn(mockUser);
    when(mockSession.getAttribute("user")).thenReturn("admin");

    adminServlet.doGet(mockRequest, mockResponse);

    verify(mockRequest).setAttribute("userToBeShown", mockUser);
    verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoPost_block() throws IOException, ServletException {
    when(mockRequest.getParameter("username")).thenReturn("foo");
    when(mockRequest.getParameter("action")).thenReturn("Block");
    when(mockUserStore.getUser("foo")).thenReturn(mockUser);
    when(mockSession.getAttribute("user")).thenReturn("admin");

    adminServlet.doPost(mockRequest, mockResponse);

    verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    verify(mockUserStore).addUser(any());
  }

  @Test
  public void testDoPost_unblock() throws IOException, ServletException {
    when(mockRequest.getParameter("username")).thenReturn("foo");
    when(mockRequest.getParameter("action")).thenReturn("Unblock");
    when(mockUserStore.getUser("foo")).thenReturn(mockUser);
    when(mockSession.getAttribute("user")).thenReturn("admin");

    adminServlet.doPost(mockRequest, mockResponse);

    verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    verify(mockUserStore).addUser(any());
  }

  @Test
  public void testDoPost_reset() throws IOException, ServletException {
    when(mockRequest.getParameter("username")).thenReturn("foo");
    when(mockRequest.getParameter("action")).thenReturn("Reset");
    when(mockUserStore.getUser("foo")).thenReturn(mockUser);
    when(mockSession.getAttribute("user")).thenReturn("admin");

    adminServlet.doPost(mockRequest, mockResponse);

    verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    verify(mockUserStore).addUser(any());
  }

  @Test
  public void testDoPost_notACommand() throws IOException, ServletException {
    when(mockRequest.getParameter("username")).thenReturn("foo");
    when(mockRequest.getParameter("action")).thenReturn("BLAAAH");
    when(mockUserStore.getUser("foo")).thenReturn(mockUser);
    when(mockSession.getAttribute("user")).thenReturn("admin");

    adminServlet.doPost(mockRequest, mockResponse);

    verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    verify(mockUserStore, never()).addUser(any());
  }
}
