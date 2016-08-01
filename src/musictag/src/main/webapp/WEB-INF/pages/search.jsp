<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta content='text/html; charset=ISO-8859-1' http-equiv='Content-Type'>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/release.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/artist-overview.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/search.css" />
    <script src="<%=request.getContextPath()%>/js/jquery.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
    <script src="<%=request.getContextPath()%>/js/util.js"></script>
    <script src="<%=request.getContextPath()%>/js/tag_builder.js"></script>
    <script src="<%=request.getContextPath()%>/js/paginator.js"></script>
    <script src="<%=request.getContextPath()%>/js/search.js"></script>
  </head>
  <body>
    <%@ include file="_navbar.html"%>
    <div class='container' style='margin-top:80px;'>
      <div class='row'>
        <div class='col-md-6 col-md-offset-3'>
          <form action='/musictag/search'>
            <div class='input-group' id='search-bar'>
              <input class='form-control' id='search-input' name='key' placeholder='Search for...' type='text'>
              <span class='input-group-btn'>
                <button class='btn btn-default' type='submit'>
                  <i class='glyphicon glyphicon-search'></i>
                </button>
              </span>
            </div>
          </form>
        </div>
      </div>
      <div id='search-summary'>
        <p>&nbsp;</p>
      </div>
      <!-- Nav tabs -->
      <ul class='nav nav-tabs' id='search-tabs' role='tablist'>
        <li role='presentation'>
          <a aria-controls='home' data-toggle='tab' href='#recordings' id='recordings-tab' role='tab'>Recordings</a>
        </li>
        <li role='presentation'>
          <a aria-controls='profile' data-toggle='tab' href='#artists' id='artists-tab' role='tab'>Artists</a>
        </li>
        <li role='presentation'>
          <a aria-controls='messages' data-toggle='tab' href='#releases' id='releases-tab' role='tab'>Albums</a>
        </li>
      </ul>
      <!-- Tab panes -->
      <div class='tab-content'>
        <div class='tab-pane fade' id='recordings' role='tabpanel'>
          <table class='table table-striped' data-page-param='currPage' data-pagination='#recordings-pagination' data-per-page-param='perPage' data-per-page='12' data-window='10' id='recording-tbl'>
            <thead>
              <th>Name</th>
              <th>Artist(s)</th>
              <th>Related album(s)</th>
              <th>Length</th>
            </thead>
            <tbody></tbody>
          </table>
          <div class='text-center' id='recordings-pagination'></div>
        </div>
        <div class='tab-pane fade' id='artists' role='tabpanel'>
          <div class='row' data-page-param='currPage' data-pagination='#artist-pagination' data-per-page-param='perPage' data-per-page='24' data-window='10' id='artist-frames'></div>
          <div class='text-center' id='artist-pagination'></div>
        </div>
        <div class='tab-pane fade' id='releases' role='tabpanel'>
          <div class='row' data-page-param='currPage' data-pagination='#release-pagination' data-per-page-param='perPage' data-per-page='24' data-window='10' id='release-frames'></div>
          <div class='text-center' id='release-pagination'></div>
        </div>
      </div>
    </div>
  </body>
</html>
