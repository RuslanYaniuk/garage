package com.garage.test.controllers;

import com.garage.dto.GarageConfigDTO;
import org.junit.Test;

import static com.garage.test.utils.CarFixtures.getNewCar;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hibernate.criterion.Restrictions.isNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GarageAdministrationControllerTests extends AbstractControllerTest {

    @Test
    public void addGarage_validGarageConfig_garageAdded() throws Exception {
        GarageConfigDTO garageConfigDTO = new GarageConfigDTO(2, 50);

        mockMvc.perform(put("/garage")
                .contentType(APPLICATION_JSON_UTF8)
                .content(jacksonObjectMapper.writeValueAsString(garageConfigDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("complete")))
                .andExpect(jsonPath("$.object.id", not(null)))
                .andExpect(jsonPath("$.object.numberOfLevels", is(garageConfigDTO.getNumberOfLevels())))
                .andExpect(jsonPath("$.object.lotsOnLevel", is(garageConfigDTO.getLotsOnLevel())));
    }

    @Test
    public void addGarage_inValidGarageConfig_garageAdded() throws Exception {
        GarageConfigDTO garageConfigDTO = new GarageConfigDTO(-2, 50);

        mockMvc.perform(put("/garage")
                .contentType(APPLICATION_JSON_UTF8)
                .content(jacksonObjectMapper.writeValueAsString(garageConfigDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is("GarageConfigurationException")));

        garageConfigDTO = new GarageConfigDTO(2, null);
        mockMvc.perform(put("/garage")
                .contentType(APPLICATION_JSON_UTF8)
                .content(jacksonObjectMapper.writeValueAsString(garageConfigDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is("ValidationException")));
    }


}
