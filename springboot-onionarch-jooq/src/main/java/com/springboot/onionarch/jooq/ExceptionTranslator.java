/**
 * Copyright (c) 2009-2015, Data Geekery GmbH (http://www.datageekery.com)
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Other licenses:
 * -----------------------------------------------------------------------------
 * Commercial licenses for this work are available. These replace the above
 * ASL 2.0 and offer limited warranties, support, maintenance, and commercial
 * database integrations.
 *
 * For more information, please visit: http://www.jooq.org/licenses
 *
 */
package com.springboot.onionarch.jooq;

import org.jooq.ExecuteContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultExecuteListener;

import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ExceptionTranslator extends DefaultExecuteListener {

  /**
   * Generated UID
   */
  private static final long serialVersionUID = -2450323227461061152L;

  @Override
  public void exception(ExecuteContext ctx) {

    // [#4391] Translate only SQLExceptions
    if (ctx.sqlException() != null) {
      SQLDialect dialect = ctx.dialect();
      SQLExceptionTranslator translator = (dialect != null)
          ? new SQLErrorCodeSQLExceptionTranslator(dialect.thirdParty().springDbName())
          : new SQLStateSQLExceptionTranslator();

      ctx.exception(translator.translate("jOOQ", ctx.sql(), ctx.sqlException()));
    }
  }
}
