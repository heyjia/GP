var publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCi5CpeoSjOduO7BNSlCRErGJwNue2PcC7C/axn+WYufObkRCiyeW7SXFo56He2yLE0o+CLSSAuTwbam2yPx92p3Jt7pUzNvaGqBSTJSmzJzxtDsBvb9Z+Bw5t38M3DSD2FujThRVclU80OHQJvVaanr1e7ebl9lCdcvMpsFrFXLQIDAQAB";
function encryptPwd(password){
    console.log("publicKey："+ publicKey);
    console.log("password:" + password);
    var encrypt = new JSEncrypt();
    encrypt.setPublicKey(publicKey);
    password = encrypt.encrypt(password);
    console.log("加密后："+ password);
    return password;
}