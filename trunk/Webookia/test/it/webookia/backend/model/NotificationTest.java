package it.webookia.backend.model;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class NotificationTest extends AppEngineTestCase {

    private Notification model = new Notification();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
