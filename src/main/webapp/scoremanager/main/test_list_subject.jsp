<%-- 成績一覧（科目）JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">

		<section>

			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
				成績一覧（科目）
			</h2>

			<form action="TestListSubjectExecute.action" method="get">

				<div class="border rounded p-4 mb-4">

					<div class="row">

						<div class="col-2">

							<label for="ent_year">
								入学年度
							</label>

							<select
								class="form-select"
								id="ent_year"
								name="ent_year">

								<option value="">
									--------
								</option>

								<c:forEach var="year" items="${entYearSet}">

									<option value="${year}">
										${year}
									</option>

								</c:forEach>

							</select>

						</div>

						<div class="col-2">

							<label for="class_num">
								クラス
							</label>

							<select
								class="form-select"
								id="class_num"
								name="class_num">

								<option value="">
									--------
								</option>

								<c:forEach var="classNum" items="${classNumSet}">

									<option value="${classNum}">
										${classNum}
									</option>

								</c:forEach>

							</select>

						</div>

						<div class="col-4">

							<label for="subject_cd">
								科目
							</label>

							<select
								class="form-select"
								id="subject_cd"
								name="subject_cd">

								<option value="">
									--------
								</option>

								<c:forEach var="subject" items="${subjectSet}">

									<option value="${subject.cd}">
										${subject.name}
									</option>

								</c:forEach>

							</select>

						</div>

						<div class="col-2 mt-auto">

							<input
								class="btn btn-secondary"
								type="submit"
								value="検索">

						</div>

					</div>

				</div>

			</form>

			<c:if test="${not empty list}">

				<div class="mb-2">

					科目：${subjectName}

				</div>

				<table class="table table-hover">

					<thead>

						<tr>

							<th>入学年度</th>
							<th>クラス</th>
							<th>学生番号</th>
							<th>氏名</th>
							<th>1回</th>
							<th>2回</th>

						</tr>

					</thead>

					<tbody>

						<c:forEach var="obj" items="${list}">

							<tr>

								<td>${obj.ent_year}</td>
								<td>${obj.class_num}</td>
								<td>${obj.student_no}</td>
								<td>${obj.student_name}</td>

								<td>

									<c:choose>

										<c:when test="${obj.no == 1}">
											${obj.point}
										</c:when>

										<c:otherwise>
											-
										</c:otherwise>

									</c:choose>

								</td>

								<td>

									<c:choose>

										<c:when test="${obj.no == 2}">
											${obj.point}
										</c:when>

										<c:otherwise>
											-
										</c:otherwise>

									</c:choose>

								</td>

							</tr>

						</c:forEach>

					</tbody>

				</table>

			</c:if>

		</section>

	</c:param>

</c:import>