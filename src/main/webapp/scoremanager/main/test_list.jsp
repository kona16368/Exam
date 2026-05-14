<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
 
<c:import url="/common/base.jsp">
 
	<c:param name="title">
		成績参照
</c:param>
 
	<c:param name="content">
 
		<section class="me-4">
 
			<h2 class="h3 mb-3 fw-normal bg-light p-3">成績一覧</h2>
 
			<div class="border rounded p-4 mb-3">
 
				<!-- 科目情報検索 -->
<form action="TestListSubjectExecute.action" method="get">
<div class="row align-items-end mb-3">
 
						<div class="col-2">
<label class="form-label">科目情報</label>
</div>
 
						<div class="col-2">
<label class="form-label">入学年度</label>
<select name="f1" class="form-select">
<option value="">--------</option>
<c:forEach var="year" items="${ent_year_set}">
<option value="${year}" <c:if test="${year == f1}">selected</c:if>>
										${year}
</option>
</c:forEach>
</select>
</div>
 
						<div class="col-2">
<label class="form-label">クラス</label>
<select name="f2" class="form-select">
<option value="">--------</option>
<c:forEach var="num" items="${class_num_set}">
<option value="${num}" <c:if test="${num == f2}">selected</c:if>>
										${num}
</option>
</c:forEach>
</select>
</div>
 
						<div class="col-4">
<label class="form-label">科目</label>
<select name="f3" class="form-select">
<option value="">--------</option>
<c:forEach var="subject" items="${subject_set}">
<option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>
										${subject.name}
</option>
</c:forEach>
</select>
</div>
 
						<div class="col-2">
<button class="btn btn-secondary" name="f" value="sj" type="submit">
								検索
								
</button>
</div>
 
					</div>
</form>
 
				<hr>
 
				<!-- 学生番号検索 -->
<form action="TestListStudentExecute.action" method="get">
<div class="row align-items-end">
 
						<div class="col-2">
<label class="form-label">学生情報</label>
</div>
 
						<div class="col-4">
<label class="form-label">学生番号</label>
<input type="text"
								name="f4"
								value="${f4}"
								class="form-control"
								placeholder="学生番号を入力してください">
</div>
 
						<div class="col-2">
<button class="btn btn-secondary" name="f" value="st" type="submit">
								検索
</button>
</div>
 
					</div>
</form>
 
			</div>
 
			<c:if test="${empty test_list}">
<p class="text-info">
					科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
</p>
</c:if>
 
			<c:if test="${not empty student}">
<p>
					氏名：${student.name}（${student.no}）
</p>
</c:if>
 
			
 
		</section>
 
	</c:param>
 
</c:import>
 