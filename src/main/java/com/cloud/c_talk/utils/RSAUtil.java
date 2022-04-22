package com.cloud.c_talk.utils;

import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {

    static String pubKey1 = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEA97av9yGvCldbZMyQfiFi" +
            "zr6sAcJeYJZx2na6xOGrp9adqMdIU1PjtZlNdicm2nCtAAex0Q/2lqZgw8KtAIl/" +
            "dCg3OWkC+krnDrG4ZxG1H0GQ53FS8FIT74O8V2ij9zVbO/WG726iTzSJNxSy84jk" +
            "Wdn/fcVONJbcC05vWBgPYfS7cOYzK4DWL3MJtjU7UYOw8g5UAqsMNZ/4ix4IAD4R" +
            "FfPy52MHNeQz8CMQw6emwJryj4FMAKp487C1wa/iaYu02ZT9WvreuL7X36/W1LqM" +
            "eHcjSNg+9/kFk2HAJ4oJInQNpr21ssAeM4qbYt9Gq0PL43Pb03grhLH50O96Ykcc" +
            "LWHm+kO2FMxxbiBtPRr6aX8ZTTzT4M+pTqu5hgllXOxV9O7OlblJBbdaiAE4bUKp" +
            "mtwUzVSCxdyL/gJeRlXiEMu55q5x+eGSgwg7+8IQCLZsmTFXelw9EroMtZoNQZH8" +
            "CWQjOGMwmIx7IVqTO6Fnag2p/QqnvZRmZHXTAPypDcHpmzwYPlT38UhN6UpbNR2S" +
            "VMEa7oAvBZMX7rOxCsnW6foWJZGxZh8+gF3b4hcmTy/nRVgAG85AEVivPOhEs4eM" +
            "79Rm5aC7RoxJhgNdTh+gKSrN/cRor+Ro1U7il8WNULZkexu4n3wVexYrMr5I3vT3" +
            "qLxoBjAtTNfroAH+4bVDQX0CAwEAAQ==";

    static String priKey1 = "MIIJQQIBADANBgkqhkiG9w0BAQEFAASCCSswggknAgEAAoICAQD3tq/3Ia8KV1tk" +
            "zJB+IWLOvqwBwl5glnHadrrE4aun1p2ox0hTU+O1mU12JybacK0AB7HRD/aWpmDD" +
            "wq0AiX90KDc5aQL6SucOsbhnEbUfQZDncVLwUhPvg7xXaKP3NVs79YbvbqJPNIk3" +
            "FLLziORZ2f99xU40ltwLTm9YGA9h9Ltw5jMrgNYvcwm2NTtRg7DyDlQCqww1n/iL" +
            "HggAPhEV8/LnYwc15DPwIxDDp6bAmvKPgUwAqnjzsLXBr+Jpi7TZlP1a+t64vtff" +
            "r9bUuox4dyNI2D73+QWTYcAnigkidA2mvbWywB4zipti30arQ8vjc9vTeCuEsfnQ" +
            "73piRxwtYeb6Q7YUzHFuIG09GvppfxlNPNPgz6lOq7mGCWVc7FX07s6VuUkFt1qI" +
            "AThtQqma3BTNVILF3Iv+Al5GVeIQy7nmrnH54ZKDCDv7whAItmyZMVd6XD0Sugy1" +
            "mg1BkfwJZCM4YzCYjHshWpM7oWdqDan9Cqe9lGZkddMA/KkNwembPBg+VPfxSE3p" +
            "Sls1HZJUwRrugC8Fkxfus7EKydbp+hYlkbFmHz6AXdviFyZPL+dFWAAbzkARWK88" +
            "6ESzh4zv1GbloLtGjEmGA11OH6ApKs39xGiv5GjVTuKXxY1QtmR7G7iffBV7Fisy" +
            "vkje9PeovGgGMC1M1+ugAf7htUNBfQIDAQABAoICAALu3taGR5RPxH1FDtDvhEP+" +
            "bfr3Wja6kU6M6Vk7ctTh7av58BlMfP3BevFTNVqQyvudG7XmAn12Kt8mhvXiquet" +
            "MjozzB06ldHOuOg/hW2BUT3sUZcW4bWMUcaXLpnWv+XN2nMrVDCr5pQIYrYPgOSv" +
            "eB79HxfwUVqw6xCX+Faoly+XCjQRqneVXSATMUidzpKPKN9/Nv3Ljx3WlTGrinhA" +
            "Ezg6K/OvaA4H3EL00TymmQip90RqwFPiW8SXWI73YITTBsz0+34khFnZsymBq/gh" +
            "MQHDzbsR78b2+Vd+gu0tgpve0y/ypmjl00Ewc53Dh76BuLCgU6OWl+apb0GKpgK9" +
            "l1X6L+2FoxI0u66RWRvEzGkAubbuEy6K6klsu+/0usAQAqyqW9v+DTugyruF5W5B" +
            "Czy5idnnktfP8On4+Gh4TtzxIt/+JZMJyKKVNCclsv3DqARRo7jFU7yEnEPrROgY" +
            "Y1FNqqyECIw8/30DtddFCSfXWorvi+Ka3EBPBvhAio5w15vcdc1HAxB4DxrVRCtW" +
            "QQiTrdjDmpQPQjXUgpLV3ZUq0befoBhBlHFBICeOV+y0p+Yqb2JSSFzEgE6LOZdf" +
            "gwL/iVeN0DliBIf8zZPE/XEII5fxV68i0uj45XuhGoPZiIcVHpVz/A9vm2ihBaXi" +
            "i7DwCjEIJJykhTZZNLnhAoIBAQD86/MvmaJgn2jyIEjZxpcmLV5oxsv8dIaILvyd" +
            "UleiVUGBYdmEUa3i4viGaUoEnknRx2PLW/ABCoP/4SGJ8iDEG9S4ay6GcPrFh5q0" +
            "Bc8VcC7lF2gMxnBJUHVx2piGJfHzSnCeOtiZ8byJcUeMpgbFswllvimC33Is4ww8" +
            "eqlRSs+gZaW4EKAsAqDLeoMRRI1P/jMAdLWklry6+/3H0/HrTWCsOp/RDND20xCj" +
            "9yaebts3+4lRadGHRBIVtKlyrI0QYLilzuEQoKGKzaX5H0MRwBaAedDw/7wC/86L" +
            "YB1M4gYUaTs49NtJeQSPRzYlDTi44jLT59+m+L3L3GK2ys/PAoIBAQD6uoKeChGT" +
            "NlxWlcMXFRpLqq3LPLM+tzecq2oTNmv/Nort12YXwxp/m/Rrg9E4c4OwFBYlVTRl" +
            "0Nq9oXX9S9Vm2QoLM0mzBr/lHyURGJ/yGE22jJ2j51LrSihKP656aMaE1ZVM4o46" +
            "w121gvBi/RsTCnTLtq3FYUnKol/gFuCkei7GkV/ZDlTNAY65FIZhFihKuNRWBWHt" +
            "ZuoblldTsV7XOUDOFFG9IeOiL3iFMZKhegw+Kzqf9Fmo8hQayRjAYNkqKkvPQ8oM" +
            "4zgvWjY5x/e5pP0tSzcN7OZTIN+5XdolgNK0DTWIhBBv01x6mqdZuKdfX7pghGnI" +
            "KLpAFFqRQADzAoIBAA8FJCgLg60hSmY2rJziQk+uGhE23R5c/TBROgdNwI6PaZh5" +
            "ayWEhEMZH5E7531muROQN8j36MTnDve/MvKyQmfjDzr0gorRYtMgPnu6bJXlV6Ob" +
            "RD6d+kUgVhkw1iYBUuET5qQ+8mYSCRRQeeNMaNKM014LcQOoqWspAK0Gy89cFgB8" +
            "iN7pfdmfPlVXLTl1TL8XZYf9Zlwj54ljNasMVy2WJsuTn7IWhMJjvnU+VDjMdJBa" +
            "i8kd86eIz1oipH1+6dGzLMQMCSmnoHBRzi2BQcZoFe4DTflB9rRm5Kk6SAMs2ReV" +
            "OEMlp3Axk6e+nuSMaHXyWPpkps+PrhXRPLuGR9kCggEAeeJ/Krq0Tn8npkPJgggV" +
            "L2F6eQk6DavdkgDWYR6co4VtRi/nBngnAo/a4wVCiRHGm6s4FM/11+UO4Q6DoC3k" +
            "QMoTktxM4IXQdV6Nk2EIKBVeefajj5EdgxoaufV5KTKUBvEZUjX0dGaSwHE10LlA" +
            "FyKHIpzcns4rpCEJQJN+gxaOdKMpGAhaewpdymVK6hluXty5BFQ7hb/44I5WR247" +
            "L586fLPuBGZcFXTEPuOZZ3xARVyYdDfB+losIdVOff7WsyO6lLhcmaU/o+1T3QTz" +
            "zUCCfylISzG1ygV6HM+eA4s7YqHlO0cQAnyaQVO7NOeKHdgYb2EPV2lNGWeLm36k" +
            "twKCAQAapNXqiYvQkbnYH1NquaX1ZUkhDiVRn07j7m5DjAXRgUKaFtZpH4OXjzgl" +
            "cSUsvR6lC2A5DTe2wP3kpqI33rtG1K9Q+6Nrpz42/tzR6guqgieZdswWOdTvhonh" +
            "aVJGjqLxGZ9SOlRH+MMDyCAEPyoJ2ABwA9P5qGnWiRjaSL/HaOuipMWV6PO55XtT" +
            "iDulJwJaTYkWWlnaQBkmaXBOElM/km+taGd0ukbC22OlKA+f3pZqtrsKBsw4tgWf" +
            "ErZ2H2E1IFS9h/2hVQLs5d2l6eNTicPAl2GgrBDrE7hOEXF4gSpMXQqzH4/YnrZ4" +
            "REgd5NmsMSuToj9nzHDIBLJ31AfG";

    static String pubKey2 = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEA32jgOl6LoXJE2IN/YVkn" +
            "2Cfu+YQGqxKoqW5OvZ2ee2qmr5+qYRDw92u8BlMyVW8Zkwc063yg4n5IvD5v6T0V" +
            "fR5g7+FGhnkNvu5e+14aPaG2AChDj4bc9U7VMhJkEUuCPLjJSmqzl20goLB2UkGZ" +
            "G3I6gA/YGGxNlMK8fND4/TsCkx40IsZGlRytezjgeqbmLdO9Sur+z4Z76b6i0Qdj" +
            "u30wKEsDDPocdZyKcxg9BlyN9+SdpviG8+pj+43I6otN/Seya/YcHef12Ay7n6Dz" +
            "qfmkTMpHoAgtcOixblxsOdkcLf2AJXBkKS6u6ZwUKfDhyC2sISDkuTlnLJsyMFBO" +
            "4H5AMxaM964258QQYaxqqjJXXxCT04aNuXHDjbvzVPUsONAIy0DZVt59tXQchVq6" +
            "N5RvJ79zmIut/4IeV8S2EIy30/m4jX8fYNsrx574t1T6Dhc0+b9z6mBwiKglbVPJ" +
            "Chzlc7F2/ZFj5lY8OkKVapDpKWjIG36eKFl/zS3Sk4BciSKnbm6GDrhPknGvTIMf" +
            "W1UNBaIxTJJtbcpxNSLYdOJ6IAEdrcvHCwe5KFPWDSG6xkjMKU6+iH+5nMXXqftb" +
            "JuST1qMwjuEeCNByfGDhC6/DMUHNyL+kgMsJr4w4PxnFw7FkvfUvoX5t9NCtVEbs" +
            "GMdF1ZHV8tHrZ4LnWptb8kECAwEAAQ==";

    static String priKey2 = "MIIJRAIBADANBgkqhkiG9w0BAQEFAASCCS4wggkqAgEAAoICAQDfaOA6XouhckTY" +
            "g39hWSfYJ+75hAarEqipbk69nZ57aqavn6phEPD3a7wGUzJVbxmTBzTrfKDifki8" +
            "Pm/pPRV9HmDv4UaGeQ2+7l77Xho9obYAKEOPhtz1TtUyEmQRS4I8uMlKarOXbSCg" +
            "sHZSQZkbcjqAD9gYbE2Uwrx80Pj9OwKTHjQixkaVHK17OOB6puYt071K6v7Phnvp" +
            "vqLRB2O7fTAoSwMM+hx1nIpzGD0GXI335J2m+Ibz6mP7jcjqi039J7Jr9hwd5/XY" +
            "DLufoPOp+aRMykegCC1w6LFuXGw52Rwt/YAlcGQpLq7pnBQp8OHILawhIOS5OWcs" +
            "mzIwUE7gfkAzFoz3rjbnxBBhrGqqMldfEJPTho25ccONu/NU9Sw40AjLQNlW3n21" +
            "dByFWro3lG8nv3OYi63/gh5XxLYQjLfT+biNfx9g2yvHnvi3VPoOFzT5v3PqYHCI" +
            "qCVtU8kKHOVzsXb9kWPmVjw6QpVqkOkpaMgbfp4oWX/NLdKTgFyJIqduboYOuE+S" +
            "ca9Mgx9bVQ0FojFMkm1tynE1Ith04nogAR2ty8cLB7koU9YNIbrGSMwpTr6If7mc" +
            "xdep+1sm5JPWozCO4R4I0HJ8YOELr8MxQc3Iv6SAywmvjDg/GcXDsWS99S+hfm30" +
            "0K1URuwYx0XVkdXy0etngudam1vyQQIDAQABAoICAQCXF9PGRhkDGEgNcmmcyfJT" +
            "kzZhE/R6kIT3Cb5BKS984nVXA5H0UcPWYctpL60z8I6ITHBy7vfUFnpUrZl8Ua6N" +
            "zxTaRy4uVgKA5a/hKxRuKEbxtkly8U/Qq/8t/RgmRj4PmR2xsZZCTDCr3Iw5N1o4" +
            "ILoF0DFi2yRg/o8Bj0dF5Dnqi5vRBO3JfPqrAp129hdoWPKFj4bDjX2SaTauTeQa" +
            "ciE2rvyiMyk6NaPBEVx8R+5QABLLUPvcTOJPx+VhEtOs6cMWlv0PEMjI1Fu0KuOA" +
            "SnDe6lMknUM2QHl3XRRozzfqtq6LI4bp/0OWhNNyRhI9Ev597UQjLPplRZTc7gNZ" +
            "Nv1VsIVXni66U6/Iia6hSr77ypahKgnoRx+INUTWFC1+1Yr83JCGzyS76nccJ9Cg" +
            "Rf9Junff4mJ+c3Jg8Q/Xas7FZEu5hqPDD4L/sIkXi38WLwUY2gHM6+nu2acjQkmb" +
            "jTuEsh4JavWmPRU7irxzUGn7ZwNix12JkgPrbjFKflHJm/aM2gEuBemsxusDtXbB" +
            "YyHiFYQotOedRK2AjnDNvNZ6AcVwQAIdK/abS+qo+BwgQffGHVFY0523DLgjl0tn" +
            "SSCYLBwS37qPoivgKuUIrJZeR5qp4l/rDz8OYRflqTzh4nW1Qx/b913kOQbyAy7d" +
            "GB+VogRKI1f2Jz39xSnSDQKCAQEA935FYNAqfK4KLdCAkPgEaWo6BI/cVV32kJA7" +
            "68q+9yoDt0YbSvSZuzoitn7zXc38RnK4fY6gStkfJ/C82XuXuvjeLuFJCwNjLQZA" +
            "6c1HpKuE9Ulf9T12ihaLhGjmA/rUTqjECm/GDNcyUqZab+y1X4dnOvOrj90fNvyn" +
            "wyS6QkBOFv3wd6wElfsqGbqYuIjAPUehF+wpRfncBZfyYuQlUL/qCve4+soJx2uz" +
            "tUZ3hftJOIrTCLrRPpmzfhMqXoWFJQJiHKX3BYUEVyf8I3pJjco4HgYSo78wjPWU" +
            "q+o40aRrHNTr5pB0gQFGlRqTGiNFidOkAUaiFqYrZi+FKSwPfwKCAQEA5xawpLXX" +
            "RmUO/ZValK44GzrKjfB02KBHBg0SCEUfPfdgtCLG1dAE2JL9aBxVY49ID0rgiJA7" +
            "L/JeVAQwx9F+jQlAu5SiGxLxKCL0btPi8mJEgQjGY89iUi3qSjKLrnolBC61i705" +
            "22Nb5nSYrskh+wS8HU55ldzo9Gm2ewmoDu0hKmxICFcx4euA/EQ7MLUNA9lM9I37" +
            "E2KGKeirTyPCAcPIO1VzUKGIZHpBvMwe/JiEZ2rY2hLzlqNEGh1c5c5God0w98qf" +
            "pQr+otJc2ltsRyO0BWlQn1qMOpPm8IKReLgBr/5Lp9dN5K0WthgNu3D04k/Vu2ev" +
            "03zbxtfKhezePwKCAQEA9IhVokdrg08ujX/SV+pF6aXkdzdxGwPBJ6rrGZl0P5/K" +
            "lh31v4SYFG5nd+tNKCTrGuwkryHp7KpEZ2EsJrJAA6P7W1SKqBALwKPKJXgHToON" +
            "1hFOd2/oHJ4T8Zr3kX0WojWLpUg05eEGPrVQ6/90YVPWwl+Z5oYzXlAarewvAGCr" +
            "B3qWSpey3EGSomNEnCe50ZJhb9KOIS6znu83tU/4MqW8aGPE6D4RlXEr2XMrizzy" +
            "ZqR7yMdVEUYqG+px1XyVRjDpoaa1wEViOX8vWBpubGMeOUQEzLeOD+hhoc7FnxWW" +
            "I6GZB3E5O3F4PMBKEexBu+FZzUztv/UlMkzHhBudnwKCAQB1N6o0clSXTY20DX1b" +
            "659kGIdERlNOysYcCKc78KvvR7sSxI0tSY4buTu3AqBBlx1LPIT2YIrVQFCet11z" +
            "Dg3uRmJChBtSNmKDJG/ug6YbosLy3NudJ0XNP6MXLdpyj1yVaYgHMN3XLegg1sXX" +
            "6whEhttQ1kyQOvUjfTfl4zkPETOAjFpheLFB0q6jx4xqoXvPlvsVFavk4lYBjhbP" +
            "VukC8idwCWEr5jQEG4ua08NoB4kp2CXVQEW1BG33/SrxGpfzZPqAxpQCKLiPKLuW" +
            "u0cKMs8O66PMoSZ+VCWRm/lkTZn1hr0jju/g7feWOE9G4/4jibF1lRu34B6vFyiQ" +
            "lF0LAoIBAQCvc2Z+ddA+DD4d33Bizu6L3yFTq8z0lYc30zjP0lk350wrkm4a0bLG" +
            "NvrfQG29zgI9QnL1oApihbv8u6MgHJneZBrvUzPAvzfYSjgcivuON8EkRzst7sWP" +
            "kBMya0TrAgctgTuOSCcv/L/UruuvnA50W1ht/lB6KDyKynzy6ARv4aX8tfsUVJ+U" +
            "YeduqEajtNAZcx/Ix0ftmYrSP7Bik9VEm7N3eDsh6rhvU3VAih6R33r+PIuDdAlg" +
            "CAJ+x3UjBF/Udi3jRVMQWkTXdrFq73rHpYDX3SkXgmUzb4C7oRXq1uhY1qu+uZ8z" +
            "B/+2cxqMBQlJTsLWXeUvKMJeWYkKqwxQ";


    public static String encryptWithPri1 (String str) throws Exception {
        if (str == null) {
            return null;
        }
        byte[] priB = Base64.getDecoder().decode(priKey1);
        return encryptWithPri(str, priB);
    }

    public static String encryptWithPri2 (String str) throws Exception {
        if (str == null) {
            return null;
        }
        byte[] priB = Base64.getDecoder().decode(priKey2);
        return encryptWithPri(str, priB);
    }

    public static String encryptWithPub1 (String str) throws Exception {
        if (str == null) {
            return null;
        }
        byte[] priB = Base64.getDecoder().decode(pubKey1);
        return encryptWithPub(str, priB);
    }

    public static String encryptWithPub2 (String str) throws Exception {
        if (str == null) {
            return null;
        }
        byte[] priB = Base64.getDecoder().decode(pubKey2);
        return encryptWithPub(str, priB);
    }

    /**
     * 私钥加密
     * @param str
     * @param priB
     * @return
     * @throws Exception
     */
    private static String encryptWithPri (String str, byte[] priB) throws Exception {
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(priB));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, rsaPrivateKey);
        String out = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
        return out;
    }

    /**
     * 公钥加密
     * @param str
     * @param pubB
     * @return
     * @throws Exception
     */
    private static String encryptWithPub (String str, byte[] pubB) throws Exception {
        RSAPublicKey rsaPublicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(pubB));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
        String out = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
        return out;
    }

    /**
     * 公钥解密
     * @param str
     * @param pubB
     * @return
     * @throws Exception
     */
    private static String decryptWithPub (String str, byte[] pubB) throws Exception {
        byte[] strB = Base64.getDecoder().decode(str.getBytes("UTF-8"));
        RSAPublicKey rsaPublicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(pubB));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, rsaPublicKey);
        return new String(cipher.doFinal(strB));
    }

    /**
     * 私钥解密
     * @param str 私钥加密的密文
     * @throws Exception
     */
    private static String decryptWithPri (String str, byte[] priB) throws Exception {
        byte[] strB = Base64.getDecoder().decode(str.getBytes("UTF-8"));
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(priB));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
        return new String(cipher.doFinal(strB));
    }

    public static String decryptWithPri1 (String str) throws Exception {
        if (str == null) {
            return null;
        }
        byte[] priB = Base64.getDecoder().decode(priKey1);
        return decryptWithPri(str, priB);
    }

    public static String decryptWithPri2 (String str) throws Exception {
        if (str == null) {
            return null;
        }
        byte[] priB = Base64.getDecoder().decode(priKey2);
        return decryptWithPri(str, priB);
    }

    public static String decryptWithPub1 (String str) throws Exception {
        if (str == null) {
            return null;
        }
        byte[] priB = Base64.getDecoder().decode(pubKey1);
        return decryptWithPub(str, priB);
    }

    public static String decryptWithPub2 (String str) throws Exception {
        if (str == null) {
            return null;
        }
        byte[] priB = Base64.getDecoder().decode(pubKey2);
        return decryptWithPub(str, priB);
    }

    public static void main(String[] args) throws Exception {
        String s = "";
        String en = encryptWithPub2(s);
        System.out.println(en);
        System.out.println(decryptWithPri2(en));
    }

}
