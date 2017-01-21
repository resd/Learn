import com.resd.test.webview.entities.BlogEntry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BlogEntryController {

    /** First way to create json response method
    * */
    @RequestMapping("/test")
    public ResponseEntity<Object> testJson() {
        BlogEntry entry = new BlogEntry();
        entry.setTitle("Test Blog Entry");
        return new ResponseEntity<>(entry, HttpStatus.OK);
    }

    /** Second way to create json response method
     * */
    @GetMapping("/test")
    public @ResponseBody BlogEntry testJson2() {
        BlogEntry entry = new BlogEntry();
        entry.setTitle("Test Blog Entry");
        return entry;
    }

    /** Way to create json request method 
     * */
    @PostMapping("/testPost")
    public @ResponseBody BlogEntry testJson2post(
            @RequestBody BlogEntry entry) {
        return entry;
    }
}


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

//@WebAppConfiguration
public class BlogEntryControllerTest {

    @InjectMocks
    private BlogEntryController controller;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void test() throws Exception {
//        mockMvc.perform(get("/test")).andDo(print());
        mockMvc.perform(post("/testPost")
                .content("{\"title\": \"Blog Entry Test\"}")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$.title", is("Blog Entry Test")))
                .andDo(print());
    }
}