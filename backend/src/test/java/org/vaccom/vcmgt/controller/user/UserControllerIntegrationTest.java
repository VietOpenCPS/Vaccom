package org.vaccom.vcmgt.controller.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.vaccom.vcmgt.config.DisableSecurityConfig;

/**
 * FIXME: Currently not checking the authority by disable the security filter
 *
 * @author TrinhNX
 * @since 8/27/2021
 */
@SpringBootTest(classes = DisableSecurityConfig.class)
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void checkinQR() throws Exception {
        final String qrCode = "xxx";
        mockMvc.perform(MockMvcRequestBuilders.put("/rest/v1/user/checkin/{0}", qrCode))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}