package pw.flyshit.filemanager;
/*
 * ����: �ļ�/�ļ��� ZIPѹ����ѹ��
 * ����: ���ļ�/�ļ��н���ZIPѹ���ͽ�ѹ
 * Mail: Flyshit@cqu.edu.cn
 */
import java.io.File;
import org.apache.tools.ant.taskdefs.Zip;  //ʹ��ant�����������������������
import org.apache.tools.ant.Project;       
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.taskdefs.Expand;
public class ZipOperator 
{
	public boolean Compress(String Source_Path,String Destinate_Path) //ѹ���ļ����ļ���
	{
		File Source_File = new File(Source_Path);
		File Destinate_File = new File(Destinate_Path);
		if(!Source_File.exists() || Destinate_File.exists()) //Դ�ļ�������,����Ŀ���ļ��Ѵ���
		{
			return false;
		}
		Project Zip_Project = new Project();
		Zip ZipCompress = new Zip();
		FileSet ZipFileSet = new FileSet();
		ZipCompress.setProject(Zip_Project);
		ZipCompress.setDestFile(Destinate_File);
		ZipFileSet.setProject(Zip_Project);
		if(Source_File.isFile())
		{
			ZipFileSet.setFile(Source_File);
		}
		else
		{
			ZipFileSet.setDir(Source_File);
		}
		ZipCompress.addFileset(ZipFileSet);
		ZipCompress.execute();
		return true;
	}
	
	public boolean Decompress(String Source_Path,String Destinate_Path)
	{
		File Source_File = new File(Source_Path);
		File Destinate_File = new File(Destinate_Path);
		if(!Source_File.exists() || Destinate_File.exists()) //Դ�ļ�������,����Ŀ���ļ��Ѵ���
		{
			return false;
		}
		Project UZip_Project = new Project();
		Expand UZipExpand = new Expand();
		String Encoding = System.getProperty("file.encoding"); //��ȡ��ǰϵͳ����
		UZipExpand.setProject(UZip_Project);
		UZipExpand.setSrc(Source_File);
		UZipExpand.setDest(Destinate_File);
		UZipExpand.setEncoding(Encoding); //���ñ��� ��ֹ��������
		UZipExpand.execute();
		return true;
	}
}
