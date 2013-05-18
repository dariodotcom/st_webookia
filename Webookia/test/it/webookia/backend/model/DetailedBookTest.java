package it.webookia.backend.model;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class DetailedBookTest extends AppEngineTestCase {

    private DetailedBook model = new DetailedBook();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
