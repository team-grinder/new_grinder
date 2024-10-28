package com.grinder;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JasyptTest {

    @Autowired
    private StringEncryptor jasyptStringEncryptor;

    @Test
    void encryptTest() {

        String key = System.getProperty("jasypt.encryptor.password");
        System.out.println("============= 암호화 테스트 =============");
        System.out.println("암호화 키: " + key);

        // 암호화 할 데이터들
        // 필요 시 여기에 형식에 맞춰 추가 후 적용
        String url = "jdbc url";
        String username = "유저명";
        String password = "패스워드";
        String driver = "드라이버 클래스 명";

        // 암호화 결과값
        System.out.println("============= 암호화 값 =============");
        System.out.println("url=" + jasyptStringEncryptor.encrypt(url) );
        System.out.println("username=" + jasyptStringEncryptor.encrypt(username));
        System.out.println("password=" + jasyptStringEncryptor.encrypt(password));
        System.out.println("driver-class-name=" + jasyptStringEncryptor.encrypt(driver));

        // 테스트를 위한 검증
        String encryptedText = jasyptStringEncryptor.encrypt("test");
        String decryptedText = jasyptStringEncryptor.decrypt(encryptedText);
        assertThat(decryptedText).isEqualTo("test");
    }
}