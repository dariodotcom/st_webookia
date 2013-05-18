package it.webookia.backend.model;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class BookDetailsTest extends AppEngineTestCase {

    private BookDetails model = new BookDetails();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
