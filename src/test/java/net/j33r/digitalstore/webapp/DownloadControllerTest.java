package net.j33r.digitalstore.webapp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DownloadControllerTest {

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() throws Exception {
        mockMvc = webAppContextSetup(context).build();
    }

    @Test
    public void download() throws Exception {
        final ResultActions action = mockMvc.perform(get("/download/2"));

        final MockHttpServletResponse response = action.andReturn().getResponse();
        final byte[] content = response.getContentAsByteArray();

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Assert.assertTrue(response.containsHeader("Content-disposition"));
        Assert.assertEquals(String.format("attachment;filename=\"%s\"", "machu-picchu.jpg"),
                response.getHeaderValue("Content-disposition"));
        final int expectedLength = 339239;
        Assert.assertEquals(expectedLength, response.getHeaderValue("Content-length"));
        Assert.assertEquals(expectedLength, content.length);
    }
}
