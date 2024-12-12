package com.grinder.web.main.cafe;

import com.grinder.CreateDummy;
import com.grinder.domain.cafe.model.Cafe;
import com.grinder.domain.cafe.service.CafeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
class CafeControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private CafeController cafeController;

    @Autowired
    private CafeService cafeService;

    @Autowired
    private CreateDummy createDummy;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cafeController).build();
    }

    @Test
    @DisplayName("카페 정보 조회 테스트")
    @Transactional
    void getCafeData() throws Exception {
        // given
        createDummy.createCafeDummy(100);

        Cafe cafe = cafeService.findCafeByName("카페1").get(0);

        mockMvc.perform(get("/cafe/" + cafe.getId())
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.cafe.name").value("카페1"));
    }
}