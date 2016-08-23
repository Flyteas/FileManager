package pw.flyshit.filemanager;
/*
 * ����: �ļ�������
 * ����: ������ļ�/�ļ��еĴ�����������ɾ������Ŀ¼������Ŀ¼ ����
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
	private String Current_Dir; //��ǰĿ¼
	FileOperator(String Current_Dir_Input) //���캯��
	{
		this.Current_Dir = Current_Dir_Input;
	}
	
	public String GetCurrentDir() //��ȡ��ǰĿ¼
	{
		return this.Current_Dir;
	}
	
	public String DirPathHandle(String Dir_Input) //·�������ж��û������·�������·�����Ǿ���·������������·����ת��Ϊ����·��
	{
		Dir_Input = Dir_Input.replace('/', File.separatorChar); //���û������·���еķָ��� / \ ȫ��ͳһ�ɵ�ǰ����ƽ̨�µķָ���
		Dir_Input = Dir_Input.replace('\\', File.separatorChar); //���û������·���еķָ��� / \ ȫ��ͳһ�ɵ�ǰ����ƽ̨�µķָ���
		if(!(Dir_Input.charAt(0) == '/' || (Dir_Input.length()>1 && Dir_Input.charAt(1) == ':'))) //�ж��û�������Ǿ���·���������·��
		{//�����·��
			Dir_Input = this.Current_Dir + File.separator + Dir_Input; //ת���ɾ���·��
		}
		while(Dir_Input.indexOf(File.separator+File.separator) != -1) //ɾ������ָ����������ܴ��ڵĶ�������ָ���ȫ���滻��һ���ָ���
		{
			Dir_Input = Dir_Input.replace(File.separator+File.separator, File.separator);
		}
		return Dir_Input;
	}
	
 	public boolean EnterDirectory(String Dir_Input) //����Ŀ¼(���õ�ǰĿ¼)
	{
		Dir_Input = this.DirPathHandle(Dir_Input); //���û������·�����д���
		this.File_obj = new File(Dir_Input);
		if(this.File_obj.exists()&&this.File_obj.isDirectory()) //�ж�Ҫ�����Ŀ¼�Ƿ����
		{
			this.Current_Dir = Dir_Input; //���µ�ǰĿ¼
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean CreateFloder(String FloderPath) //�����ļ���,�ɶ༶����
	{
		FloderPath = this.DirPathHandle(FloderPath); //·������ 
		this.File_obj = new File(FloderPath);
		if(!this.File_obj.isDirectory()) //����������򴴽��ļ���
		{
			return this.File_obj.mkdirs();
		}
		return false;
	}
	
	public boolean DeleteFloder(String FloderPath) //ɾ���ļ���
	{
		FloderPath = this.DirPathHandle(FloderPath); //·������ 
		File File_Deleted = new File(FloderPath);
		if(File_Deleted.isDirectory()) //�����һ���ļ���,��ݹ�ɾ������������ļ����ļ���
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
	
	public boolean CopyFloder(String Source_Floder_Path,String Destinate_Floder_Path) //�����ļ���
	{
		Source_Floder_Path = this.DirPathHandle(Source_Floder_Path); //·������ 
		Destinate_Floder_Path = this.DirPathHandle(Destinate_Floder_Path); //·������ 
		File Source_Floder = new File(Source_Floder_Path);
		File Destinate_Floder = new File(Destinate_Floder_Path);
		if(!(Source_Floder.isDirectory() && !Destinate_Floder.isDirectory())) //�ж�Դ�ļ����Ƿ���ڣ�Ŀ���ļ����Ƿ񲻴���
		{
			return false;
		}
		//�˴������ļ���
		Destinate_Floder.mkdirs(); //�½��ļ���
		File[] AllFiles = Source_Floder.listFiles(); //��ȡ�ļ��б�
		for(int i=0;i<AllFiles.length;i++) //�ݹ鿽��
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
	public boolean ListAllFile() //�г���ǰĿ¼�µ������ļ�
	{
		if(this.Current_Dir.isEmpty())
		{
			return false;
		}
		this.File_obj = new File(this.Current_Dir);
		if(!this.File_obj.isDirectory()) //�������Ŀ¼
		{
			return false;
		}
		File[] AllFile = this.File_obj.listFiles();
		for(int i=0;i<AllFile.length;i++) //�г�������Ŀ¼
		{
			if(AllFile[i].isDirectory())
			{
				long ModifyTime = AllFile[i].lastModified(); //��ȡ����޸�ʱ��
				Calendar DirTime = Calendar.getInstance();
				DirTime.setTimeInMillis(ModifyTime);
				System.out.println(AllFile[i].getName() + "		" + DirTime.getTime());
			}
		}
		for(int i=0;i<AllFile.length;i++) //�г������ļ�
		{
			if(AllFile[i].isFile())
			{
				long ModifyTime = AllFile[i].lastModified(); //��ȡ����޸�ʱ��
				Calendar DirTime = Calendar.getInstance();
				DirTime.setTimeInMillis(ModifyTime);
				long FileLengthLong = AllFile[i].length(); //��ȡ�ļ���С,��λByte
				String FileLengthDisplay; //�ļ���С������λ
				FileLengthDisplay = Long.toString(FileLengthLong) + " Byte";
				double FileLength = FileLengthLong;
				if(FileLength>1024) //ʹ��KBΪ��λ
				{
					FileLength = FileLength/1024;
					FileLengthDisplay = String.format("%.2f",FileLength) + " KB"; //������λС��
				}
				if(FileLength>1024) //ʹ��MBΪ��λ
				{
					FileLength = FileLength/1024; 
					FileLengthDisplay = String.format("%.2f",FileLength) + " MB"; //������λС��
				}
				if(FileLength>1024) //ʹ��GBΪ��λ
				{
					FileLength = FileLength/1024;
					FileLengthDisplay = String.format("%.2f",FileLength) + " GB"; //������λС��
				}
				System.out.println(AllFile[i].getName() + "		" + DirTime.getTime() + "		" + FileLengthDisplay);
			}
		}
		return true;
	}
	
	public boolean CopyFile(String Source_File_Path,String Destinate_File_Path) //�����ļ�
	{
		Source_File_Path = this.DirPathHandle(Source_File_Path); //·������
		Destinate_File_Path = this.DirPathHandle(Destinate_File_Path); //·������
		File Source_File = new File(Source_File_Path);
		File Destinate_File = new File(Destinate_File_Path);
		if(!Source_File.isFile() || Destinate_File.isFile()) //�ж��Ƿ���Կ���
		{
			return false;
		}
		try
		{
			FileInputStream InputStream = new FileInputStream(Source_File);
			FileOutputStream OutputStream = new FileOutputStream(Destinate_File);
			byte[] CopyBuffer = new byte[1024]; //������
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
	
	public boolean DeleteFile(String FilePath) //ɾ���ļ�
	{
		FilePath = this.DirPathHandle(FilePath); //·������
		this.File_obj = new File(FilePath);
		if(this.File_obj.isFile())
		{
			return this.File_obj.delete();
		}
		return false;
	}


}
