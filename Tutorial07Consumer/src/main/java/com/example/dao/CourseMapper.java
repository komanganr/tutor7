package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.example.model.CourseModel;
import com.example.model.StudentModel;

@Mapper
public interface CourseMapper {
	@Select("select id_course, name, credits from course where id_course = #{id_course}")
	@Results(value = { @Result(property = "id_course", column = "id_course"),
			@Result(property = "name", column = "name"), @Result(property = "credits", column = "credits"),
			@Result(property = "students", column = "id_course", javaType = List.class, many = @Many(select = "selectStudents")) })
	CourseModel selectCourse(@Param("id_course") String id_course);

	/*
	 * Melakukan join antara tabel studentcourse dengan student berdasarkan npm
	 * dan difilter hanya pada course dengan npm yang diberikan.
	 */
	@Select("select student.npm, name, gpa " + "from studentcourse join student "
			+ "on studentcourse.npm = student.npm " + "where studentcourse.id_course = #{id_course}")
	List<StudentModel> selectStudents(@Param("id_course") String id_course);

	@Select("select id_course, name, credits from course")
	@Results(value = { @Result(property = "id_course", column = "id_course"),
			@Result(property = "name", column = "name"), @Result(property = "credits", column = "credits"),
			@Result(property = "students", column = "id_course", javaType = List.class, many = @Many(select = "selectStudents")) })
	List<CourseModel> selectAllCourses();
}