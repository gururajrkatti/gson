/*
 * Copyright (C) 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.gson;

import java.io.EOFException;
import java.io.Reader;
import java.io.StringReader;

/**
 * A parser that allows reading of multiple {@link JsonElement}s from the specified reader
 * asynchronously.
 *
 * @author Inderjeet Singh
 * @author Joel Leitch
 * @since 1.4
 */
public final class JsonParserAsync {

  private final JsonParserJavacc parser;

  /**
   * @param json The string containing JSON elements concatenated to each other.
   * @since 1.4
   */
  public JsonParserAsync(String json) {
    this(new StringReader(json));      
  }
  
  /**
   * @param reader The data stream containing JSON elements concatenated to each other.
   * @since 1.4
   */
  public JsonParserAsync(Reader reader) {
    parser = new JsonParserJavacc(reader);      
  }
  
  /**
   * Returns the next available {@link JsonElement} on the reader. Null if none available.
   * 
   * @return the next available {@link JsonElement} on the reader. Null if none available.
   * @throws JsonParseException if the incoming stream is malformed JSON.
   * @since 1.4
   */
  public JsonElement nextElement() throws JsonParseException {
    try {
      JsonElement element = parser.parse();
      return element;
    } catch (TokenMgrError e) {
      throw new JsonParseException("Failed parsing JSON source to Json", e);
    } catch (ParseException e) {
      throw new JsonParseException("Failed parsing JSON source to Json", e);
    } catch (StackOverflowError e) {
      throw new JsonParseException("Failed parsing JSON source to Json", e);
    } catch (OutOfMemoryError e) {
      throw new JsonParseException("Failed parsing JSON source to Json", e);
    } catch (JsonParseException e) {
      if (e.getCause() instanceof EOFException) {
        return null;
      } else {
        throw e;
      }
    }
  }    
}