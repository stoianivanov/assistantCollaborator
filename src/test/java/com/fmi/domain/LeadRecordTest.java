package com.fmi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fmi.web.rest.TestUtil;

public class LeadRecordTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeadRecord.class);
        LeadRecord leadRecord1 = new LeadRecord();
        leadRecord1.setId(1L);
        LeadRecord leadRecord2 = new LeadRecord();
        leadRecord2.setId(leadRecord1.getId());
        assertThat(leadRecord1).isEqualTo(leadRecord2);
        leadRecord2.setId(2L);
        assertThat(leadRecord1).isNotEqualTo(leadRecord2);
        leadRecord1.setId(null);
        assertThat(leadRecord1).isNotEqualTo(leadRecord2);
    }
}
