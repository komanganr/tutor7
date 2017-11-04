package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Many;

import com.example.model.CourseModel;
import com.example.model.StudentModel;

@Mapper
public interface StudentMapper {
	/*
	 * @Select("select npm, name, gpa from student where npm = #{npm}")
	 * StudentModel selectStudent (@Param("npm") String npm);
	 */
	@Select("select npm, name, gpa from student where npm = #{npm}")
	@Results(value = {
			@Result(property = "npm", column = "npm"),
			@Result(property = "name", column = "name"),
			@Result(property = "gpa", column = "gpa"),
			@Result(property = "courses", column = "npm", javaType = List.class, many = @Many(select = "selectCourses")) })
	StudentModel selectStudent(@Param("npm") String npm);

	/*
	 * @Select("select npm, name, gpa from student") List<StudentModel>
	 * selectAllStudents ();
	 */

	@Select("select npm, name, gpa from student")
	@Results(value = { @Result(property = "npm", column = "npm"),
			@Result(property = "name", column = "name"),
			@Result(property = "gpa", column = "gpa"),
			@Result(property = "courses", column = "npm", javaType = List.class, many = @Many(select = "selectCourses")) })
	List<StudentModel> selectAllStudents();

	@Insert("INSERT INTO student (npm, name, gpa) VALUES (#{npm}, #{name}, #{gpa})")
	void addStudent(StudentModel student);

	@Delete("DELETE FROM student where npm = #{npm}")
	void deleteStudent(StudentModel student);

	@Update("UPDATE student SET name = #{name}, gpa=#{gpa} WHERE npm = #{npm}")
	void updateStudent(StudentModel student);

	/*
	 * Melakukan join antara tabel studentcourse dengan course berdasarkan
	 * id_course dan difilter hanya pada student dengan NPM yang diberikan.
	 */
	@Select("select course.id_course, name, credits " + "from studentcourse join course "
			+ "on studentcourse.id_course = course.id_course " + "where studentcourse.npm = #{npm}")
	List<CourseModel> selectCourses(@Param("npm") String npm);

	@Select("select id_course, name, credits from course where id_course = #{id_course}")
	@Results(value = { @Result(property = "id_course", column = "id_course"),
			@Result(property = "name", column = "name"), @Result(property = "credits", column = "credits"),
			@Result(property = "npm", column = "courses", javaType = List.class, many = @Many(select = "selectStudents")) })
	CourseModel selectCourse(@Param("id_course") String id_course);

	/*
	 * Melakukan join antara tabel studentcourse dengan course berdasarkan
	 * id_course dan difilter hanya pada student dengan NPM yang diberikan.
	 */
	@Select("select student.npm, name " +
	 "from studentcourse join student "
	 + "on studentcourse.id_course = student.npm "
	 + "where studentcourse.id_course = #{id_course}")
	List<CourseModel> selectStudents(@Param("id_course") String id_course);
}