import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Student {
	
	private String name ;
	private String advisor;
	private String typeFields;
	
	public Student() {
		super();//This is our super class of Student Class .
	}
	
	public Student(String name , String advisor , String typeFields) {
		this.advisor = advisor;
		this.name = name;
		this.typeFields = typeFields;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdvisor() {
		return advisor;
	}

	public void setAdvisor(String advisor) {
		this.advisor = advisor;
	}

	public String getTypeFields() {
		return typeFields;
	}

	public void setTypeFields(String parts) {
		this.typeFields = parts;
	}
	
	@Override
	public String toString() {// This overrides the toString
		//method and  that does also returns its data fields.

		
		
		return String.format("Type : %s\r\nName : %s\r\nAdvisor : %s\r\n",
							typeFields,name,advisor
				);
	}
	
	
	
	public class underGraduateStudent extends Student {//This class is subclass of Student class . Its takes
		//number of hours about taking semester . 
		private String takingHours;
		
		public underGraduateStudent() {
			super();
		}
		
		public underGraduateStudent(String name , String advisor , String typeFields , String takingHours) {
			super(name ,  advisor ,  typeFields);
			this.takingHours = takingHours;
		}
		
		@Override
			public String toString() {//Polymorphisim used.
				// TODO Auto-generated method stub
				return super.toString() + "Taking Hours : %d\r\n" + takingHours;
			}

		public String getTakingHours() {//For the get taking hours semester . 
			return takingHours;
		}

		public void setTakingHours(String parts) {//For the modificate number of hours . 
			this.takingHours = parts;
		}
		
	}
	
	public class graduateStudent extends Student {
		private String thesis;
		
		public graduateStudent() {
			super();
		}
		
		public graduateStudent(String typeFields ,  String name ,String advisor, String thesis) {
			super(name , advisor , typeFields);
			this.thesis = thesis;
		}
		@Override
		public String toString() {//Polymorphisim used .
			
			return super.toString() +"Thesis Topic : %s\r\n" + thesis;
		}
		public String getThesis() {//For get extra data as thesis topic
			return thesis;
		}

		public void setThesis(String thesis) {//For modificate extra data as thesis topic
			this.thesis = thesis;
		}
		
	}
	
	public interface StudentParser{
		abstract Student parse(String student) throws FileNotFoundException;//The parse 
		//method takes a string a returns an object of a subtype (UndergradStudent or GraduateStudent).
		//The string argument contains a student information that needs to be parsed by the parse method
	}
	
	public static class  GradParser implements StudentParser{
				
        @Override
        public  Student parse(String student) throws FileNotFoundException {
        		Student stu = new Student();
        		graduateStudent gradStu = stu.new graduateStudent();

                Scanner input = new Scanner(new File("Studentinformation.txt"));
                input.useDelimiter("-");
                String[] info = input.next().split(":");
                while(input.hasNext()) {
                       
                       String type = info[0];
                       String name = info[1];
                       String advisor = info[2];
                       String thesis= info[3];

                     graduateStudent newGradStu = gradStu.new graduateStudent(type,name,advisor,thesis);
                     StudentHandler.students.add(newGradStu);
                     

                }
               
                return gradStu;
        }


    }
	
	public static class UndergradParser implements StudentParser{

		@Override
		public Student parse(String student) throws FileNotFoundException {
			Student stu = new Student();
    		graduateStudent underStu = stu.new graduateStudent();
			Scanner input = new Scanner(new File("Studentinformation.txt"));
			input.useDelimiter("-");
			String[] info = input.next().split(":");
			while(input.hasNext()) {
				
				String type = info[0];
                String name = info[1];
                String advisor = info[2];
                String hours = info[3];
                
                underGraduateStudent newUnderGradStu = underStu.new underGraduateStudent(type,name,advisor,hours);
                //StudentHandler.students.add(newUnderGradStu);
			}
			return underStu;
	}
}
	
		
		public static class StudentHandler {
	        public static  ArrayList<Student> students = new ArrayList<>();
	        
	        
	        
	        public StudentHandler() {
	                super();
	                this.students = new ArrayList();
	        }

	        //public ArrayList<Student> getStudents() {
	        //    return students;
	       // }
	        
	       
	        
	        public void initialize() throws FileNotFoundException {
	        	int index = 0;
	        	Scanner input = new Scanner(new File("Studentinformation.txt"));
	        	input.useDelimiter("-");
	        	while(input.hasNext()) {
	        		 index = input.nextInt();
	        	}
	        	StudentParser[] parser = {new GradParser(),new UndergradParser()};
	        	students.add((Student) parser[index]);
	        	System.out.println("Initialize method applied succesfuly");
	        }
	        
	        
	        
	        public void searchByName(String name) {
                boolean found = false;

                for (int i = 0; i < this.students.size(); i++) {
                        if (this.students.get(i).getName().equals(name)) {
                                System.out.println(this.students.get(i).toString());
                                found = true;
                        }
                }

                if (found == false) {
                        System.out.println("Student with that name not found!!");
                }
        }
	        
	        public void modifyName(String oldName, String newName) {
                boolean found = false;
                int index = 0;

                for (int i = 0; i < this.students.size(); i++) {
                        if (this.students.get(i).getName().equals(oldName)) {
                                found = true;
                                index = i;
                        }
                }

                if (found) {
                        this.students.get(index).setName(newName);
                }
                System.out.println("Students name is changed");
        }

        public void saveData() throws IOException {
                File file = new File("output.txt");

                FileWriter fileWriter = new FileWriter(file);

                for (int i = 0; i < this.students.size(); i++) {
                        fileWriter.write(this.students.get(i).toString());
                        fileWriter.write("\n");
                }

                fileWriter.flush();
                fileWriter.close();
                System.out.println("Students data is saved .");
        }
      
}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		StudentHandler stuHand = new StudentHandler();
		try {
			stuHand.initialize();
			stuHand.searchByName("Alparslan");
			stuHand.modifyName("Alparslan", "Mustafa");
			stuHand.saveData();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        	
	}

 }

