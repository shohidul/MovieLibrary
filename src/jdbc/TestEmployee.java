package jdbc;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestEmployee {

//    private static byte[] myImg;
//    private static WritableImage image;
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc/spring-jdbc.xml");
//        EmployeeDao ed = (EmployeeDao)context.getBean(EmployeeDao.class);
//        //System.out.println(ed.getEmployeebyIdRM(7).getSalary());
//        List<Employee> emplist = ed.getAllEmployeeList();
//        for (Employee e: emplist) {
//            System.out.println(e.getName());
//            
//        }
//        EmployeeSupportDao ed = (EmployeeSupportDao)context.getBean("employeeSupportDao");
//        System.out.println(ed.saveEmployee(new Employee("A", 10000)));
        HibernateDao hd = (HibernateDao)context.getBean("hibernateDao");
//        Date date = new Date();
//        hd.saveMovies(new Movies("C", "3", "3", 3000, "3", date));
//            hd.updateEmployee(new Employee(25,"D", "4", 3000));
//            hd.deleteEmployee(25);
//        System.out.println(hd.getEmployee());

//        hd.login(new Employee("a", "21"));
         List <Movie> list = hd.generateMovieId();
        for (Movie s: list) {
            System.out.println(s.getMovieId());
//            myImg = s.getPoster();
//            ImageView iv = new ImageView(image);
        }
       
//
//        List<Movies> mvelist = hd.getMovies();
//        for (Movies e: mvelist) {
//            System.out.println(e.getId()+" " +e.getTitle()+" " +e.getGenre());
//            
//        }
    }
    
//     private static Image convertToJavaFXImage(byte[] raw, final int width, final int height) {
//        image = new WritableImage(400, 500);
//        try {
//            ByteArrayInputStream bis = new ByteArrayInputStream(myImg);
//            BufferedImage read = ImageIO.read(bis);
//            image = SwingFXUtils.toFXImage(read, null);
//        } catch (IOException ex) {
//        }
//        return image;
//};
}
