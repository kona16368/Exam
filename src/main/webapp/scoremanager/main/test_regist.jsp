<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
 
<c:import url="/common/base.jsp">
 
	<c:param name="title">
		得点管理システム
	</c:param>
 
	<c:param name="content">
 
		<h2 class="h3 mb-4 bg-light py-2 px-3">
			成績管理
		</h2>
 
		<!-- 検索フォーム -->
		<form action="TestRegistExecute.action"
			  method="post">
 
			<div class="row mb-4">
 
				<div class="col-md-3">
 
					<label class="form-label">
						入学年度
					</label>
 
					<select name="ent_year"
							class="form-select">
 
						<option value="">
							--------
						</option>
 
						<c:forEach var="year"
								   items="${ent_year_set}">
 
							<option value="${year}"
								<c:if test="${year == ent_year}">
									selected
								</c:if>>
 
								${year}
 
							</option>
 
						</c:forEach>
 
					</select>
 
				</div>
 
				<div class="col-md-3">
 
					<label class="form-label">
						クラス
					</label>
 
					<select name="class_num"
							class="form-select">
 
						<option value="">
							--------
						</option>
 
						<c:forEach var="classNum"
								   items="${class_num_set}">
 
							<option value="${classNum}"
								<c:if test="${classNum == class_num}">
									selected
								</c:if>>
 
								${classNum}
 
							</option>
 
						</c:forEach>
 
					</select>
 
				</div>
 
				<div class="col-md-3">
 
					<label class="form-label">
						科目
					</label>
 
					<select name="subject_cd"
							class="form-select">
 
						<option value="">
							--------
						</option>
 
						<c:forEach var="subject"
								   items="${subject_set}">
 
							<option value="${subject.cd}"
								<c:if test="${subject.cd == subject_cd}">
									selected
								</c:if>>
 
								${subject.name}
 
							</option>
 
						</c:forEach>
 
					</select>
 
				</div>
 
				<div class="col-md-2">
 
					<label class="form-label">
						回数
					</label>
 
					<input type="number"
						   name="no"
						   value="${no}"
						   class="form-control">
 
				</div>
 
				<div class="col-md-1 d-flex align-items-end">
 
					<button type="submit"
							class="btn btn-secondary">
 
						検索
 
					</button>
 
				</div>
 
			</div>
 
		</form>
 
		<!-- 学生一覧 -->
		<c:if test="${not empty students}">
 
			<form action="TestRegistDone.action"
				  method="post">
				  
				  
 
				<input type="hidden"
					   name="ent_year"
					   value="${ent_year}">
 
				<input type="hidden"
					   name="class_num"
					   value="${class_num}">
 
				<input type="hidden"
					   name="subject_cd"
					   value="${subject_cd}">
 
				<input type="hidden"
					   name="no"
					   value="${no}">
 
				<table class="table table-bordered">
 
					<thead class="table-secondary">
 
						<tr>
							<th>入学年度</th>
							<th>クラス</th>
							<th>学生番号</th>
							<th>氏名</th>
							<th>点数</th>
						</tr>
 
					</thead>
 
					<tbody>
 
						<c:forEach var="student"
								   items="${students}">
 
							<tr>
 
								<td>${student.entYear}</td>
 
								<td>${student.classNum}</td>
 
								<td>${student.no}</td>
 
								<td>${student.name}</td>
 
								<td>

									<input type="number"
										   name="point_${student.no}"
										   value="${student.point}"
										   class="form-control">
								
									<c:if test="${errorStudent == student.no}">
										<div class="text-danger small mt-1">
											${errorMessage}
										</div>
									</c:if>
								
								</td>
 
							</tr>
 
						</c:forEach>
 
					</tbody>
 
				</table>
 
				<button type="submit"
						class="btn btn-primary">
 
					登録して終了
 
				</button>
 
			</form>
 
		</c:if>
 
	</c:param>
 
</c:import>