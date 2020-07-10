/*
* @Author: hushuli
* @Date:   2020-06-14 15:00:32
* @Last Modified by:   Administrator
* @Last Modified time: 2020-06-14 17:48:44
*/
import java.io.*;
import java.text.DecimalFormat;
public class Count{
	public static void main(String[] args) throws IOException{
		analyze();
	}

	//打印结果
	public static void analyze() throws IOException{
		//统计当前文件夹下所有文件名，形成一个File[]实例
		File f = new File("d:\\tmp");
		File[] ff = f.listFiles();
		double row = 0;	//总行数
		double blank = 0;	//空白行数
		double comment = 0;	//注释行数
		double code = 0;	//代码行数，由 总行数-空白行-注释行 可得
		for(File i:ff){
			if(i.getName().endsWith(".java")){	//判断是否为java文件
				row += count_Rows(i);
				blank += count_Blanks(i);
				comment += count_Comments(i);
			}
		}
		code = row - blank - comment;
		DecimalFormat df = new DecimalFormat("0.00%");

		System.out.println("总行数为" + (int)row);
		System.out.println("空白行数为" + (int)blank +"，占比为" + df.format(blank/row));
		System.out.println("注释行数为" + (int)comment +"，占比为" + df.format(comment/row));
		System.out.println("代码行数为" + (int)code +"，占比为" + df.format(code/row));
	}

	//统计单个文件的总行数rows，返回总行数rows
	public static double count_Rows(File file) throws IOException{
		BufferedReader input = new BufferedReader(new FileReader(file));	//读取文件内容
		double rows = 0;
		while (input.readLine() != null){
			rows++;
		}
		return rows;
	}

	//统计单个文件的空白行数blanks，返回空白行数blanks
	public static double count_Blanks(File file) throws IOException{
		BufferedReader input = new BufferedReader(new FileReader(file));	//读取文件内容
		double blanks = 0;
		String line = null;
		while ((line = input.readLine()) != null){	//判断是否为空白行
			if (line.trim().equals("")){
				blanks++;
			} 
		}
		return blanks;
	}

	//统计单个文件的注释行数comments，返回注释行数comments
	public static double count_Comments(File file) throws IOException{
		/*
		*3种注释分别为
		*1、单行注释:	
		*2、多行注释：	
		*3、行尾多行注释：
		*	第一行为代码，第二行为注释
		*/
		BufferedReader input = new BufferedReader(new FileReader(file));	//读取文件内容
		double comments = 0;
		String line = null;
		while ((line = input.readLine()) != null){
			line = line.trim();
			if(line.startsWith("//")){	//判断是否为单行注释
				comments++;
			}else if(line.startsWith("/*")){	//判断是否为多行注释
				comments++;
				while (!line.endsWith("*/")){
					comments++;
					line = input.readLine().trim();
				}
			}else if (line.contains("/*")){		//判断是否为行尾注释
				line = input.readLine().trim();
				if (line.endsWith("*/")){
					comments++;
				}
			}
		}
		return comments;
	}

}

