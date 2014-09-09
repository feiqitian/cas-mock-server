/*
 * File created on Sep 9, 2014 
 *
 * Copyright (c) Carl Harris, Jr.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.soulwing.cas.server.web;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.soulwing.cas.server.Credential;
import org.soulwing.cas.server.CredentialFactory;
import org.soulwing.cas.server.service.AuthenticationException;
import org.soulwing.cas.server.service.LoginService;
import org.soulwing.cas.server.service.NotAuthenticException;

/**
 * A backing bean for the CAS login page.
 *
 * @author Carl Harris
 */
@Named
@RequestScoped
public class LoginBean {

  static final String SUCCESS_OUTCOME_ID = "success";
    
  static final String FAILURE_OUTCOME_ID = "failure";
  
  @Inject
  protected CredentialFactory credentialFactory;
  
  @Inject
  protected LoginService loginService;
  
  @Inject
  protected Errors errors;
  
  private Credential credential;
  
  
  /**
   * Gets the {@code credential} property.
   * @return property value
   */
  public Credential getCredential() {
    return credential;
  }

  /**
   * Initializes this bean.
   */
  @PostConstruct
  public void init() {
    credential = credentialFactory.newCredential();
  }
  
  /**
   * An action method that is fired when the user clicks the 'Login' button
   * on the form.
   * @return outcome ID
   */
  public String login() {
    try {
      loginService.authenticate(credential);
      return SUCCESS_OUTCOME_ID;
    }
    catch (NotAuthenticException ex) {
      errors.addError("invalidUsernameOrPassword");
      return null;
    }
    catch (AuthenticationException ex) {
      return FAILURE_OUTCOME_ID;
    }
  }
  
}