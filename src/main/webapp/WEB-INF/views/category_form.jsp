<%-- 
    Document   : category_form
    Created on : Dec 29, 2025, 10:32:05 AM
    Author     : admin
--%>

<%@page import="View.CssResolver"%>
<%@page import="View.ComponentResolver"%>
<%@ page import="Data.Category" %>
<%@ page import="Paths.Api" %>

<%
    Category category = (Category) request.getAttribute("category");
    // Detect edit mode from existence of category id (null-safe)
    boolean isEdit = (category != null && category.getId() != null);
%>

<jsp:include page="<%= ComponentResolver.resolve(ComponentResolver.HEADER) %>">
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
                      action="<%= isEdit ? (request.getContextPath() + Api.CATEGORIES_UPDATE(category.getId()).getPath()) : (request.getContextPath() + Api.CATEGORIES_CREATE.getPath()) %>">

                    <% if (isEdit) { %>
                        <input type="hidden" name="id" value="<%= category.getId() %>">
                    <% } %>

                    <!-- NAME -->
                    <div class="mb-3">
                        <label for="categoryName" class="form-label fw-semibold">
                            Category Name
                        </label>
                        <input id="categoryName" type="text"
                               name="name"
                               class="form-control"
                               placeholder="e.g. Work, Personal, School"
                               required
                               value="<%= isEdit ? category.getName() : "" %>">
                    </div>

                    <!-- COLOR -->
                    <div class="mb-3">
                        <label for="categoryColor" class="form-label fw-semibold">
                            Category Color
                        </label>
                        <input id="categoryColor" type="color"
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

                        <a href="<%= request.getContextPath() + Api.CATEGORIES_LIST.getPath() %>"
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

<jsp:include page="<%= ComponentResolver.resolve(ComponentResolver.FOOTER) %>" />
