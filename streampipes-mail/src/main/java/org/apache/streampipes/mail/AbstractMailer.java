/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.apache.streampipes.mail;

import org.apache.streampipes.config.backend.BackendConfig;
import org.apache.streampipes.config.backend.model.EmailConfig;
import org.apache.streampipes.mail.config.MailConfigurationBuilder;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.email.EmailPopulatingBuilder;
import org.simplejavamail.api.email.Recipient;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;

import javax.mail.Message;
import java.util.List;
import java.util.stream.Collectors;

public class AbstractMailer {

  protected Mailer getMailer() {
    return getMailer(getEmailConfig());
  }

  protected Mailer getMailer(EmailConfig config) {
    return new MailConfigurationBuilder().buildMailerFromConfig(config);
  }

  protected EmailConfig getEmailConfig() {
    return BackendConfig.INSTANCE.getEmailConfig();
  }

  protected void deliverMail(Email email) {
    deliverMail(getEmailConfig(), email);
  }

  protected void deliverMail(EmailConfig config, Email email) {
    Mailer mailer = getMailer(config);
    mailer.sendMail(email);
  }

  protected EmailPopulatingBuilder baseEmail() {
    return baseEmail(getEmailConfig());
  }

  protected EmailPopulatingBuilder baseEmail(EmailConfig config) {
    return EmailBuilder
            .startingBlank()
            .from(config.getSenderAddress());
  }

  protected List<Recipient> toSimpleRecipientList(List<String> recipients) {
    return recipients
            .stream()
            .map(r -> new Recipient("", r, Message.RecipientType.TO))
            .collect(Collectors.toList());
  }
}