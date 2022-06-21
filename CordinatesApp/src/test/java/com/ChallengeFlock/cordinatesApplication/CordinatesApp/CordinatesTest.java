package com.ChallengeFlock.cordinatesApplication.CordinatesApp;

import com.ChallengeFlock.cordinatesApplication.CordinatesApp.controller.CordinatesController;
import com.ChallengeFlock.cordinatesApplication.CordinatesApp.service.CordinatesService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = CordinatesAppApplication.class)
@AutoConfigureMockMvc
public class CordinatesTest {

    @Autowired
    public MockMvc mockMvc;

    @InjectMocks
    private CordinatesController controller = mock(CordinatesController.class);

    @MockBean
    private CordinatesService cordinatesService = mock(CordinatesService.class);

    @Test
    public void shouldReturnProvinceData() throws Exception {
        String name = "Sgo. del Estero";
        String expected = "Cordenadas de la Provincia: {latitude=-27.7824116550944, longitude=-63.2523866568588}";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> responseEntity = new ResponseEntity<>(
                expected,
                header,
                HttpStatus.OK
        );

        when(cordinatesService.obtainProvinceCordsWithName(name)).thenReturn(responseEntity);
    }

}
