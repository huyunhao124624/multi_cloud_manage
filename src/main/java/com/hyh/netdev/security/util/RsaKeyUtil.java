package com.hyh.netdev.security.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * 单例模式生成RSA
 * 该方法耗时较久
 *
 * @author Albumen
 */
public class RsaKeyUtil {
    private final static Logger logger = LoggerFactory.getLogger(RsaKeyUtil.class);
    private static RsaKeyUtil instance = new RsaKeyUtil();

    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;


    private RsaKeyUtil() {
        try {
            loadPublicKey();
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    RSAPublicKey getPublicKey() {
        return publicKey;
    }

    public static RsaKeyUtil getInstance() {
        return instance;
    }


    private void loadPublicKey() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/jwtKey")));
        Security.addProvider(new BouncyCastleProvider());
        PEMKeyPair kp = (PEMKeyPair) new PEMParser(br).readObject();

        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        this.privateKey = (RSAPrivateKey) converter.getPrivateKey(kp.getPrivateKeyInfo());
        this.publicKey = (RSAPublicKey) converter.getPublicKey(kp.getPublicKeyInfo());
    }
}
