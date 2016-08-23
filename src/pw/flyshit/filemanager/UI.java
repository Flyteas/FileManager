package pw.flyshit.filemanager;
/*
 * 类名: 用户交互类
 * 功能: 负责程序与用户的交互，并调用相应的类和方法响应用户操作
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
		FileOperator FileOperator_obj; //文件操作类
		ZipOperator Zip_obj; //压缩解压类
		Cryptor Cryptor_obj; //加密解密类
		Scanner UserEnterScanner; //获取用户输入信息
		File Current_File;
		String Current_Dir; //记录当前路径
		DisplayUsage();
		Current_File = new File("");
		try
		{
			Current_Dir = Current_File.getCanonicalPath(); //获取程序路径
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
			System.out.printf(Current_Dir+":"); //显示当前路径
			UserEnterScanner = new Scanner(System.in);
			String UserFuncSeletor = UserEnterScanner.next();
			switch(UserFuncSeletor) //根据用户输入调用相应方法
			{
			case "cd":
				//进入目录
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
				//创建文件夹
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
				//删除文件夹
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
				//拷贝文件夹
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
				//列出当前目录所有文件夹及文件
				if(!FileOperator_obj.ListAllFile())
				{
					System.out.println("List Files and Floders error!");
				}
				break;
			case "cpfile":
				//拷贝文件
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
				//删除文件
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
				//压缩文件或文件夹
				String Zip_Source_Path = UserEnterScanner.next();
				String Zip_Destinate_Path = UserEnterScanner.next();
				Zip_Source_Path = FileOperator_obj.DirPathHandle(Zip_Source_Path); //路径处理，如果用户输入的是相对路径，则转换为绝对路径
				Zip_Destinate_Path = FileOperator_obj.DirPathHandle(Zip_Destinate_Path); //同上
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
				//解压缩文件或文件夹
				String UZip_Source_Path = UserEnterScanner.next();
				String UZip_Destinate_Path = UserEnterScanner.next();
				UZip_Source_Path = FileOperator_obj.DirPathHandle(UZip_Source_Path); //路径处理，如果用户输入的是相对路径，则转换为绝对路径
				UZip_Destinate_Path = FileOperator_obj.DirPathHandle(UZip_Destinate_Path); //同上
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
				//加密文件
				String Encrypt_Source_Path = UserEnterScanner.next();
				String Encrypt_Destinate_Path = UserEnterScanner.next();
				String AES_Encrypt_Key = UserEnterScanner.next();
				Encrypt_Source_Path = FileOperator_obj.DirPathHandle(Encrypt_Source_Path); //路径处理，如果用户输入的是相对路径，则转换为绝对路径
				Encrypt_Destinate_Path = FileOperator_obj.DirPathHandle(Encrypt_Destinate_Path); //同上
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
				//解密文件
				String Decrypt_Source_Path = UserEnterScanner.next();
				String Decrypt_Destinate_Path = UserEnterScanner.next();
				String AES_Decrypt_Key = UserEnterScanner.next();
				Decrypt_Source_Path = FileOperator_obj.DirPathHandle(Decrypt_Source_Path); //路径处理，如果用户输入的是相对路径，则转换为绝对路径
				Decrypt_Destinate_Path = FileOperator_obj.DirPathHandle(Decrypt_Destinate_Path); //同上
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
				//帮助信息
				DisplayUsage();
				break;
			case "exit":
				//退出程序
				UserEnterScanner.close();
				return;
			default:
				System.out.println("Command error!");
			}
		}
	}
	public static void DisplayUsage() //输出程序使用方法
	{	
		System.out.println("FileManager v0.1");
		System.out.println("Mail: Flyshit@cqu.edu.cn");
		System.out.println("Usage:");
		System.out.println("cd path :Enter Path"); //进入文件夹
		System.out.println("mkdir floder_name :Create a floder which named floder_name"); //新建文件夹
		System.out.println("rmfloder floder_name :Delete a floder which named floder_name"); //删除文件夹
		System.out.println("cpfloder /path/sour_floder /path/dest_floder :Copy /path/sour_floder to /path/dest_floder"); //拷贝文件夹
		System.out.println("ls :Display current path's all files and floders"); //列出当前文件夹下的所有文件和子文件夹
		System.out.println("cpfile /path/sour_file /path/dest_file :Copy /path/sour_file to /path/dest_file"); //拷贝文件
		System.out.println("rmfile file_name :Delete a file which named file_name"); //删除文件
		System.out.println("zip /path/sour_file_or_floder /path/dest_file :Compresse /path/file_or_floder and save to /path/dest_file"); //压缩文件或文件夹
		System.out.println("uzip /path/sour_file /path/dest_file_or_floder :Decompresse /path/file and save to /path/dest_file_or_floder"); //解压缩文件或文件夹
		System.out.println("encrypt /path/sour_file /path/dest_file AES_Key :Encrypt /path/sour_file and save to /path/dest_file with AES_Key");  //加密文件
		System.out.println("decrypt /path/sour_file /path/dest_file AES_Key :Decrypt /path/sour_file and save to /path/dest_file with AES_Key");  //解密文件
		System.out.println("help :Display help infomation");  //获取帮助信息
		System.out.println("exit :Exit FileManager");	//退出程序
	}

}
