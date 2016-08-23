package pw.flyshit.filemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

/*
 * 类名: 文件加密解密类
 * 功能: 采用AES算法加密解密文件
 * Mail: Flyshit@cqu.edu.cn
 */
public class Cryptor 
{
	private Cipher AESInitCipher(String EncryptKey,int Encrypt_Mode) //初始化密码器
	{
		Cipher AESCipher = null;
		KeyGenerator KeyGen = null;
		try
		{
			SecureRandom AESSecureRandom = new SecureRandom(EncryptKey.getBytes());
			KeyGen = KeyGenerator.getInstance("AES");
			KeyGen.init(128,AESSecureRandom);
			SecretKey AESSecretKey = KeyGen.generateKey();
			byte[] KeyFormat = AESSecretKey.getEncoded();
			SecretKeySpec AESSecretKeySpec = new SecretKeySpec(KeyFormat,"AES");
			AESCipher = Cipher.getInstance("AES");
			AESCipher.init(Encrypt_Mode, AESSecretKeySpec);// 初始化完毕
		}
		catch(Exception e)
		{
			System.out.println("Initialized AES Key error!");
		}
		return AESCipher;
	}
	
	public boolean Encrypt(String Source_Path,String Destinate_Path,String AESKey) //加密文件
	{
		File Source_File = new File(Source_Path); //源文件
		File Destinate_File = new File(Destinate_Path); //加密文件
		FileInputStream EncryptInputStream = null;
		FileOutputStream EncryptOutputStream = null;
		if(!Source_File.isFile() || Destinate_File.isFile())
		{
			return false;
		}
		
		try
		{
			EncryptInputStream = new FileInputStream(Source_File);
			EncryptOutputStream = new FileOutputStream(Destinate_File);
			Cipher AESCipher = this.AESInitCipher(AESKey, Cipher.ENCRYPT_MODE); //初始化密码
			CipherInputStream EncryptCipherInputStream = new CipherInputStream(EncryptInputStream,AESCipher); //加密流
			byte[] WriteBuffer = new byte[2048]; //缓冲区
			int ReadCount= 0;
			while( (ReadCount = EncryptCipherInputStream.read(WriteBuffer)) != -1 )
			{
				EncryptOutputStream.write(WriteBuffer, 0, ReadCount); //写数据
				EncryptOutputStream.flush(); //刷新
			}
			EncryptCipherInputStream.close(); //关闭加密流
			EncryptInputStream.close(); //关闭输入流
			EncryptOutputStream.close(); //关闭输出流
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage()); //显示错误信息
			return false;
		}
		return true;	
	}
	
	public boolean Decrypt(String Source_Path,String Destinate_Path,String AESKey) //解密文件
	{
		File Source_File = new File(Source_Path); //源文件
		File Destinate_File = new File(Destinate_Path); //解密文件
		FileInputStream DecryptInputStream = null;
		FileOutputStream DecryptOutputStream = null;
		if(!Source_File.isFile() || Destinate_File.isFile())
		{
			return false;
		}
		
		try
		{
			DecryptInputStream = new FileInputStream(Source_File);
			DecryptOutputStream = new FileOutputStream(Destinate_File);
			Cipher AESCipher = this.AESInitCipher(AESKey, Cipher.DECRYPT_MODE); //初始化密码
			CipherOutputStream DecryptCipherOutputStream = new CipherOutputStream(DecryptOutputStream,AESCipher); //解密流
			byte[] WriteBuffer = new byte[2048]; //缓冲区
			int ReadCount= 0;
			while( (ReadCount = DecryptInputStream.read(WriteBuffer)) != -1 )
			{
				DecryptCipherOutputStream.write(WriteBuffer, 0, ReadCount); //写文件
				DecryptCipherOutputStream.flush();
			}
			DecryptCipherOutputStream.close(); //关闭解密流
			DecryptInputStream.close(); //关闭输入流
			DecryptOutputStream.close(); //关闭输出流
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage()); //显示错误信息
			return false;
		}
		return true;	
	}

}
