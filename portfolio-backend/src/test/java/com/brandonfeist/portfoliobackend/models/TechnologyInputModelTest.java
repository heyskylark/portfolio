package com.brandonfeist.portfoliobackend.models;

import static org.springframework.test.util.AssertionErrors.assertEquals;

import com.brandonfeist.portfoliobackend.models.domain.Technology;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TechnologyInputModelTest {

  private TechnologyInputModel technologyInputModel;

  @Before
  public void init() {
    technologyInputModel = new TechnologyInputModel();
    technologyInputModel.setName("React");
  }

  @Test
  public void whenConvertingTechnologyInputModelToTechnology_allValuesMatch() {
    final Technology technology = technologyInputModel.toTechnology();

    assertEquals("Technology names should match",
        technologyInputModel.getName(), technology.getName());
  }
}
