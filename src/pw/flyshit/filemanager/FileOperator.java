package pw.flyshit.filemanager;
/*
 * 类名: 文件操作类
 * 功能: 负责对文件/文件夹的创建，拷贝，删除，列目录，进入目录 操作
 * Mail: Flyshit@cqu.edu.cn
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class FileOperator 
{
	private File File_obj;
	private String Current_Dir; //当前目录
	FileOperator(String Current_Dir_Input) //构造函数
	{
		this.Current_Dir = Current_Dir_Input;
	}
	
	public String GetCurrentDir() //获取当前目录
	{
		return this.Current_Dir;
	}
	
	public String DirPathHandle(String Dir_Input) //路径处理，判断用户输入的路径是相对路径还是绝对路径，如果是相对路径则转换为绝对路径
	{
		Dir_Input = Dir_Input.replace('/', File.separatorChar); //将用户输入的路径中的分隔符 / \ 全部统一成当前运行平台下的分隔符
		Dir_Input = Dir_Input.replace('\\', File.separatorChar); //将用户输入的路径中的分隔符 / \ 全部统一成当前运行平台下的分隔符
		if(!(Dir_Input.charAt(0) == '/' || (Dir_Input.length()>1 && Dir_Input.charAt(1) == ':'))) //判断用户输入的是绝对路径还是相对路径
		{//是相对路径
			Dir_Input = this.Current_Dir + File.separator + Dir_Input; //转换成绝对路径
		}
		while(Dir_Input.indexOf(File.separator+File.separator) != -1) //删除多余分隔符，将可能存在的多个连续分隔符全部替换成一个分隔符
		{
			Dir_Input = Dir_Input.replace(File.separator+File.separator, File.separator);
		}
		return Dir_Input;
	}
	
 	public boolean EnterDirectory(String Dir_Input) //进入目录(设置当前目录)
	{
		Dir_Input = this.DirPathHandle(Dir_Input); //对用户输入的路径进行处理
		this.File_obj = new File(Dir_Input);
		if(this.File_obj.exists()&&this.File_obj.isDirectory()) //判断要进入的目录是否存在
		{
			this.Current_Dir = Dir_Input; //更新当前目录
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean CreateFloder(String FloderPath) //创建文件夹,可多级创建
	{
		FloderPath = this.DirPathHandle(FloderPath); //路径处理 
		this.File_obj = new File(FloderPath);
		if(!this.File_obj.isDirectory()) //如果不存在则创建文件夹
		{
			return this.File_obj.mkdirs();
		}
		return false;
	}
	
	public boolean DeleteFloder(String FloderPath) //删除文件夹
	{
		FloderPath = this.DirPathHandle(FloderPath); //路径处理 
		File File_Deleted = new File(FloderPath);
		if(File_Deleted.isDirectory()) //如果是一个文件夹,则递归删除里面的所有文件及文件夹
		{
			String[] All_Files = File_Deleted.list();
			for(int i=0;i<All_Files.length;i++)
			{
				if(!DeleteFloder(FloderPath + File.separator + All_Files[i]))
				{
					return false;
				}
			}
		}
		return File_Deleted.delete();
	}
	
	public boolean CopyFloder(String Source_Floder_Path,String Destinate_Floder_Path) //拷贝文件夹
	{
		Source_Floder_Path = this.DirPathHandle(Source_Floder_Path); //路径处理 
		Destinate_Floder_Path = this.DirPathHandle(Destinate_Floder_Path); //路径处理 
		File Source_Floder = new File(Source_Floder_Path);
		File Destinate_Floder = new File(Destinate_Floder_Path);
		if(!(Source_Floder.isDirectory() && !Destinate_Floder.isDirectory())) //判断源文件夹是否存在，目标文件夹是否不存在
		{
			return false;
		}
		//此处复制文件夹
		Destinate_Floder.mkdirs(); //新建文件夹
		File[] AllFiles = Source_Floder.listFiles(); //获取文件列表
		for(int i=0;i<AllFiles.length;i++) //递归拷贝
		{
			if(AllFiles[i].isFile())
			{
				this.CopyFile(AllFiles[i].getAbsolutePath(), Destinate_Floder_Path + File.separator + AllFiles[i].getName());
			}
			if(AllFiles[i].isDirectory())
			{
				CopyFloder(AllFiles[i].getAbsolutePath(),Destinate_Floder_Path + File.separator + AllFiles[i].getName());
			}
		}
		return true;
	}
	public boolean ListAllFile() //列出当前目录下的所有文件
	{
		if(this.Current_Dir.isEmpty())
		{
			return false;
		}
		this.File_obj = new File(this.Current_Dir);
		if(!this.File_obj.isDirectory()) //如果不是目录
		{
			return false;
		}
		File[] AllFile = this.File_obj.listFiles();
		for(int i=0;i<AllFile.length;i++) //列出所有子目录
		{
			if(AllFile[i].isDirectory())
			{
				long ModifyTime = AllFile[i].lastModified(); //获取最后修改时间
				Calendar DirTime = Calendar.getInstance();
				DirTime.setTimeInMillis(ModifyTime);
				System.out.println(AllFile[i].getName() + "		" + DirTime.getTime());
			}
		}
		for(int i=0;i<AllFile.length;i++) //列出所有文件
		{
			if(AllFile[i].isFile())
			{
				long ModifyTime = AllFile[i].lastModified(); //获取最后修改时间
				Calendar DirTime = Calendar.getInstance();
				DirTime.setTimeInMillis(ModifyTime);
				long FileLengthLong = AllFile[i].length(); //获取文件大小,单位Byte
				String FileLengthDisplay; //文件大小，带单位
				FileLengthDisplay = Long.toString(FileLengthLong) + " Byte";
				double FileLength = FileLengthLong;
				if(FileLength>1024) //使用KB为单位
				{
					FileLength = FileLength/1024;
					FileLengthDisplay = String.format("%.2f",FileLength) + " KB"; //保留两位小数
				}
				if(FileLength>1024) //使用MB为单位
				{
					FileLength = FileLength/1024; 
					FileLengthDisplay = String.format("%.2f",FileLength) + " MB"; //保留两位小数
				}
				if(FileLength>1024) //使用GB为单位
				{
					FileLength = FileLength/1024;
					FileLengthDisplay = String.format("%.2f",FileLength) + " GB"; //保留两位小数
				}
				System.out.println(AllFile[i].getName() + "		" + DirTime.getTime() + "		" + FileLengthDisplay);
			}
		}
		return true;
	}
	
	public boolean CopyFile(String Source_File_Path,String Destinate_File_Path) //拷贝文件
	{
		Source_File_Path = this.DirPathHandle(Source_File_Path); //路径处理
		Destinate_File_Path = this.DirPathHandle(Destinate_File_Path); //路径处理
		File Source_File = new File(Source_File_Path);
		File Destinate_File = new File(Destinate_File_Path);
		if(!Source_File.isFile() || Destinate_File.isFile()) //判断是否可以拷贝
		{
			return false;
		}
		try
		{
			FileInputStream InputStream = new FileInputStream(Source_File);
			FileOutputStream OutputStream = new FileOutputStream(Destinate_File);
			byte[] CopyBuffer = new byte[1024]; //缓冲区
			int CopyRecord;
			while((CopyRecord = InputStream.read(CopyBuffer)) !=-1 )
			{
				OutputStream.write(CopyBuffer,0,CopyRecord);
			}
			InputStream.close();
			OutputStream.close();
			return true;
		}
		catch (IOException e)
		{
			return false;
		}
	}
	
	public boolean DeleteFile(String FilePath) //删除文件
	{
		FilePath = this.DirPathHandle(FilePath); //路径处理
		this.File_obj = new File(FilePath);
		if(this.File_obj.isFile())
		{
			return this.File_obj.delete();
		}
		return false;
	}


}
