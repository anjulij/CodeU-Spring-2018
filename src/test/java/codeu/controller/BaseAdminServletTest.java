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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import codeu.model.data.Conversation;
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

public class BaseAdminServletTest {

  /**
   * A stubbed (not mocked) subclass of a base admin servlet. Testing subclasses in mockito is a bit
   * harder, since you actually have to provide an implementation of pure abstract methods, and this
   * is a quick and dirty way to do it.
   */
  private static final class StubbedAdminServlet extends BaseAdminServlet {
    int numValidatedGetCalls;
    int numValidatedPostCalls;

    @Override
    protected void onValidatedGet(HttpServletRequest request, HttpServletResponse response) {
      ++numValidatedGetCalls;
    }

    @Override
    protected void onValidatedPost(HttpServletRequest request, HttpServletResponse response) {
      ++numValidatedPostCalls;
    }
  }

  private StubbedAdminServlet adminServlet;

  @Mock private HttpServletRequest mockRequest;
  @Mock private HttpSession mockSession;
  @Mock private HttpServletResponse mockResponse;
  @Mock private RequestDispatcher mockRequestDispatcher;
  @Mock private ConversationStore mockConversationStore;
  @Mock private UserStore mockUserStore;

  @Before
  public void setup() {
    adminServlet = new StubbedAdminServlet();

    MockitoAnnotations.initMocks(this);
    when(mockRequest.getSession()).thenReturn(mockSession);

    when(mockRequest.getRequestDispatcher("/WEB-INF/view/admin.jsp"))
        .thenReturn(mockRequestDispatcher);
    adminServlet.setConversationStore(mockConversationStore);
    adminServlet.setUserStore(mockUserStore);
  }

  @Test
  public void testDoGet() throws IOException, ServletException {
    when(mockSession.getAttribute("user")).thenReturn("admin");
    adminServlet.doGet(mockRequest, mockResponse);
    assertEquals(1, adminServlet.numValidatedGetCalls);
    assertEquals(0, adminServlet.numValidatedPostCalls);
  }

  @Test
  public void testDoGet_UserNotLoggedIn() throws IOException, ServletException {
    when(mockSession.getAttribute("user")).thenReturn(null);

    adminServlet.doGet(mockRequest, mockResponse);

    verify(mockConversationStore, never()).addConversation(any(Conversation.class));
    verify(mockResponse).sendRedirect("/login");
    assertEquals(0, adminServlet.numValidatedGetCalls);
    assertEquals(0, adminServlet.numValidatedPostCalls);
  }

  @Test
  public void testDoGet_UserNotAdmin() throws IOException, ServletException {
    when(mockSession.getAttribute("user")).thenReturn("howdy");

    adminServlet.doGet(mockRequest, mockResponse);

    verify(mockConversationStore, never()).addConversation(any(Conversation.class));
    verify(mockResponse).sendRedirect("/conversations");
    assertEquals(0, adminServlet.numValidatedGetCalls);
    assertEquals(0, adminServlet.numValidatedPostCalls);
  }
}
