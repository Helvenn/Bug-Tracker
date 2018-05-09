import java.util.List;

import test2_fk.StudentTown;

public class CodeSnippets {

	/**
	 *			SAVE
	 */
	StudentTown student = new StudentTown(name, postBr);
	id = (Integer) session.save(student);
	
	/**
	 * 			DELETE
	 */
	StudentTown student = (StudentTown)session.get(StudentTown.class, id);
	session.delete(student);
	
	/**
	 * 			GET
	 */
	student = (StudentTown)session.get(StudentTown.class, id);
	
	/**
	 * 			UPDATE
	 */
	StudentTown student = (StudentTown)session.get(StudentTown.class, id);
	student.setName(newName);
	
	/**
	 * 			QUERY
	 */
	List students = session.createQuery("FROM Student").list(); 
}
