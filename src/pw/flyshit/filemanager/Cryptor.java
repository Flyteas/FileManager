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
 * ����: �ļ����ܽ�����
 * ����: ����AES�㷨���ܽ����ļ�
 * Mail: Flyshit@cqu.edu.cn
 */
public class Cryptor 
{
	private Cipher AESInitCipher(String EncryptKey,int Encrypt_Mode) //��ʼ��������
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
			AESCipher.init(Encrypt_Mode, AESSecretKeySpec);// ��ʼ�����
		}
		catch(Exception e)
		{
			System.out.println("Initialized AES Key error!");
		}
		return AESCipher;
	}
	
	public boolean Encrypt(String Source_Path,String Destinate_Path,String AESKey) //�����ļ�
	{
		File Source_File = new File(Source_Path); //Դ�ļ�
		File Destinate_File = new File(Destinate_Path); //�����ļ�
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
			Cipher AESCipher = this.AESInitCipher(AESKey, Cipher.ENCRYPT_MODE); //��ʼ������
			CipherInputStream EncryptCipherInputStream = new CipherInputStream(EncryptInputStream,AESCipher); //������
			byte[] WriteBuffer = new byte[2048]; //������
			int ReadCount= 0;
			while( (ReadCount = EncryptCipherInputStream.read(WriteBuffer)) != -1 )
			{
				EncryptOutputStream.write(WriteBuffer, 0, ReadCount); //д����
				EncryptOutputStream.flush(); //ˢ��
			}
			EncryptCipherInputStream.close(); //�رռ�����
			EncryptInputStream.close(); //�ر�������
			EncryptOutputStream.close(); //�ر������
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage()); //��ʾ������Ϣ
			return false;
		}
		return true;	
	}
	
	public boolean Decrypt(String Source_Path,String Destinate_Path,String AESKey) //�����ļ�
	{
		File Source_File = new File(Source_Path); //Դ�ļ�
		File Destinate_File = new File(Destinate_Path); //�����ļ�
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
			Cipher AESCipher = this.AESInitCipher(AESKey, Cipher.DECRYPT_MODE); //��ʼ������
			CipherOutputStream DecryptCipherOutputStream = new CipherOutputStream(DecryptOutputStream,AESCipher); //������
			byte[] WriteBuffer = new byte[2048]; //������
			int ReadCount= 0;
			while( (ReadCount = DecryptInputStream.read(WriteBuffer)) != -1 )
			{
				DecryptCipherOutputStream.write(WriteBuffer, 0, ReadCount); //д�ļ�
				DecryptCipherOutputStream.flush();
			}
			DecryptCipherOutputStream.close(); //�رս�����
			DecryptInputStream.close(); //�ر�������
			DecryptOutputStream.close(); //�ر������
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage()); //��ʾ������Ϣ
			return false;
		}
		return true;	
	}

}
