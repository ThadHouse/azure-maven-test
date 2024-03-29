/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.wpi.first.wpilibj.controller.PIDController;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PIDInputOutputTest {
  private PIDController m_controller;

  @BeforeEach
  void setUp() {
    m_controller = new PIDController(0, 0, 0);
  }

  @Test
  void outputRangeTest() {
    m_controller.setP(1);
    m_controller.setOutputRange(-50, 50);

    assertAll(
        () -> assertEquals(-50, m_controller.calculate(100, 0), 1e-5),
        () -> assertEquals(50, m_controller.calculate(0, 100), 1e-5)
    );
  }

  @Test
  void inputRangeTest() {
    m_controller.setP(1);
    m_controller.setOutputRange(-1000, 1000);
    m_controller.setInputRange(-50, 50);

    assertAll(
        () -> assertEquals(-100, m_controller.calculate(100, 0), 1e-5),
        () -> assertEquals(50, m_controller.calculate(0, 100), 1e-5)
    );
  }

  @Test
  void continuousInputTest() {
    m_controller.setP(1);
    m_controller.enableContinuousInput(-180, 180);

    assertTrue(m_controller.calculate(-179, 179) < 0.0);
  }

  @Test
  void proportionalGainOutputTest() {
    m_controller.setP(4);

    assertEquals(-0.1, m_controller.calculate(0.025, 0), 1e-5);
  }

  @Test
  void integralGainOutputTest() {
    m_controller.setI(4);

    double out = 0;

    for (int i = 0; i < 5; i++) {
      out = m_controller.calculate(.025, 0);
    }

    assertEquals(-0.5 * m_controller.getPeriod(), out, 1e-5);
  }

  @Test
  void derivativeGainOutputTest() {
    m_controller.setD(4);

    m_controller.calculate(0, 0);

    assertEquals(-0.01 / m_controller.getPeriod(), m_controller.calculate(0.0025, 0), 1e-5);
  }
}
