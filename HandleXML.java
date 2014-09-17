import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class HandleXML {
	/**
	 * 采用DOM4J方式
	 * @author Administrator
	 *
	 */
	public class DOM4JForXml {
		
		public Document getDocument(String fileName){ 
			File inputXml = new File(fileName);
			SAXReader sr = new SAXReader();
			Document doc = null;
			try {      
			    doc = sr.read(inputXml);  
			}catch (Exception e){
				e.printStackTrace();
			}
			return doc;
	
		}
		
		/**
		 * 将文件保存到硬盘
		 * @param doc
		 * @param fileName
		 */
		  public void writeToFile(Document doc,String fileName){  
		  try{
			  Writer writer = new FileWriter(fileName); 
			  OutputFormat format = OutputFormat.createPrettyPrint();  
			  format.setEncoding("UTF-8");   
			  XMLWriter xmlWriter = new XMLWriter(writer, format); 
			  xmlWriter.write(doc);    
			  xmlWriter.close();     
			  System.out.println(" 文件已经存完！");  
		  
		  }catch (Exception e) {  
		      
			    e.printStackTrace();  
			  
		   }  
		  
		  }
		  /**
		   * 遍历XML文件输出节点值
		   * @param fileName
		   */
		  public void readXml(String fileName){   
			  Document doc = getDocument(fileName); 
			  List nameList = doc.selectNodes("/students/student/name");  
			  List sexList = doc.selectNodes("/students/student/sex"); 
			  List ageList = doc.selectNodes("/students/student/age"); 
			  List javaScoreList = doc.selectNodes("/students/student/scores/java"); 
			  List cScoreList = doc.selectNodes("/students/student/scores/c");
			  List cppScoreList = doc.selectNodes("/students/student/scores/cpp");
			  for(int i=0;i<nameList.size();i++){   
				  StringBuilder sb = new StringBuilder();   
				  sb.append("name="+((Element)nameList.get(i)).getTextTrim());
				  sb.append(",sex="+((Element)sexList.get(i)).getTextTrim());
				  sb.append(",age="+((Element)ageList.get(i)).getTextTrim());
			      sb.append(",java="+((Element)javaScoreList.get(i)).getTextTrim());
			      sb.append(",c="+((Element)cScoreList.get(i)).getTextTrim());
			      sb.append(",cpp="+((Element)cppScoreList.get(i)).getTextTrim());
			      System.out.println(sb.toString());  
	
			  }
		  }
		  /**
		   * 根据姓名修改一个学生的信息
		   * @param name
		   * @param newValue
		   * @param fileName
		   */
		  public void updateXml(String name,Map newValue,String fileName){
			  Document doc = getDocument(fileName);   
			  List nameList = doc.selectNodes("/students/student/name");  
			  Iterator iterator = nameList.iterator();  
				  while(iterator.hasNext()){   
					    Element element = (Element)iterator.next();
					    if(name!=null && name.equals(element.getText())){  
					    	Element pe = element.getParent();
					    	List childList = pe.elements(); 
					    	for(int i=0;i<childList.size();i++){  
					    		Iterator valueSet = newValue.entrySet().iterator();  
					    		while(valueSet.hasNext()){   
					    			Map.Entry entry = (Map.Entry)valueSet.next();   
					    			String nodeName = ((Element)childList.get(i)).getName();  
					    			String key = entry.getKey().toString();  
					    			if(key!=null && key.equals(nodeName)){
					    				((Element)childList.get(i)).setText((String)entry.getValue());
					    			}
					    		}
					    	}
					    }
				   }
				  writeToFile(doc, fileName);
				  System.out.println("update");
				  
				  }
		  /**
		   * 增加
		   * 创建一个新学生
		   * @param name
		   * @param age
		   * @param sex
		   * @param address
		   */
		  public void addNewNode(String name, String sex,String age, String java, String c,String cpp,String fileName){ 
			  try{   
				    //得到doc文档 
				    Document document = getDocument(fileName); 
				    //得到根节点   
				    Element students = document.getRootElement(); 
				    Element studentElement = students.addElement("student");
				    Element nameElement = studentElement.addElement("name");
				    nameElement.setText(name);
				    Element sexElement = studentElement.addElement("sex");
				    sexElement.setText(sex);
				    Element ageElement = studentElement.addElement("age");
				    ageElement.setText(age);
				    Element scoresElement = studentElement.addElement("scores");
				    Element javaElement = scoresElement.addElement("java");
				    javaElement.setText(java);
				    Element cElement = scoresElement.addElement("c");
				    cElement.setText(c);
				    Element cppElement = scoresElement.addElement("cpp");
				    cppElement.setText(cpp);
				    writeToFile(document, fileName);
				    System.out.println("add");
			  }catch(Exception e){
				  
			  }


		  }
		  
		  /**
		   * 
		   * @param name
		   * @param newValue
		   * @param fileName
		   */
		  public void deleteXml(String name,String fileName){
			  Document doc = getDocument(fileName);   
			  List nameList = doc.selectNodes("/students/student/name");  
			  Iterator iterator = nameList.iterator(); 
			  while(iterator.hasNext()){   
				    Element element = (Element)iterator.next();
				    if(name!=null && name.equals(element.getText())){  
				    	Element student = element.getParent();
				    	Element students = student.getParent();
				    	students.remove(student);
				    	}
				    }
			  writeToFile(doc, fileName);
			  System.out.println("delete");
		  }
		  
		  /**
		   * 具体查找
		   * @param name
		   * @param newValue
		   * @param fileName
		   */
		  public void findXml(String stringName,String stringValue,String fileName){
//			  int N=0;
//			  StringBuilder[] nodeName = null;//存放查找到的节点
			  Document doc = getDocument(fileName);   
			  List nameList = doc.selectNodes("/students/student/name"); 
			  List sexList = doc.selectNodes("/students/student/sex"); 
			  List ageList = doc.selectNodes("/students/student/age"); 
			  List javaList = doc.selectNodes("/students/student/scores/java"); 
			  List cList = doc.selectNodes("/students/student/scores/c"); 
			  List cppList = doc.selectNodes("/students/student/scores/cpp"); 
			  if(stringName!=null&&stringName.equals("name"))
			  {
				  for(int i=0;i<nameList.size();i++){ 
					  if(((Element)nameList.get(i)).getTextTrim().equals(stringValue))
					  {
						  StringBuilder sb = new StringBuilder();   
						  sb.append("name="+((Element)nameList.get(i)).getTextTrim());
						  sb.append(",sex="+((Element)sexList.get(i)).getTextTrim());
						  sb.append(",age="+((Element)ageList.get(i)).getTextTrim());
					      sb.append(",java="+((Element)javaList.get(i)).getTextTrim());
					      sb.append(",c="+((Element)cList.get(i)).getTextTrim());
					      sb.append(",cpp="+((Element)cppList.get(i)).getTextTrim());
					      System.out.println(sb.toString()); 
				      }		        
		          }
				  System.out.println("findByName");
			  }
			  
			  if(stringName!=null&&stringName.equals("sex"))
			  {
				  for(int i=0;i<sexList.size();i++){ 
					  if(((Element)sexList.get(i)).getTextTrim().equals(stringValue))
					  {
						  StringBuilder sb = new StringBuilder();   
						  sb.append("name="+((Element)nameList.get(i)).getTextTrim());
						  sb.append(",sex="+((Element)sexList.get(i)).getTextTrim());
						  sb.append(",age="+((Element)ageList.get(i)).getTextTrim());
					      sb.append(",java="+((Element)javaList.get(i)).getTextTrim());
					      sb.append(",c="+((Element)cList.get(i)).getTextTrim());
					      sb.append(",cpp="+((Element)cppList.get(i)).getTextTrim());
					      System.out.println(sb.toString()); 
				      }		        
		          }
				  System.out.println("findBysex");
			  }
			  
			  if(stringName!=null&&stringName.equals("age"))
			  {
				  for(int i=0;i<ageList.size();i++){ 
					  if(((Element)ageList.get(i)).getTextTrim().equals(stringValue))
					  {
						  StringBuilder sb = new StringBuilder();   
						  sb.append("name="+((Element)nameList.get(i)).getTextTrim());
						  sb.append(",sex="+((Element)sexList.get(i)).getTextTrim());
						  sb.append(",age="+((Element)ageList.get(i)).getTextTrim());
					      sb.append(",java="+((Element)javaList.get(i)).getTextTrim());
					      sb.append(",c="+((Element)cList.get(i)).getTextTrim());
					      sb.append(",cpp="+((Element)cppList.get(i)).getTextTrim());
					      System.out.println(sb.toString()); 
				      }		        
		          }
				  System.out.println("findByage");
			  }
			  
			  if(stringName!=null&&stringName.equals("java"))
			  {
				  for(int i=0;i<javaList.size();i++){ 
					  if(((Element)javaList.get(i)).getTextTrim().equals(stringValue))
					  {
						  StringBuilder sb = new StringBuilder();   
						  sb.append("name="+((Element)nameList.get(i)).getTextTrim());
						  sb.append(",sex="+((Element)sexList.get(i)).getTextTrim());
						  sb.append(",age="+((Element)ageList.get(i)).getTextTrim());
					      sb.append(",java="+((Element)javaList.get(i)).getTextTrim());
					      sb.append(",c="+((Element)cList.get(i)).getTextTrim());
					      sb.append(",cpp="+((Element)cppList.get(i)).getTextTrim());
					      System.out.println(sb.toString()); 
				      }		        
		          }
				  System.out.println("findByjava");
			  }
			  
			  if(stringName!=null&&stringName.equals("c"))
			  {
				  for(int i=0;i<cList.size();i++){ 
					  if(((Element)cList.get(i)).getTextTrim().equals(stringValue))
					  {
						  StringBuilder sb = new StringBuilder();   
						  sb.append("name="+((Element)nameList.get(i)).getTextTrim());
						  sb.append(",sex="+((Element)sexList.get(i)).getTextTrim());
						  sb.append(",age="+((Element)ageList.get(i)).getTextTrim());
					      sb.append(",java="+((Element)javaList.get(i)).getTextTrim());
					      sb.append(",c="+((Element)cList.get(i)).getTextTrim());
					      sb.append(",cpp="+((Element)cppList.get(i)).getTextTrim());
					      System.out.println(sb.toString()); 
				      }		        
		          }
				  System.out.println("findByc");
			  }
			  
			  if(stringName!=null&&stringName.equals("c"))
			  {
				  for(int i=0;i<cppList.size();i++){ 
					  if(((Element)cppList.get(i)).getTextTrim().equals(stringValue))
					  {
						  StringBuilder sb = new StringBuilder();   
						  sb.append("name="+((Element)nameList.get(i)).getTextTrim());
						  sb.append(",sex="+((Element)sexList.get(i)).getTextTrim());
						  sb.append(",age="+((Element)ageList.get(i)).getTextTrim());
					      sb.append(",java="+((Element)javaList.get(i)).getTextTrim());
					      sb.append(",c="+((Element)cList.get(i)).getTextTrim());
					      sb.append(",cpp="+((Element)cppList.get(i)).getTextTrim());
					      System.out.println(sb.toString()); 
				      }		        
		          }
				  System.out.println("findBycpp");
			  }  
          }
		  /**
		   * 查找某一类，并排序
		   * @param stringName
		   * @param fileName
		   */
		  public void findXml(String stringName,String fileName){
			  
			  Document doc = getDocument(fileName);   
			  List nameList = doc.selectNodes("/students/student/name"); 
			  List sexList = doc.selectNodes("/students/student/sex"); 
			  List ageList = doc.selectNodes("/students/student/age"); 
			  List javaList = doc.selectNodes("/students/student/scores/java"); 
			  List cList = doc.selectNodes("/students/student/scores/c"); 
			  List cppList = doc.selectNodes("/students/student/scores/cpp"); 
			  if(stringName!=null&&stringName.equals("name"))
			  {
				  System.out.println("find ordered name");
				  String[] arrayName = new String[nameList.size()];
				  for(int i=0;i<nameList.size();i++){
					  arrayName[i] = ((Element)nameList.get(i)).getTextTrim();	  
				  }
				  Arrays.sort(arrayName);
				  for(int j=0;j<arrayName.length;j++){
					  System.out.println(arrayName[j]);	  
				  }
			  }
			  
			  if(stringName!=null&&stringName.equals("age"))
			  {
				  System.out.println("find ordered age");
				  String[] arrayage = new String[ageList.size()];
				  for(int i=0;i<ageList.size();i++){
					  arrayage[i] = ((Element)ageList.get(i)).getTextTrim();	  
				  }
				  Arrays.sort(arrayage);
				  for(int j=0;j<arrayage.length;j++){
					  System.out.println(arrayage[j]);	  
				  }
			  }
			  if(stringName!=null&&stringName.equals("java"))
			  {
				  System.out.println("find ordered java");
				  String[] arrayjava = new String[javaList.size()];
				  for(int i=0;i<javaList.size();i++){
					  arrayjava[i] = ((Element)javaList.get(i)).getTextTrim();	  
				  }
				  Arrays.sort(arrayjava);
				  for(int j=0;j<arrayjava.length;j++){
					  System.out.println(arrayjava[j]);	  
				  }
			  }
			  if(stringName!=null&&stringName.equals("c"))
			  {
				  System.out.println("find ordered c");
				  String[] arrayc = new String[cList.size()];
				  for(int i=0;i<cList.size();i++){
					  arrayc[i] = ((Element)cList.get(i)).getTextTrim();	  
				  }
				  Arrays.sort(arrayc);
				  for(int j=0;j<arrayc.length;j++){
					  System.out.println(arrayc[j]);	  
				  }
			  }
			  if(stringName!=null&&stringName.equals("cpp"))
			  {
				  System.out.println("find ordered cpp");
				  String[] arraycpp = new String[cppList.size()];
				  for(int i=0;i<cppList.size();i++){
					  arraycpp[i] = ((Element)cppList.get(i)).getTextTrim();	  
				  }
				  Arrays.sort(arraycpp);
				  for(int j=0;j<arraycpp.length;j++){
					  System.out.println(arraycpp[j]);	  
				  }
			  }			  			  
		  }
	
	}
	public static void main(String[] args)
	{
		HandleXML handleXml = new HandleXML();   
		HandleXML.DOM4JForXml dom4j = handleXml.new DOM4JForXml();
		String filename= "score.xml";
		dom4j.readXml(filename);  
		Map newValue = new HashMap();
		newValue.put("age","99");
		dom4j.updateXml("gqz00", newValue, filename);
		dom4j.readXml(filename); 
		dom4j.addNewNode("you", "women", "25", "99","98", "97", filename);
		dom4j.readXml(filename); 
		dom4j.deleteXml("you", filename);
		dom4j.readXml(filename); 
		dom4j.findXml("name", "gqz00", filename);
		dom4j.findXml("sex", "women", filename);
		dom4j.findXml("age", "26", filename);
		dom4j.findXml("java", "99", filename);
		dom4j.findXml("c", "98", filename);
		dom4j.findXml("cpp", "88", filename);
		dom4j.findXml("cpp",  filename);
	}

}
