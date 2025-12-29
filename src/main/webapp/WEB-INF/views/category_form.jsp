<%-- 
    Document   : category_form
    Created on : Dec 29, 2025, 10:32:05 AM
    Author     : admin
--%>

<%@page import="View.ViewResolver"%>
<%@page import="View.CssResolver"%>
<%@page import="View.ComponentResolver"%>
<%@ page import="Data.Category" %>

<%
    Category category = (Category) request.getAttribute("category");
    String mode = (String) request.getAttribute("mode");

    boolean isEdit = "edit".equals(mode);
%>

<jsp:include page="<%= ComponentResolver.HEADER %>">
    <jsp:param name="title" value="Categories list" />
    <jsp:param name="css" value= "<%= CssResolver.CATEGORY_FORM %>" />
</jsp:include>

<div class="container py-5">
    <div class="form-container">

        <div class="form-card bg-white">

            <!-- HEADER -->
            <div class="form-header">
                <h3>
                    <i class="bi <%= isEdit ? "bi-pencil-square" : "bi-folder-plus" %>"></i>
                    <%= isEdit ? "Edit Category" : "Create New Category" %>
                </h3>
                <small class="opacity-75">
                    <%= isEdit ? "Update category details" : "Organize your todos with categories" %>
                </small>
            </div>

            <!-- FORM -->
            <div class="form-body">
                <form method="post"
                      action="<%= request.getContextPath() %>/category/<%= isEdit ? "update" : "create" %>">

                    <% if (isEdit) { %>
                        <input type="hidden" name="id" value="<%= category.getId() %>">
                    <% } %>

                    <!-- NAME -->
                    <div class="mb-3">
                        <label class="form-label fw-semibold">
                            Category Name
                        </label>
                        <input type="text"
                               name="name"
                               class="form-control"
                               placeholder="e.g. Work, Personal, School"
                               required
                               value="<%= isEdit ? category.getName() : "" %>">
                    </div>

                    <!-- COLOR -->
                    <div class="mb-3">
                        <label class="form-label fw-semibold">
                            Category Color
                        </label>
                        <input type="color"
                               name="color"
                               class="form-control form-control-color"
                               value="<%= isEdit ? category.getColor() : "#6366f1" %>"
                               oninput="updatePreview(this.value)">
                        <div id="colorPreview"
                             class="color-preview"
                             style="background:<%= isEdit ? category.getColor() : "#6366f1" %>">
                        </div>
                    </div>

                    <!-- ACTIONS -->
                    <div class="d-grid gap-2 mt-4">
                        <button class="btn btn-primary btn-main">
                            <i class="bi bi-check-circle"></i>
                            <%= isEdit ? "Save Changes" : "Create Category" %>
                        </button>

                        <a href="<%= request.getContextPath() %>/categories"
                           class="btn btn-outline-secondary btn-cancel">
                            Cancel
                        </a>
                    </div>

                </form>
            </div>

        </div>

    </div>
</div>

<script>
    function updatePreview(color) {
        document.getElementById("colorPreview").style.background = color;
    }
</script>

<%@ include file="<%= ComponentResolver.FOOTER %>" %>


