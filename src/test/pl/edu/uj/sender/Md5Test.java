package pl.edu.uj.sender;


import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Md5Test {

    @Test
    public void givenPassword_whenHashingUsingCommons_thenVerifying() {

        String hash = "35454B055CC325EA1AF2126E27707052";
        String password = "ILoveJava";

        String md5Hex = DigestUtils.md5Hex(password).toUpperCase();

        Assertions.assertEquals(md5Hex, hash);
    }
}
