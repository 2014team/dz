<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script type="text/javascript" src="js/jquery.min.js"></script>
  </head>
 <body>
 <script type="text/javascript">
 	$(function(){
 		window.location.href="/admin/login.do";
 	});
 </script>
 <script src="/js/aes.js"></script>
 <script src="/js/md5.js"></script>
 
 <script>
 
  // 将秘钥MD5处理
  var SecretKey = md5("8NONwyJtHesysWpM")
  // 将秘钥编码处理
  var key = CryptoJS.enc.Utf8.parse(SecretKey);
  console.log(key);
  
  // 加密内容
  var plaintText = (new Date()).getTime()+"";//'ABCDEFGH'; // 明文
  
  // AES加密处理
  var encryptedData = CryptoJS.AES.encrypt(plaintText, key, {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.Pkcs7
  });
  
  console.log("加密前："+plaintText);
  console.log("加密后："+encryptedData);
  
  encryptedData = encryptedData.ciphertext.toString();
  var encryptedHexStr = CryptoJS.enc.Hex.parse(encryptedData);
  var encryptedBase64Str = CryptoJS.enc.Base64.stringify(encryptedHexStr);
  var decryptedData = CryptoJS.AES.decrypt(encryptedBase64Str, key, {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.Pkcs7
  });
  var decryptedStr = decryptedData.toString(CryptoJS.enc.Utf8);
  console.log("解密后:"+decryptedStr);
   var pwd = "PCsUFtgog9/qpqmqXsuCRQ==";
   
  //加密服务端返回的数据
  var decryptedData = CryptoJS.AES.decrypt(pwd, key, {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.Pkcs7
  });
  console.log("解密服务端返回的数据:"+decryptedStr);
</script>

</body>
</html>
