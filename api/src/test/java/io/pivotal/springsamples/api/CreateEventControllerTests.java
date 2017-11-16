package io.pivotal.springsamples.api;

import io.pivotal.springsamples.CreateEvent;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(CreateEventController.class)
@RunWith(SpringRunner.class)
public class CreateEventControllerTests {

    @ClassRule
    public static final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateEvent createEvent;

    @BeforeClass
    public static void beforeClass() {
        environmentVariables.set("UPCOMING_EVENTS_WINDOW", "1");
        assertThat(System.getenv("UPCOMING_EVENTS_WINDOW")).isEqualTo("1");
    }

    @Test
    public void createEvent_ShouldReturnAJsonEvent() throws Exception {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String title = "Lunchtime Drawtime";
        String id = "e5992da5-3481-494e-bb08-0fe865e6b706";
        String path = "/api/events";

        Mockito.when(createEvent.perform(Mockito.anyString(), Mockito.any(LocalDate.class), Mockito.any(LocalDateTime.class), Mockito.any(CreateEvent.ResultHandler.class)))
                .thenReturn(
                        ResponseEntity
                                .created(UriComponentsBuilder.newInstance().path(path + "/" + id).build().toUri())
                                .body(new EventJson(id, title, date)));

        this.mockMvc.perform(MockMvcRequestBuilders.post(path)
                .content("{\n" +
                        "  \"title\": \"" + title + "\",\n" +
                        "  \"date\": \"" + date + "\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.header().string("Location", path + "/" + id))
                .andExpect(MockMvcResultMatchers.jsonPath("@.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("@.title").value(title))
                .andExpect(MockMvcResultMatchers.jsonPath("@.date").value(date));
    }

    @Test
    public void createEventWithInvalidDate_ShouldReturnAnErrorResponse() throws Exception {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("d MMM uuuu"));
        String title = "Lunchtime Drawtime";
        String path = "/api/events";

        this.mockMvc.perform(MockMvcRequestBuilders.post(path)
                .content("{\n" +
                        "  \"title\": \"" + title + "\",\n" +
                        "  \"date\": \"" + date + "\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("@.errors[0].code").value("invalid_date"))
                .andExpect(MockMvcResultMatchers.jsonPath("@.errors[0].description").value("The date must be in yyyy-mm-dd format"));
    }

    @Configuration
    @Import(CreateEventController.class)
    static class Config {
    }

}
