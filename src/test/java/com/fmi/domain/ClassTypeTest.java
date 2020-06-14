package com.fmi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fmi.web.rest.TestUtil;

public class ClassTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassType.class);
        ClassType classType1 = new ClassType();
        classType1.setId(1L);
        ClassType classType2 = new ClassType();
        classType2.setId(classType1.getId());
        assertThat(classType1).isEqualTo(classType2);
        classType2.setId(2L);
        assertThat(classType1).isNotEqualTo(classType2);
        classType1.setId(null);
        assertThat(classType1).isNotEqualTo(classType2);
    }
}
