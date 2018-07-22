package jdbc;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class HibernateDao {

    private List< Movie> list;
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

//    public boolean login(Employee e) {
//        try {
//
//            Query query = getSessionFactory().openSession().createQuery("SELECT u FROM Employee u WHERE u.name=:name AND u.pass =:pass");
//            query.setString("name", e.getName());
//            query.setString("pass", e.getPass());
//
//            List<Employee> cList = query.list();
//            cList.toString();
//
////            
//            if (cList.size() > 0) {
//                System.out.println("OK");
//                return true;
//            } else {
//                System.out.println("Not OKkkkkk");
//                return false;
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//
//            return false;
//
//        }
//    }
//
    public Movie getMovieId(String movieId) {
        return (Movie) getSessionFactory().getCurrentSession().get(Movie.class, movieId);
    }
    public MovieGenre getMovieGenreId(int id) {
        return (MovieGenre) getSessionFactory().getCurrentSession().get(MovieGenre.class, id);
    }
//
    public List< Movie> getMovies() {

//        String hql = "select * from Employee";
        list = getSessionFactory().openSession().createCriteria(Movie.class).list();
        return list;

    }
//
    @Transactional
    public void saveMovies(Movie e) {
        getSessionFactory().getCurrentSession().save(e);
    }
    @Transactional
    public void saveMovies_Genres(MovieGenre e) {
        getSessionFactory().getCurrentSession().save(e);
    }
//
    @Transactional
    public void updateMovie(Movie e) {
        getSessionFactory().getCurrentSession().update(e);
    }
    
//
////    @Transactional
////    public void deleteEmployee(Employee e){
////    getSessionFactory().getCurrentSession().delete(e);
////    }
    @Transactional
    public void deleteMovie(String movieId) {
        getSessionFactory().getCurrentSession().delete(getMovieId(movieId));
    }
    public List<MovieGenre> getAllMovieGenre(String duplicateMovId){
    Query query = getSessionFactory().openSession().createQuery("FROM MovieGenre E WHERE E.duplicateMovId = :id");
    query.setParameter("id", duplicateMovId);
    List results = query.list();
        return results;
    
    }
    
    public List<Movie> getAllWatchedMovies(){
    Query query = getSessionFactory().openSession().createQuery("FROM Movie E WHERE E.watchStatus = :id");
    query.setParameter("id", "Watched");
    List results = query.list();
        return results;
    
    }
    public List<Movie> getAllNonWatchedMovies(){
    Query query = getSessionFactory().openSession().createQuery("FROM Movie E WHERE E.watchStatus = :id");
    query.setParameter("id", "Not Watched");
    List results = query.list();
        return results;
    
    }
    public List<Movie> getAllFavoutiteMovies(){
    Query query = getSessionFactory().openSession().createQuery("FROM Movie E WHERE E.favourite = :id");
    query.setParameter("id", "Yes");
    List results = query.list();
        return results;
    
    }
    
    
    @Transactional
    public void deleteMovieGenre(int id) {
        getSessionFactory().getCurrentSession().delete(getMovieGenreId(id));
    
    }
    
    public List<Movie> getSingleValue(String movieId){
//    String hql = "FROM Movies E WHERE E.id = :id";
    
    Query query = getSessionFactory().openSession().createQuery("FROM Movie E WHERE E.movieId = :id");
    query.setParameter("id", movieId);
    List results = query.list();
        return results;
    
    }
    public List<Movie> generateMovieId(){
    Query query = getSessionFactory().openSession().createQuery("FROM Movie order by movieId desc");
    query.setMaxResults(1);
    List movieId = query.list();
        return movieId;
    }
    
    public List<Movie> sortByYear(){
    Query query = getSessionFactory().openSession().createQuery("FROM Movie order by year desc");
    List movieId = query.list();
        return movieId;
    }
    
    public List<Movie> sortByLastAdded(){
    Query query = getSessionFactory().openSession().createQuery("FROM Movie order by addedDate desc");
    List movieId = query.list();
        return movieId;
    }
    
    public List<Movie> sortByTitle(){
    Query query = getSessionFactory().openSession().createQuery("FROM Movie order by title");
    List movieId = query.list();
        return movieId;
    }
    
    public List<Movie> sortByRating(){
    Query query = getSessionFactory().openSession().createQuery("FROM Movie order by rating desc");
    List movieId = query.list();
        return movieId;
    }
    
    public List<MovieGenre> filterByGenre(String duplicateMovId){
    Query query = getSessionFactory().openSession().createSQLQuery("SELECT id FROM movie_genre JOIN movie on movie.movie_id = movie_genre.movie_id WHERE movie_genre.duplicateMovId = :id");
    query.setParameter("id", duplicateMovId);
    List movieId = query.list();
        return movieId;
    }
}
