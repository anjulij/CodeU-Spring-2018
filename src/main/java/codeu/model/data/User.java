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

package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

/** Class representing a registered user. */
public class User {
  private final UUID id;
  private final String name;
  private final String password;
  private final Instant creation;
  private final boolean blocked;

  /**
   * Constructs a new User.
   *
   * @param id the ID of this User
   * @param name the username of this User
   * @param password the password of this User
   * @param creation the creation time of this User
   */
  public User(UUID id, String name, String password, Instant creation) {
    this(id, name, password, creation, false);
  }

  /**
   * Constructs a new User.
   *
   * @param id the ID of this User
   * @param name the username of this User
   * @param password the password of this User
   * @param creation the creation time of this User
   * @param blocked whether or not the user is blocked
   */
  public User(UUID id, String name, String password, Instant creation, boolean blocked) {
    this.id = id;
    this.name = name;
    this.password = password;
    this.creation = creation;
    this.blocked = blocked;
  }

  /** Returns a new user that is the same as the old user, but is blocked. */
  public static User blockUser(User original) {
    return new User(original.id, original.name, original.password, original.creation, true);
  }

  /** Returns a new user that is the same as the old user, but is not blocked. */
  public static User unblockUser(User original) {
    return new User(original.id, original.name, original.password, original.creation, false);
  }


  /** Edits the password for a particular user, returns a new user. */
  public static User resetPassword(User original, String password) {
    return new User(original.id, original.name, password, original.creation, original.blocked);
  }

  /** Returns the ID of this User. */
  public UUID getId() {
    return id;
  }

  /** Returns the username of this User. */
  public String getName() {
    return name;
  }

  /** Returns the password of this User. */
  public String getPassword() {
    return password;
  }

  /** Returns the creation time of this User. */
  public Instant getCreationTime() {
    return creation;
  }

  public boolean isBlocked() {
    return blocked;
  }
}
