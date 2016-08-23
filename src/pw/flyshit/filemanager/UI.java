package pw.flyshit.filemanager;
/*
 * ����: �û�������
 * ����: ����������û��Ľ�������������Ӧ����ͷ�����Ӧ�û�����
 * Mail: Flyshit@cqu.edu.cn
 */
import java.io.File;
import java.util.Scanner;
import pw.flyshit.filemanager.FileOperator;
import pw.flyshit.filemanager.ZipOperator;
import pw.flyshit.filemanager.Cryptor;
public class UI 
{
	public static void main(String[] args)
	{
		FileOperator FileOperator_obj; //�ļ�������
		ZipOperator Zip_obj; //ѹ����ѹ��
		Cryptor Cryptor_obj; //���ܽ�����
		Scanner UserEnterScanner; //��ȡ�û�������Ϣ
		File Current_File;
		String Current_Dir; //��¼��ǰ·��
		DisplayUsage();
		Current_File = new File("");
		try
		{
			Current_Dir = Current_File.getCanonicalPath(); //��ȡ����·��
		}
		catch(Exception e)
		{
			Current_Dir = "";
			System.out.println("Get program's path error!");
		}
		FileOperator_obj = new FileOperator(Current_Dir);
		Zip_obj = new ZipOperator();
		Cryptor_obj = new Cryptor();
		while(true)
		{	
			System.out.printf(Current_Dir+":"); //��ʾ��ǰ·��
			UserEnterScanner = new Scanner(System.in);
			String UserFuncSeletor = UserEnterScanner.next();
			switch(UserFuncSeletor) //�����û����������Ӧ����
			{
			case "cd":
				//����Ŀ¼
				if(FileOperator_obj.EnterDirectory(UserEnterScanner.next()))
				{
					Current_Dir = FileOperator_obj.GetCurrentDir();
				}
				else
				{
					System.out.println("No such Path!");
				}
				break;
			case "mkdir":
				//�����ļ���
				if(FileOperator_obj.CreateFloder(UserEnterScanner.next()))
				{
					System.out.println("Created Floder Successful");
				}
				else
				{
					System.out.println("Created Floder error!");
				}
				break;
			case "rmfloder":
				//ɾ���ļ���
				if(FileOperator_obj.DeleteFloder(UserEnterScanner.next()))
				{
					System.out.println("Delete Floder Successful!");
				}
				else
				{
					System.out.println("Delete Floder error!");
				}
				break;
			case "cpfloder":
				//�����ļ���
				if(!FileOperator_obj.CopyFloder(UserEnterScanner.next(), UserEnterScanner.next()))
				{
					System.out.println("Copy Floder error!");
				}
				else
				{
					System.out.println("Copy Floder successful!");
				}
				break;
			case "ls":
				//�г���ǰĿ¼�����ļ��м��ļ�
				if(!FileOperator_obj.ListAllFile())
				{
					System.out.println("List Files and Floders error!");
				}
				break;
			case "cpfile":
				//�����ļ�
				if(!FileOperator_obj.CopyFile(UserEnterScanner.next(), UserEnterScanner.next()))
				{
					System.out.println("Copy File error!");
				}
				else
				{
					System.out.println("Copy File successful!");
				}
				break;
			case "rmfile":
				//ɾ���ļ�
				if(FileOperator_obj.DeleteFile(UserEnterScanner.next()))
				{
					System.out.println("Delete File successful !");
				}
				else
				{
					System.out.println("Delete File error !");
				}
				break;
			case "zip":
				//ѹ���ļ����ļ���
				String Zip_Source_Path = UserEnterScanner.next();
				String Zip_Destinate_Path = UserEnterScanner.next();
				Zip_Source_Path = FileOperator_obj.DirPathHandle(Zip_Source_Path); //·����������û�����������·������ת��Ϊ����·��
				Zip_Destinate_Path = FileOperator_obj.DirPathHandle(Zip_Destinate_Path); //ͬ��
				if(Zip_obj.Compress(Zip_Source_Path, Zip_Destinate_Path))
				{
					System.out.println("Compress Successful!");
				}
				else
				{
					System.out.println("Compress Error!");
				}
				break;
			case "uzip":
				//��ѹ���ļ����ļ���
				String UZip_Source_Path = UserEnterScanner.next();
				String UZip_Destinate_Path = UserEnterScanner.next();
				UZip_Source_Path = FileOperator_obj.DirPathHandle(UZip_Source_Path); //·����������û�����������·������ת��Ϊ����·��
				UZip_Destinate_Path = FileOperator_obj.DirPathHandle(UZip_Destinate_Path); //ͬ��
				if(Zip_obj.Decompress(UZip_Source_Path, UZip_Destinate_Path))
				{
					System.out.println("Decompress Successful!");
				}
				else
				{
					System.out.println("Decompress Error!");
				}
				break;
			case "encrypt":
				//�����ļ�
				String Encrypt_Source_Path = UserEnterScanner.next();
				String Encrypt_Destinate_Path = UserEnterScanner.next();
				String AES_Encrypt_Key = UserEnterScanner.next();
				Encrypt_Source_Path = FileOperator_obj.DirPathHandle(Encrypt_Source_Path); //·����������û�����������·������ת��Ϊ����·��
				Encrypt_Destinate_Path = FileOperator_obj.DirPathHandle(Encrypt_Destinate_Path); //ͬ��
				if(Cryptor_obj.Encrypt(Encrypt_Source_Path, Encrypt_Destinate_Path, AES_Encrypt_Key))
				{
					System.out.println("Encrypt File with AES successful!");
				}
				else
				{
					System.out.println("Encrypt File with AES error!");
				}
				break;
			case "decrypt":
				//�����ļ�
				String Decrypt_Source_Path = UserEnterScanner.next();
				String Decrypt_Destinate_Path = UserEnterScanner.next();
				String AES_Decrypt_Key = UserEnterScanner.next();
				Decrypt_Source_Path = FileOperator_obj.DirPathHandle(Decrypt_Source_Path); //·����������û�����������·������ת��Ϊ����·��
				Decrypt_Destinate_Path = FileOperator_obj.DirPathHandle(Decrypt_Destinate_Path); //ͬ��
				if(Cryptor_obj.Decrypt(Decrypt_Source_Path, Decrypt_Destinate_Path, AES_Decrypt_Key))
				{
					System.out.println("Decrypt File with AES successful!");
				}
				else
				{
					System.out.println("Decrypt File with AES error!");
				}
				break;
			case "help":
				//������Ϣ
				DisplayUsage();
				break;
			case "exit":
				//�˳�����
				UserEnterScanner.close();
				return;
			default:
				System.out.println("Command error!");
			}
		}
	}
	public static void DisplayUsage() //�������ʹ�÷���
	{	
		System.out.println("FileManager v0.1");
		System.out.println("Mail: Flyshit@cqu.edu.cn");
		System.out.println("Usage:");
		System.out.println("cd path :Enter Path"); //�����ļ���
		System.out.println("mkdir floder_name :Create a floder which named floder_name"); //�½��ļ���
		System.out.println("rmfloder floder_name :Delete a floder which named floder_name"); //ɾ���ļ���
		System.out.println("cpfloder /path/sour_floder /path/dest_floder :Copy /path/sour_floder to /path/dest_floder"); //�����ļ���
		System.out.println("ls :Display current path's all files and floders"); //�г���ǰ�ļ����µ������ļ������ļ���
		System.out.println("cpfile /path/sour_file /path/dest_file :Copy /path/sour_file to /path/dest_file"); //�����ļ�
		System.out.println("rmfile file_name :Delete a file which named file_name"); //ɾ���ļ�
		System.out.println("zip /path/sour_file_or_floder /path/dest_file :Compresse /path/file_or_floder and save to /path/dest_file"); //ѹ���ļ����ļ���
		System.out.println("uzip /path/sour_file /path/dest_file_or_floder :Decompresse /path/file and save to /path/dest_file_or_floder"); //��ѹ���ļ����ļ���
		System.out.println("encrypt /path/sour_file /path/dest_file AES_Key :Encrypt /path/sour_file and save to /path/dest_file with AES_Key");  //�����ļ�
		System.out.println("decrypt /path/sour_file /path/dest_file AES_Key :Decrypt /path/sour_file and save to /path/dest_file with AES_Key");  //�����ļ�
		System.out.println("help :Display help infomation");  //��ȡ������Ϣ
		System.out.println("exit :Exit FileManager");	//�˳�����
	}

}
