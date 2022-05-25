package com.sb.resnyxbot.cbr;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class XmlCbrServiceTest {

    @Autowired
    private XmlMapper xmlMapper;

    @Test
    void loadEncoding() throws Exception {
        CurrRate rates = xmlMapper.readValue(
                XmlCbrServiceTest.class.getResource("/rates.xml"), CurrRate.class
        );
        LOG.info("{}", rates.getCurrRecords().get(3));
        Assertions.assertThat(
                rates.getCurrRecords().get(0).getName()
        ).isEqualTo("Австралийский доллар");
    }

    @Test
    void loadDynamic() throws Exception {
        CurrRange range = xmlMapper.readValue(
                XmlCbrServiceTest.class.getResource("/XML_dynamic.xml"), CurrRange.class
        );
        Assertions.assertThat(
                range.getRecords()
        ).hasSize(8);
    }

    @Test
    void testUpdate() {
        LocalDateTime first = LocalDateTime.of(2022, 2, 5, 23, 59, 59);
        LOG.info("{}", first);
        Assertions.assertThat(
                first.isBefore(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
        ).isTrue();
    }

    @Test
    void testRange() throws Exception {
        CurrRange range = xmlMapper.readValue(
                XmlCbrServiceTest.class.getResource("/XML_dynamic.xml"), CurrRange.class
        );
        List<CurrRange.RangeRecord> recs = range.getRecords();
        recs.sort(Comparator.comparing(CurrRange.RangeRecord::getDate).reversed());
        Assertions.assertThat(
                recs.get(0).getDate()
        ).isEqualTo(LocalDate.of(2001, 3, 14));
        Assertions.assertThat(
                recs.get(1).getDate()
        ).isEqualTo(LocalDate.of(2001, 3, 13));
    }
}