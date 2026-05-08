<%-- 科目削除JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section>
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>
			
				<div class="mx-auto py-2 px-4">

				<p>
					「${name}（${cd}）」を削除してもよろしいですか？
				</p>

			</div>
			<form action="SubjectUpdateExecute.action" method="get">
			<input type="hidden" name="cd" value="${cd}">

				<div class="mx-auto py-2 px-4">
					<input class="btn btn-danger"
						   type="submit"
						   value="削除">
				</div>
			</form>
			<a href="SubjectList.action">戻る</a>
		</section>
	</c:param>
</c:import>