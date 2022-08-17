package com.kcs.attendancesystem.controller;

import com.kcs.attendancesystem.core.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kcs.attendancesystem.dto.ResponseVO;
import com.kcs.attendancesystem.dto.TeacherVO;
import com.kcs.attendancesystem.service.TeacherService;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@CommonRestController
public class TeacherController {

	@Autowired
	private TeacherService teacherService;
	
	@GetMapping("/teacher/findAll")
	public ResponseVO<Page<TeacherVO>> findAllTeacher(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
		return teacherService.findTeachers(pageNo, pageSize);
	}

	@GetMapping("/teachers")
	public ResponseVO<List<TeacherVO>> findAll() {
		return teacherService.findAll();
	}

	@GetMapping("/teacher")
	public ResponseVO<TeacherVO> findTeacher(@RequestParam("teacherId") Long teacherId) {
		return teacherService.findTeacher(teacherId);
	}

	@PutMapping("/teacher")
	public ResponseVO<Teacher> updateTeacher(@RequestBody TeacherVO vo) {
		return teacherService.updateTeacher(vo);
	}

	@DeleteMapping("/teacher/{teacherId}")
	public ResponseVO<Void> deleteTeacher(@PathVariable("teacherId") Long teacherId) {
		return teacherService.deleteTeacher(teacherId);
	}

	@GetMapping("/teachersCount")
	public ResponseVO<Long> countAll() {
		return teacherService.countAll();
	}

	@GetMapping("/searchTeachers")
	public ResponseVO<List<TeacherVO>> searchTeachers(@RequestParam("teacherName") String teacherName) {
		return teacherService.searchTeachers(teacherName);
	}
}
