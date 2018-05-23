package daewook.assignment.urlshortener.utils;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class Base52EncoderTest {
    @Test
    public void testToBase52() {
        assertEquals(Base52Encoder.encode(0), "a");
        assertEquals(Base52Encoder.encode(25), "z");
        assertEquals(Base52Encoder.encode(26), "A");
        assertEquals(Base52Encoder.encode(51), "Z");
    }

    @Test
    public void testFromBase52() {
        assertEquals(Base52Encoder.decode("a"), 0);
        assertEquals(Base52Encoder.decode("z"), 25);
        assertEquals(Base52Encoder.decode("A"), 26);
        assertEquals(Base52Encoder.decode("Z"), 51);
    }

    @Test
    public void testComebackOriginal() {
        assertEquals(0, Base52Encoder.decode(Base52Encoder.encode(0)));
        assertEquals(51, Base52Encoder.decode(Base52Encoder.encode(51)));
        assertEquals(123123123, Base52Encoder.decode(Base52Encoder.encode(123123123)));
    }
}
