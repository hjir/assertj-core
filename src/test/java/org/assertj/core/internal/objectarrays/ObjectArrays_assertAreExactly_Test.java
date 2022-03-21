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
package org.assertj.core.internal.objectarrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.error.ElementsShouldBeExactly.elementsShouldBeExactly;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.util.Arrays.array;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.api.Condition;
import org.assertj.core.internal.ObjectArrays;
import org.assertj.core.internal.ObjectArraysWithConditionBaseTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for <code>{@link ObjectArrays#assertAreExactly(AssertionInfo, Object[], int, Condition)}</code> .
 * 
 * @author Nicolas François
 * @author Mikhail Mazursky
 * @author Joel Costigliola
 */
class ObjectArrays_assertAreExactly_Test extends ObjectArraysWithConditionBaseTest {

  @Test
  void should_pass_if_satisfies_exactly_times_condition() {
    actual = array("Yoda", "Luke", "Leia");
    arrays.assertAreExactly(someInfo(), actual, 2, jedi);
    verify(conditions).assertIsNotNull(jedi);
  }

  @Test
  void should_throw_error_if_condition_is_null() {
    assertThatNullPointerException().isThrownBy(() -> {
      actual = array("Yoda", "Luke");
      arrays.assertAreExactly(someInfo(), actual, 2, null);
    }).withMessage("The condition to evaluate should not be null");
    verify(conditions).assertIsNotNull(null);
  }

  @Test
  void should_fail_if_condition_is_not_met_enough() {
    testCondition.shouldMatch(false);
    AssertionInfo info = someInfo();
    actual = array("Yoda", "Solo", "Leia");

    Throwable error = catchThrowable(() -> arrays.assertAreExactly(someInfo(), actual, 2, jedi));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(conditions).assertIsNotNull(jedi);
    verify(failures).failure(info, elementsShouldBeExactly(actual, 2, jedi));
  }

  @Test
  void should_fail_if_condition_is_met_much() {
    testCondition.shouldMatch(false);
    AssertionInfo info = someInfo();
    actual = array("Yoda", "Luke", "Obiwan");

    Throwable error = catchThrowable(() -> arrays.assertAreExactly(someInfo(), actual, 2, jedi));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(conditions).assertIsNotNull(jedi);
    verify(failures).failure(info, elementsShouldBeExactly(actual, 2, jedi));
  }

}
