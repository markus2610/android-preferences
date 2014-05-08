/*
 * Copyright (C) 2014 Andrew Reitz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * Limitations under the License.
 */

package com.inkapplications.preferences;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class LongPreferenceTest {

  SharedPreferences preferences;

  @Before
  public void setup() {
    preferences = PreferenceManager
        .getDefaultSharedPreferences(Robolectric.application);
  }

  @Test
  public void should_save_long_when_calling_set() {
    // Arrange
    long expected = 5;
    String key = "testKey";
    LongPreference preference = new LongPreference(preferences, key);

    // Act
    preference.set(expected);

    // Assert
    assertThat(preferences.getLong(key, 0)).isEqualTo(expected);
  }

  @Test
  public void should_throw_exception_when_calling_set_with_null() {
    // Arrange
    String key = "testKey1";
    LongPreference preference = new LongPreference(preferences, key);


    // Act
    try {
      preference.set(null);
    } catch (NullPointerException e) {
      return;
    }

    // Assert
    throw new AssertionError("Calling 'set' with null did not throw");
  }

  @Test
  public void should_get_value_when_calling_get() {
    // Arrange
    long expected = 2;
    String key = "testKey2";
    preferences.edit().putLong(key, expected).commit();
    LongPreference preference = new LongPreference(preferences, key);

    // Act
    long result = preference.get();

    // Assert
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void should_return_true_when_isSet_is_called_and_value_is_set() {
    // Arrange
    String key = "testKey3";
    preferences.edit().putLong(key, 1).commit();
    LongPreference preference = new LongPreference(preferences, key);

    // Act
    boolean result = preference.isSet();

    // Assert
    assertThat(result).isTrue();
  }

  @Test
  public void should_return_false_when_isSet_is_called_and_value_is_not_set() {
    // Arrange
    String key = "testKey4";
    LongPreference preference = new LongPreference(preferences, key);

    // Act
    boolean result = preference.isSet();

    // Assert
    assertThat(result).isFalse();
  }

  @Test
  public void should_delete_preference() {
    // Arrange
    String key = "testKey5";
    preferences.edit().putLong(key, 9).commit();
    LongPreference preference = new LongPreference(preferences, key);

    // Act
    preference.delete();

    // Assert
    assertThat(preferences.contains(key)).isFalse();
  }
}