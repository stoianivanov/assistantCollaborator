package com.fmi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fmi.web.rest.TestUtil;

public class DisciplineRecordTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DisciplineRecord.class);
        DisciplineRecord disciplineRecord1 = new DisciplineRecord();
        disciplineRecord1.setId(1L);
        DisciplineRecord disciplineRecord2 = new DisciplineRecord();
        disciplineRecord2.setId(disciplineRecord1.getId());
        assertThat(disciplineRecord1).isEqualTo(disciplineRecord2);
        disciplineRecord2.setId(2L);
        assertThat(disciplineRecord1).isNotEqualTo(disciplineRecord2);
        disciplineRecord1.setId(null);
        assertThat(disciplineRecord1).isNotEqualTo(disciplineRecord2);
    }
}
