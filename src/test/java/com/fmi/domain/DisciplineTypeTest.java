package com.fmi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fmi.web.rest.TestUtil;

public class DisciplineTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DisciplineType.class);
        DisciplineType disciplineType1 = new DisciplineType();
        disciplineType1.setId(1L);
        DisciplineType disciplineType2 = new DisciplineType();
        disciplineType2.setId(disciplineType1.getId());
        assertThat(disciplineType1).isEqualTo(disciplineType2);
        disciplineType2.setId(2L);
        assertThat(disciplineType1).isNotEqualTo(disciplineType2);
        disciplineType1.setId(null);
        assertThat(disciplineType1).isNotEqualTo(disciplineType2);
    }
}
