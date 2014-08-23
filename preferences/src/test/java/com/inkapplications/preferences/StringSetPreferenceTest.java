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

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.fest.assertions.api.Assertions.assertThat;

@Config(emulateSdk=18)
@RunWith(RobolectricTestRunner.class)
public class StringSetPreferenceTest {

  SharedPreferences preferences;

  @Before
  public void setup() {
    preferences = PreferenceManager
        .getDefaultSharedPreferences(Robolectric.application);
  }

  @Test
  public void should_save_stringSet_when_calling_set() {
    // Arrange
    Set<String> expected = new LinkedHashSet<>();
    expected.add("Test1");
    expected.add("Test2");
    expected.add("Test3");
    String key = "testKey";
    StringSetPreference preference = new StringSetPreference(preferences, key);

    // Act
    preference.set(expected);

    // Assert
    assertThat(preferences.getStringSet(key, null)).isEqualTo(expected);
  }

  @Test
  public void should_not_throw_exception_when_calling_set_with_null() {
    // Arrange
    String key = "testKey1";
    StringSetPreference preference = new StringSetPreference(preferences, key);


    // Act
    preference.set(null);

    // Assert
    assertThat(preferences.getString(key, null)).isNull();
  }

  @Test
  public void should_get_value_when_calling_get() {
    // Arrange
    Set<String> expected = new LinkedHashSet<>();
    expected.add("Test1");
    expected.add("Test2");
    expected.add("Test3");
    String key = "testKey2";
    preferences.edit().putStringSet(key, expected).commit();
    StringSetPreference preference = new StringSetPreference(preferences, key);

    // Act
    Set<String> result = preference.get();

    // Assert
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void should_return_true_when_isSet_is_called_and_value_is_set() {
    // Arrange
    String key = "testKey3";
    preferences.edit().putStringSet(key, Collections.<String>emptySet()).commit();
    StringSetPreference preference = new StringSetPreference(preferences, key);

    // Act
    boolean result = preference.isSet();

    // Assert
    assertThat(result).isTrue();
  }

  @Test
  public void should_return_false_when_isSet_is_called_and_value_is_not_set() {
    // Arrange
    String key = "testKey4";
    StringSetPreference preference = new StringSetPreference(preferences, key);

    // Act
    boolean result = preference.isSet();

    // Assert
    assertThat(result).isFalse();
  }

  @Test
  public void should_delete_preference() {
    // Arrange
    Set<String> testSet = new LinkedHashSet<>();
    testSet.add("Test1");
    testSet.add("Test2");
    testSet.add("Test3");
    String key = "testKey5";
    preferences.edit().putStringSet(key, testSet).commit();
    StringSetPreference preference = new StringSetPreference(preferences, key);

    // Act
    preference.delete();

    // Assert
    assertThat(preferences.contains(key)).isFalse();
  }
}
