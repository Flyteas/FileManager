package pw.flyshit.filemanager;
/*
 * 类名: 文件/文件夹 ZIP压缩解压类
 * 功能: 对文件/文件夹进行ZIP压缩和解压
 * Mail: Flyshit@cqu.edu.cn
 */
import java.io.File;
import org.apache.tools.ant.taskdefs.Zip;  //使用ant这个包，避免中文乱码问题
import org.apache.tools.ant.Project;       
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.taskdefs.Expand;
public class ZipOperator 
{
	public boolean Compress(String Source_Path,String Destinate_Path) //压缩文件、文件夹
	{
		File Source_File = new File(Source_Path);
		File Destinate_File = new File(Destinate_Path);
		if(!Source_File.exists() || Destinate_File.exists()) //源文件不存在,或者目标文件已存在
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
		if(!Source_File.exists() || Destinate_File.exists()) //源文件不存在,或者目标文件已存在
		{
			return false;
		}
		Project UZip_Project = new Project();
		Expand UZipExpand = new Expand();
		String Encoding = System.getProperty("file.encoding"); //获取当前系统编码
		UZipExpand.setProject(UZip_Project);
		UZipExpand.setSrc(Source_File);
		UZipExpand.setDest(Destinate_File);
		UZipExpand.setEncoding(Encoding); //设置编码 防止中文乱码
		UZipExpand.execute();
		return true;
	}
}
