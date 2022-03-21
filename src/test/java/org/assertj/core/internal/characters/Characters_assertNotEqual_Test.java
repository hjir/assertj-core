/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2022 the original author or authors.
 */
package org.assertj.core.internal.characters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.error.ShouldNotBeEqual.shouldNotBeEqual;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.Characters;
import org.assertj.core.internal.CharactersBaseTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for <code>{@link Characters#assertNotEqual(AssertionInfo, Character, char)}</code>.
 * 
 * @author Alex Ruiz
 * @author Joel Costigliola
 */
class Characters_assertNotEqual_Test extends CharactersBaseTest {

  @Test
  void should_fail_if_actual_is_null() {
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> characters.assertNotEqual(someInfo(), null, 'a'))
                                                   .withMessage(actualIsNull());
  }

  @Test
  void should_pass_if_characters_are_not_equal() {
    characters.assertNotEqual(someInfo(), 'a', 'b');
  }

  @Test
  void should_fail_if_characters_are_equal() {
    AssertionInfo info = someInfo();

    Throwable error = catchThrowable(() -> characters.assertNotEqual(info, 'b', 'b'));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldNotBeEqual('b', 'b'));
  }

  @Test
  void should_fail_if_actual_is_null_whatever_custom_comparison_strategy_is() {
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> charactersWithCaseInsensitiveComparisonStrategy.assertNotEqual(someInfo(),
                                                                                                                                    null,
                                                                                                                                    'a'))
                                                   .withMessage(actualIsNull());
  }

  @Test
  void should_pass_if_characters_are_not_equal_according_to_custom_comparison_strategy() {
    charactersWithCaseInsensitiveComparisonStrategy.assertNotEqual(someInfo(), 'a', 'b');
  }

  @Test
  void should_fail_if_characters_are_equal_according_to_custom_comparison_strategy() {
    AssertionInfo info = someInfo();

    Throwable error = catchThrowable(() -> charactersWithCaseInsensitiveComparisonStrategy.assertNotEqual(info, 'b', 'B'));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldNotBeEqual('b', 'B', caseInsensitiveComparisonStrategy));
  }
}
