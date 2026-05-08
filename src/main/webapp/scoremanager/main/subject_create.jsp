<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="content">

		<h2 class="h3 mb-3 fw-normal bg-light py-2 px-3">
			科目情報登録
		</h2>

		<form action="SubjectCreateExecute.action" method="post">

			<div class="mb-3">
	<label class="form-label">科目コード</label>
	<input type="text"
		   name="cd"
		   class="form-control"
		   placeholder="科目コードを入力してください"
		   required>
</div>

<div class="mb-3">
	<label class="form-label">科目名</label>
	<input type="text"
		   name="name"
		   class="form-control"
		   placeholder="科目名を入力してください"
		   required>
</div>

			<div class="mb-3">
				<button type="submit" class="btn btn-primary">
					登録
				</button>
			</div>

			<div>
				<a href="SubjectList.action">戻る</a>
			</div>

		</form>

		<c:if test="${not empty errorMessage}">
			<p class="text-danger mt-3">${errorMessage}</p>
		</c:if>

		<c:if test="${not empty message}">
			<p class="text-primary mt-3">${message}</p>
		</c:if>

	</c:param>
</c:import>